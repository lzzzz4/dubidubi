import os
import uuid

import pymysql
import requests

# 访问澎湃并得到响应
import time

import sys
from bs4 import BeautifulSoup

# 设置源
newsSource = "来自澎湃新闻"
# 设置域名前缀
preUrl = "http://dubidubi.iask.in/repoter"
# 连接数据库
conn = pymysql.connect(host='119.29.28.81', port=3306, user='root', passwd='Linzijie123!!', db='gzh', charset='utf8')
# 获取数据库游标
cursor = conn.cursor()
newsurl = "http://www.thepaper.cn/"
# 得到当前时间的字符串
todaystr = time.strftime("%Y-%m-%d", time.localtime())
currentTime = time.strftime("%Y-%m-%d %H:%M:%S", time.localtime())
resp = requests.get(newsurl)
# 设置响应的编码
resp.encoding = "utf-8"
# 得到响应的h5并解析
html5 = resp.text
h5soup = BeautifulSoup(html5, "html.parser")
# 解析h5的css
divs = h5soup.select("div.news_li")
for div in divs:
    ataglist = div.select("div.news_tu > a")
    if not ataglist:
        break
    # 得到新闻标题
    title = div.select("h2 > a")
    # 得到新闻内容
    news_content = div.select("p")
    # 得到新闻链接
    ahref = "http://www.thepaper.cn/" + ataglist[0].get("href")
    # 得到新闻图片
    imglist = ataglist[0].select("img")
    imghref = "http:" + imglist[0].get("src")
    # 访问图片服务器,并存储图片
    img_resp = requests.get(imghref)
    if img_resp.status_code == 200:
        # 新建文件夹的路径
        path = sys.argv[1] + todaystr
        if not os.path.exists(path):
            os.mkdir(path)
        # 指定文件路径存放图片
        finpath = path + "/" + imghref[10:20] + str(uuid.uuid1())[0:15] + ".jpg"
        open(finpath, "wb").write(img_resp.content)
        img_url = finpath.find("/news")
        cursor.execute('insert news_info(title,create_time,img_url,original_web_url,source,source_id) values(%s,%s,%s,%s,%s,%s)',
                       (title[0].text, currentTime, preUrl + finpath[img_url:], ahref, newsSource, 100))
conn.commit()
cursor.close()
conn.close()
