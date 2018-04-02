package cn.dubidubi.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.kevinsawicki.http.HttpRequest;

import cn.dubidubi.model.json.WxTemp;
import cn.dubidubi.model.json.WxTempOne;
import cn.dubidubi.model.json.WxWeatherTempData;
import cn.dubidubi.service.WxBaseService;
import cn.dubidubi.service.WxTempService;

/**
 * @author linzj
 * @Description: 微信发送模版消息
 * @date 2018年3月30日 下午12:51:37
 */
@Service
public class WxTempServiceImpl implements WxTempService {
	@Autowired
	WxBaseService baseService;

	@Override
	public void sendTempMessage(JSONArray jsonArray) {
		WxTemp wxTemp = new WxTemp();
		// 三个单独的属性值
		wxTemp.setTopcolor("#FF0000");
		wxTemp.setTouser("oWH4hwjClpJPB6IwAsH_RapXhLbI");
		wxTemp.setUrl("http://www.baidu.com");
		wxTemp.setTemplate_id("Sw2ublSJFBfIWvUQaZRKThGoX8FYYOVD_e4XGRQ86iA");
		// data对象中的值
		WxWeatherTempData wxWeatherTempData = new WxWeatherTempData();
		// 设置user
		WxTempOne one = new WxTempOne();
		one.setValue("lzzzz");
		one.setColor("#173177");
		// 设置weather
		WxTempOne weather = new WxTempOne();
		weather.setValue(jsonArray.getJSONObject(0).getString("weather"));
		weather.setColor("#173177");
		// 设置温度
		WxTempOne temp = new WxTempOne();
		temp.setValue(jsonArray.getJSONObject(0).getString("temperature"));
		temp.setColor("#173177");
		// 设置发布时间
		WxTempOne time = new WxTempOne();
		time.setValue(jsonArray.getJSONObject(0).getString("reporttime"));
		time.setColor("#173177");

		// 放入data中
		wxWeatherTempData.setUser(one);
		wxWeatherTempData.setTime(time);
		wxWeatherTempData.setWeather(weather);
		wxWeatherTempData.setTemp(temp);

		// 放入wxtemp中
		wxTemp.setData(wxWeatherTempData);
		String access_token = baseService.getAccessToken();
		HttpRequest httpRequest = HttpRequest
				.post("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + access_token);
		httpRequest.trustAllCerts();
		httpRequest.trustAllHosts();
		System.out.println(JSON.toJSONString(wxTemp));
		HttpRequest response = httpRequest.send(JSON.toJSONString(wxTemp));
		System.out.println(response.body());
	}
}
