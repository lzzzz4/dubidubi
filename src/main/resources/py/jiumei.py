import os
import random
import re
import uuid

import requests
import time
from bs4 import BeautifulSoup
import sys

headers = {
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 '
                  + '(KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36'}


def goToHomeSite(word, page):
    resp = requests.get("http://www.99mm.me/search/?key=" + word + "&page=" + str(page))
    resp.encoding = 'utf-8'
    h5 = resp.text
    soup = BeautifulSoup(h5, 'html.parser')
    lis = soup.select("#piclist > li")
    # 获取有几组套图
    series_count = soup.select("div.page > span.info")
    return [soup, lis, series_count]


# 九妹爬虫
soup, lis, series_count = goToHomeSite(sys.argv[1], 1)
if len(series_count) != 0:
    series_count = series_count[0].text
    series_count = re.findall(r'\d+', series_count)
    if (int(series_count[0]) > 10):
        rint = random.randint(0, 9)
    else:
        rint = random.randint(0, int(series_count[0]))
# 当页数不为1的时候,获得总的页数，随机访问一页
else:
    series_count = soup.select("div.page > a.all")[0].text
    rint = random.randint(0, 9)
    num_page = re.findall(r"\d+", series_count)
    # 获取随机任意一页的一组套图
    num_page = random.randint(1, int(num_page[0]))
    soup, lis, series_count = goToHomeSite(sys.argv[1], num_page)
seriesurl = "http://www.99mm.me" + lis[rint].select("dl > dt > a")[0].get("href")
# 系列id
resource_start = seriesurl.rfind("/")
resource_end = seriesurl.rfind(".html")
id = seriesurl[resource_start + 1:resource_end]
# 获取图片总数
imgtotal = lis[0].select("dd > span")[0].text
pictotalAmout = int(re.findall(r'\d+', imgtotal)[0])
if (pictotalAmout >= 7):
    pictotalAmout = 7
# 套图图片总数
# 获取套图中的单个图片
for i in range(1, pictotalAmout + 1):
    referUrl = seriesurl + "?url=" + str(i)
    myheaders = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 '
                      + '(KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36',
        'Referer': seriesurl + '?url=' + str(i)}
    resp = requests.get("http://www.99mm.me" + "/url.php?id=" + id, headers=myheaders)
    time.sleep(1)
    imgsrc = resp.text
    start = imgsrc.find("src=")
    end1 = imgsrc.find('"')
    end = imgsrc.find('"', end1 + 1)
    final_pic_src = imgsrc[start + 5:end]
    imgresponse = requests.get(final_pic_src)
    if (imgresponse.status_code == 200):
        today = time.strftime("%Y-%m-%d", time.localtime())
        path = sys.argv[2] + today
        if not os.path.exists(path):
            os.mkdir(path)
        filepath = str(uuid.uuid1())[0:10] + final_pic_src[final_pic_src.rfind("/") + 1:final_pic_src.rfind(
            ".jpg")] + ".jpg"
        finpath = path + "/" + filepath
        print(filepath)
        open(finpath, "wb").write(imgresponse.content)
