package cn.dubidubi.service.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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
import cn.dubidubi.service.WxTempService;

/**
 * @author 16224
 * @Description:位置及定位天气api
 * @date 2018年1月9日 下午3:27:20
 */
@Service
public class LocationServiceImpl implements LocationService {
	@Autowired
	LocationMapper locationMapper;
	@Autowired
	WxTempService wxTempService;
	// 高德的api key
	static String key = "60b4499bd0230e7a86941c12881e6f51";
	static FastDateFormat fastDateFormat = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

	/**
	 * @Description: 调取高德得到城市方法
	 * @data: @param Longitude
	 * @data: @param Latitude
	 * @data: @return
	 * @date: 2018年1月10日下午3:21:57
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

	/**
	 * @Description: 得到天气预报拼接后的字符串
	 * @data: @param wxLocationDO
	 * @data: @return
	 * @date: 2018年1月10日下午3:24:24
	 */
	@Override
	public String getWeather(WxLocationDO wxLocationDO) {
		HttpRequest request = HttpRequest
				.get("http://restapi.amap.com/v3/weather/weatherInfo?key=" + key + "&city=" + wxLocationDO.getAdcode());
		String body = request.body();
		JSONObject jsonObject = JSON.parseObject(body);
		JSONArray live = jsonObject.getJSONArray("lives");
		// 雨天发送模版消息
		// if (live.getJSONObject(0).getString("weather").indexOf("雨") != -1) {
		wxTempService.sendTempMessage(live);
		// }
		StringBuilder stringBuilder = new StringBuilder();
		// 拼接天气预报字符串
		stringBuilder.append("天气:").append(live.getJSONObject(0).getString("weather")).append("\n").append("温度:")
				.append(live.getJSONObject(0).getString("temperature")).append("℃").append("\n").append("风速:")
				.append(live.getJSONObject(0).getString("windpower")).append("\n").append("发布时间:")
				.append(live.getJSONObject(0).getString("reporttime")).append("\n").append("城市:")
				.append(wxLocationDO.getAddress()).append("\n");
		return stringBuilder.toString();
	}

	/**
	 * 储存位置信息，存在更新，不存在保存
	 */
	@Override
	@Async
	public void saveOneLocation(WxAll wxAll) {
		WxLocationDO location = new WxLocationDO();
		Date date = new Date(wxAll.getCreateTime() * 1000);
		String createTime = fastDateFormat.format(date);
		location.setCreateTime(createTime);
		location.setFromUserName(wxAll.getFromUserName());
		location.setLatitude(wxAll.getLatitude());
		location.setLongitude(wxAll.getLongitude());
		// 调用高德获取城市api,获得高德城市信息
		GaoDeAddressDTO gaoDeAddressDTO = getCity(wxAll.getLongitude(), wxAll.getLatitude());
		location.setAddress(gaoDeAddressDTO.getFormatted_address());
		location.setAdcode(gaoDeAddressDTO.getAdcode());
		// 查询对于该openid是否存在地址信息
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

	/**
	 * 得到推送消息的对象
	 */
	@Override
	public PushMessage getPushMessage(WxAll all) {
		PushMessage pushMessage = new PushMessage();
		WxLocationDO location = getLocationByOpenId(all.getFromUserName());
		pushMessage.setContent(getWeather(location));
		pushMessage.setFromUserName(all.getToUserName());
		pushMessage.setToUserName(all.getFromUserName());
		pushMessage.setMsgType("text");
		pushMessage.setCreateTime(Timestamp.valueOf(LocalDateTime.now()).getTime());
		return pushMessage;
	}

}
