package cn.dubidubi.util;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.kevinsawicki.http.HttpRequest;

import cn.dubidubi.model.WxButton;
import cn.dubidubi.model.WxMenu;
import cn.dubidubi.service.WxBaseService;
import cn.dubidubi.service.impl.WxBaseServiceImpl;

@Service
public class WxMenuUtil {
	// 用于自定义菜单
	public static void main(String[] args) {
		WxButton wxButton = new WxButton();
		wxButton.setType("click");
		wxButton.setName("获取天气");
		wxButton.setKey("weather");
		WxButton[] button = new WxButton[1];
		button[0] = wxButton;
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
