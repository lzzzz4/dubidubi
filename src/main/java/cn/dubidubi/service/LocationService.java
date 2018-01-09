package cn.dubidubi.service;

import cn.dubidubi.model.GaoDeAddress;
import cn.dubidubi.model.WxLocationDO;
import cn.dubidubi.model.PushMessage;
import cn.dubidubi.model.WxAll;

public interface LocationService {

	GaoDeAddress getCity(Double Longitude, Double Latitude);

	//Latitude=29.819321, Longitude=121.558929, Precision=220.0
	GaoDeAddress getWeather(Double Longitude, Double Latitude);
	
	void saveOneLocation(WxAll wxAll);
	
	WxLocationDO getLocationByOpenId(String openId);
	
	PushMessage getPushMessage(WxAll all);
}