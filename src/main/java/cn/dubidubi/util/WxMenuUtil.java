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
	// 用于自定义菜单
	public static void main(String[] args) {
		// 天气按钮
		WxButton wxButton = new WxButton();
		wxButton.setType("click"); // 种类，点击事件
		wxButton.setName("天气"); // 按钮名
		wxButton.setKey("weather"); // key 实际接受到的key
		// 新闻按钮
		WxButton news = new WxButton();
		news.setName("新闻");
		news.setType("click");
		news.setKey("news");
		WxButton[] button = new WxButton[2];
		button[0] = wxButton; // 将天气加入数组
		button[1] = news; // 将新闻加入数组
		WxMenu wxMenu = new WxMenu();
		wxMenu.setButton(button);
		WxBaseService wxBaseService = new WxBaseServiceImpl();
		String access_Token = wxBaseService.getAccessToken();
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
