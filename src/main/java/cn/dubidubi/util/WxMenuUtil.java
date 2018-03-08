package cn.dubidubi.util;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.kevinsawicki.http.HttpRequest;

import cn.dubidubi.model.json.WxButton;
import cn.dubidubi.model.json.WxMenu;
import cn.dubidubi.service.WxBaseService;
import cn.dubidubi.service.impl.WxBaseServiceImpl;

@Service
/**
 * @author linzj
 * @Description: 自定义菜单,menu菜单中需要button数组
 * @date 2018年1月21日 下午7:48:22
 */
public class WxMenuUtil {
	/**
	 * @Description: 自定义菜单按钮
	 * @data :@param args
	 * @date :2018年3月6日下午1:44:50
	 */
	public static void main(String[] args) {
		// 天气按钮
		WxButton wxButton = new WxButton();
		wxButton.setType("click"); // 种类，点击事件
		wxButton.setName("天气"); // 按钮名
		wxButton.setKey("weather"); // key 实际接受到的key
		// 新闻按钮
		WxButton news = new WxButton();
		news.setName("新闻");
		WxButton[] news_sub = new WxButton[2];
		// 澎湃子按钮
		WxButton pengpai = new WxButton();
		pengpai.setName("澎湃");
		pengpai.setType("click");
		pengpai.setKey("100");
		news_sub[0] = pengpai;
		// 腾讯子按钮
		WxButton tencent_news = new WxButton();
		tencent_news.setName("腾讯");
		tencent_news.setType("click");
		tencent_news.setKey("101");
		news_sub[1] = tencent_news;
		news.setSub_button(news_sub);
		WxButton[] button = new WxButton[2];
		button[0] = wxButton; // 将天气加入数组
		button[1] = news; // 将新闻加入数组
		WxMenu wxMenu = new WxMenu();
		wxMenu.setButton(button);
		WxBaseService wxBaseService = new WxBaseServiceImpl();
		String access_Token = wxBaseService.getAccessToken();
		System.out.println(access_Token);
		HttpRequest request = HttpRequest
				.post("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + access_Token);
		request.trustAllCerts();
		request.trustAllHosts();
		String json = JSON.toJSONString(wxMenu);
		System.out.println(json);
		request.send(json);
		String body = request.body();
		System.out.println(body);
	}
}
