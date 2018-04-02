package cn.dubidubi.service.impl;

import org.slf4j.LoggerFactory;
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
		LoggerFactory.getLogger(this.getClass()).info("获取accesstoken");
		return access_token;
	}

	// 定时请求获得accessToken 每10分钟执行一次
	// cron表达式,在指定每一段时间执行一次后,需要将小的单位指定好,例如现在是在整个分钟内都可以执行
	@Override
	@Scheduled(cron = "0 0/10 * * * ?")
	public void anyHourGetAccessToken() {
		accessToken = getRealAccessToken();
		LoggerFactory.getLogger(this.getClass()).info("得到accessToken码");
	}

	@Override
	public String getAccessToken() {
		if (accessToken == null) {
			accessToken = getRealAccessToken();
		}
		LoggerFactory.getLogger(this.getClass()).info("获得access_token" + accessToken);
		return accessToken;

	}

	@Override
	public String codeToAccessToken(String code) {
		String wxUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + WxProperties.APP_Id + "&secret="
				+ WxProperties.APP_SECRET + "&code=" + code + "&grant_type=authorization_code";
		HttpRequest httpRequest = HttpRequest.get(wxUrl);
		httpRequest.trustAllCerts();
		httpRequest.trustAllHosts();
		String json = httpRequest.body();
		JSONObject jsonObject = new JSONObject();
		jsonObject = JSON.parseObject(json);
		// 拉取信息
		String getInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token="
				+ jsonObject.getString("access_token") + "&openid=" + jsonObject.getString("openid") + "&lang=zh_CN";
		HttpRequest info = HttpRequest.get(getInfoUrl);
		String userInfo = info.body();
		return userInfo;
	}

}
