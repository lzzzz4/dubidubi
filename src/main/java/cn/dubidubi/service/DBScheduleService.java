package cn.dubidubi.service;

public interface DBScheduleService {

	/**
	 * @Description: 将news_info的数据移入hi_news_info
	 * @data :
	 * @date :2018年3月7日下午2:13:07
	 */
	void NewsdeleteAndMove();

	void backUpDB();
}