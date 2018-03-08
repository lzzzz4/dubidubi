package cn.dubidubi.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thoughtworks.xstream.XStream;

import cn.dubidubi.model.NewsDO;
import cn.dubidubi.model.WxLocationDO;
import cn.dubidubi.model.xml.PushMessage;
import cn.dubidubi.model.xml.WxAll;
import cn.dubidubi.model.xml.WxImageAndTextMessage;
import cn.dubidubi.service.LocationService;
import cn.dubidubi.service.MessageService;
import cn.dubidubi.util.SerializeXmlUtil;
/**
 * @author linzj
 * @Description:将消息对象转化为xml
 * @date 2018年3月8日 下午7:49:12
 */
@Service
public class MessageServiceImpl implements MessageService {
	@Autowired
	LocationService locationService;

	@Override
	public String getPushMessageXML(PushMessage pushMessage) throws IOException {
		XStream xs = SerializeXmlUtil.createXstream();
		xs.processAnnotations(PushMessage.class);
		xs.alias("xml", PushMessage.class);
		String xml = xs.toXML(pushMessage);
		System.out.println(xml);
		return xml;
	}

	@Override
	public String getPushMessageXML(WxImageAndTextMessage newsDo) throws IOException {
		XStream xs = SerializeXmlUtil.createXstream();
		xs.processAnnotations(WxImageAndTextMessage.class);
		xs.alias("xml", WxImageAndTextMessage.class);
		String xml = xs.toXML(newsDo);
		System.out.println(xml);
		return xml;
	}
}
