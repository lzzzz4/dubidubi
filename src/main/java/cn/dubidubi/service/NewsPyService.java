package cn.dubidubi.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.scheduling.annotation.Scheduled;

import cn.dubidubi.model.xml.WxAll;
import cn.dubidubi.model.xml.WxImageAndTextMessage;

public interface NewsPyService {

	void getNews();

	/**
	 * @Description: 得到要推送的图文对象
	 * @data :@return
	 * @date :2018年3月6日下午4:13:40
	 */
	WxImageAndTextMessage getPushNews(WxAll all);
}