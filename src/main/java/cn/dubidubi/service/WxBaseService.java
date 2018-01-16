package cn.dubidubi.service;



public interface WxBaseService {
	/**
	 * @Description: 每十分钟得到accesstoken
	 *
	 */
	void anyHourGetAccessToken();
	String getAccessToken();

}