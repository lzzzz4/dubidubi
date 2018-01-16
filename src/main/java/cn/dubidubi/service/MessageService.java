package cn.dubidubi.service;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import cn.dubidubi.model.xml.PushMessage;

public interface MessageService {
	/**
	 * @Description: 得到推送消息的xml字符串
	 * @param pushMessage
	 * @return
	 * @throws IOException
	 */
	String getPushMessageXML(PushMessage pushMessage) throws IOException;

}