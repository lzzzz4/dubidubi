import requests
from bs4 import BeautifulSoup

resp = requests.get("http://www.thepaper.cn/")
resp.encoding = "utf-8"
text = resp.text
soup = BeautifulSoup(text, "html.parser")
src = soup.select("div.news_li > div.news_tu > a > img")
flag = 0
for n in src:
    flag = flag + 1
    list1 = n.get("src")
    image = requests.get("http:" + list1)
    if image.status_code == 200:
        open("H:/news/" + str(flag) + ".jpg", "wb").write(image.content)
