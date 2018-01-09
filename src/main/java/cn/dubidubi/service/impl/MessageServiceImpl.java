package cn.dubidubi.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thoughtworks.xstream.XStream;

import cn.dubidubi.model.WxLocationDO;
import cn.dubidubi.model.xml.PushMessage;
import cn.dubidubi.model.xml.WxAll;
import cn.dubidubi.service.LocationService;
import cn.dubidubi.service.MessageService;
import cn.dubidubi.util.SerializeXmlUtil;

@Service
public class MessageServiceImpl implements MessageService {
	@Autowired
	LocationService locationService;

	@Override
	public String pushMessage(PushMessage pushMessage) throws IOException {
		XStream xs = SerializeXmlUtil.createXstream();
		xs.processAnnotations(PushMessage.class);
		xs.alias("xml", PushMessage.class);
		String xml = xs.toXML(pushMessage);
		System.out.println(xml);
		return xml;
	}
}