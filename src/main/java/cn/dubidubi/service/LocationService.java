package cn.dubidubi.service;

import cn.dubidubi.model.WxLocationDO;
import cn.dubidubi.model.dto.GaoDeAddressDTO;
import cn.dubidubi.model.xml.PushMessage;
import cn.dubidubi.model.xml.WxAll;

public interface LocationService {

	GaoDeAddressDTO getCity(Double Longitude, Double Latitude);

	// Latitude=29.819321, Longitude=121.558929, Precision=220.0
	GaoDeAddressDTO getWeather(Double Longitude, Double Latitude);

	void saveOneLocation(WxAll wxAll);

	WxLocationDO getLocationByOpenId(String openId);

	PushMessage getPushMessage(WxAll all);
}