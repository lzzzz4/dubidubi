package cn.dubidubi.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.scheduling.annotation.Scheduled;

import cn.dubidubi.model.xml.WxAll;
import cn.dubidubi.model.xml.WxImageAndTextMessage;

public interface NewsPyService {
	/**
	 * @Description: 执行py代码爬取数据
	 * @data :
	 * @date :2018年3月7日下午9:36:53
	 */
	void getPengPaiNews();

	/**
	 * @Description: 执行爬取腾讯py代码
	 * @data :
	 * @date :2018年3月8日下午7:09:16
	 */
	void getTencentNews();

	/**
	 * @Description: 得到要推送的图文对象
	 * @data :@return
	 * @date :2018年3月6日下午4:13:40
	 */
	WxImageAndTextMessage getPushNews(WxAll all, int code);

	/**
	 * @Description: 在第二天将明天的数据删除
	 * @data :
	 * @date :2018年3月7日下午9:37:29
	 */
	void deleteNewsByTime();
}