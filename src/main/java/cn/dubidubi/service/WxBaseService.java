package cn.dubidubi.service;

public interface WxBaseService {
	/**
	 * @Description: 每十分钟得到accesstoken
	 *
	 */
	void anyHourGetAccessToken();

	String getAccessToken();
	/**
	 * @Description:拉取授权是，使用code换取accesstoken
	 * @data :@param code
	 * @data :@return
	 * @date :2018年3月15日下午5:03:22
	 */
	String codeToAccessToken(String code);
}