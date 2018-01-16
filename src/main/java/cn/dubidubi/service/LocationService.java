package cn.dubidubi.service;

import cn.dubidubi.model.WxLocationDO;
import cn.dubidubi.model.dto.GaoDeAddressDTO;
import cn.dubidubi.model.xml.PushMessage;
import cn.dubidubi.model.xml.WxAll;

public interface LocationService {

	/**
	 * @Description: 得到城市对象
	 * @data :@param Longitude
	 * @data :@param Latitude
	 * @data :@return 
	 * @date :2018年1月10日下午3:22:35
	 */
	GaoDeAddressDTO getCity(Double Longitude, Double Latitude);
	
	/**
	 * @Description: 得到天气字符串
	 * @data :@param wxLocationDO
	 * @data :@return
	 * @date :2018年1月10日下午3:22:17
	 */
	String getWeather(WxLocationDO wxLocationDO);

	/**
	 * 储存位置信息，存在更新，不存在保存
	 */
	void saveOneLocation(WxAll wxAll);

	WxLocationDO getLocationByOpenId(String openId);

	/**
	 * 得到推送消息的对象
	 */
	PushMessage getPushMessage(WxAll all);

}