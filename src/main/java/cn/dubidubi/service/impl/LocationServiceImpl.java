package cn.dubidubi.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.kevinsawicki.http.HttpRequest;

import cn.dubidubi.dao.LocationMapper;
import cn.dubidubi.model.WxLocationDO;
import cn.dubidubi.model.dto.GaoDeAddressDTO;
import cn.dubidubi.model.xml.PushMessage;
import cn.dubidubi.model.xml.WxAll;
import cn.dubidubi.service.LocationService;

//用于处理定位相关及天气相关的api
@Service
public class LocationServiceImpl implements LocationService {
	@Autowired
	LocationMapper locationMapper;
	static String key = "60b4499bd0230e7a86941c12881e6f51";

	/**
	 * 得到城市
	 */
	@Override
	public GaoDeAddressDTO getCity(Double Longitude, Double Latitude) {
		HttpRequest request = HttpRequest
				.get("http://restapi.amap.com/v3/geocode/regeo?key=" + key + "&location=" + Longitude + "," + Latitude);
		String body = request.body();
		// System.out.println(body);
		JSONObject jsonObject = JSON.parseObject(body);
		JSONObject location = jsonObject.getJSONObject("regeocode");
		GaoDeAddressDTO gaoDeAddress = new GaoDeAddressDTO();
		gaoDeAddress.setFormatted_address(location.getString("formatted_address"));
		JSONObject address = location.getJSONObject("addressComponent");
		gaoDeAddress.setAdcode(address.getString("adcode"));
		// System.out.println(gaoDeAddress);
		return gaoDeAddress;
	}

	// 得到天气
	@Override
	public GaoDeAddressDTO getWeather(Double Longitude, Double Latitude) {
		GaoDeAddressDTO gaoDeAddress = getCity(Longitude, Latitude);
		HttpRequest request = HttpRequest
				.get("http://restapi.amap.com/v3/weather/weatherInfo?key=" + key + "&city=" + gaoDeAddress.getAdcode());
		String body = request.body();
		JSONObject jsonObject = JSON.parseObject(body);
		JSONArray live = jsonObject.getJSONArray("lives");
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("天气:").append(live.getJSONObject(0).getString("weather")).append("\n");
		stringBuilder.append("\n温度:").append(live.getJSONObject(0).getString("temperature")).append("℃").append("\n");
		stringBuilder.append("\n风速:").append(live.getJSONObject(0).getString("windpower")).append("\n");
		stringBuilder.append("\n发布时间:").append(live.getJSONObject(0).getString("reporttime")).append("\n");
		stringBuilder.append("\n城市:").append(gaoDeAddress.getFormatted_address()).append("\n");
		gaoDeAddress.setWeather(stringBuilder.toString());
		return gaoDeAddress;
	}

	// 保存打开公众号时上报的用户地址
	@Override
	public void saveOneLocation(WxAll wxAll) {
		WxLocationDO location = new WxLocationDO();
		location.setCreateTime(wxAll.getCreateTime());
		location.setFromUserName(wxAll.getFromUserName());
		location.setLatitude(wxAll.getLatitude());
		location.setLongitude(wxAll.getLongitude());
		WxLocationDO id = locationMapper.getLocationByOpenId(location.getFromUserName());
		if (id == null) {
			locationMapper.saveOneLocation(location);
		} else {
			locationMapper.updateLocationByOpenId(location);
		}
	}

	@Override
	public WxLocationDO getLocationByOpenId(String openId) {
		return locationMapper.getLocationByOpenId(openId);
	}

	// 得到地址推送的信息
	@Override
	public PushMessage getPushMessage(WxAll all) {
		PushMessage pushMessage = new PushMessage();
		WxLocationDO location = getLocationByOpenId(all.getFromUserName());
		pushMessage.setContent(getWeather(location.getLongitude(), location.getLatitude()).getWeather());
		pushMessage.setFromUserName(all.getToUserName());
		pushMessage.setToUserName(all.getFromUserName());
		pushMessage.setMsgType("text");
		pushMessage.setCreateTime(Timestamp.valueOf(LocalDateTime.now()).getTime());
		return pushMessage;
	}
}
