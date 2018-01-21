package cn.dubidubi.service.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.kevinsawicki.http.HttpRequest;

import cn.dubidubi.service.WxBaseService;
import cn.dubidubi.util.wx.WxProperties;

@Service
public class WxBaseServiceImpl implements WxBaseService {
	private static String accessToken;

	private String getRealAccessToken() {
		HttpRequest request = HttpRequest
				.get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + WxProperties.APP_Id
						+ "&secret=" + WxProperties.APP_SECRET);
		request.trustAllCerts();
		request.trustAllHosts();
		String body = request.body();
		JSONObject jsonObject = JSON.parseObject(body);
		String access_token = jsonObject.getString("access_token");
		return access_token;
	}

	// 定时请求获得accessToken 每10分钟执行一次
	@Override
	@Scheduled(cron = "* */10 * * * ?")
	public void anyHourGetAccessToken() {
		accessToken = getRealAccessToken();
	}

	@Override
	public String getAccessToken() {
		if (accessToken == null) {
			accessToken = getRealAccessToken();
		}
		return accessToken;
	}

}
