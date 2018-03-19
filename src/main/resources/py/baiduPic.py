import requests
import urllib.parse
import sys

headers = {
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 '
                  + '(KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36', "referer": "https://image.baidu.com"}
# word = "lol"
# word = urllib.parse.quote(word)
# width = ""
# height = ""
 # 从第几个图片开始
# pn = "0"
 # 显示几个
# rn = "30"
# params = {"tn": "resultjson_com", "ipn": "rj", "ct": "201326592", "fp": "result", "ie": "utf-8", "oe": "utf-8",
#          "word": sys.argv[1], "width": sys.argv[2], "height": sys.argv[3], "pn": sys.argv[4], "rn": sys.argv[5], "gsm": "30000001d"}
if sys.argv[2] == "no":
    sys.argv[2] = ""
if sys.argv[3] == "no":
    sys.argv[3] == ""
resp = requests.get("https://image.baidu.com/search/acjson"
                    + "?tn=resultjson_com&fp=result&ie=utf-8&oe=utf-8&ipn=rj"
                    + "&word=" + sys.argv[1] + "&width=" + sys.argv[2] + "&height=" + sys.argv[3]
                    + "&pn=" + sys.argv[4] + "&rn=" + sys.argv[5], headers=headers)
# resp = requests.get("https://image.baidu.com/search/acjson", data=params, headers=headers)
resp.encoding = 'utf-8'
json = resp.json()
for data in json['data']:
    try:
        if data['thumbURL'][8:11].find("ss") >= 0:
            print(data['thumbURL'])
            print(data['fromPageTitle'])
        # print("hello")
    except:
        pass
