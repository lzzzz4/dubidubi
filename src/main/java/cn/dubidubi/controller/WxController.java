package cn.dubidubi.controller;

import java.io.IOException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thoughtworks.xstream.XStream;

import cn.dubidubi.model.dto.WXUrlParameter;
import cn.dubidubi.model.xml.WxAll;
import cn.dubidubi.service.LocationService;
import cn.dubidubi.service.MessageService;
import cn.dubidubi.util.SerializeXmlUtil;

@Controller
@RequestMapping("/url")
public class WxController {
	@Autowired
	LocationService locationService;
	@Autowired
	MessageService messageService;

	// 校验接口是否通
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	public void urlCheck(WXUrlParameter parameter, HttpServletResponse response) {
		System.out.println(parameter.getEchostr());
		try {
			response.getWriter().write(parameter.getEchostr());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 通用接口,微信公众号的所有响应都会发至此
	@RequestMapping(value = "/check", method = RequestMethod.POST, produces = "application/xml;charset=utf-8")
	@ResponseBody
	public String index(HttpServletRequest request, HttpServletResponse response) {
		String xml = null;
		String rxml = "";
		try (ServletInputStream inputStream = request.getInputStream()) {
			xml = IOUtils.toString(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 将xml转化为对象
		XStream xs = SerializeXmlUtil.createXstream();
		xs.processAnnotations(WxAll.class);
		xs.alias("xml", WxAll.class);
		WxAll content = (WxAll) xs.fromXML(xml);
		// 当种类为事件时
		if ("event".equals(content.getMsgType())) {
			// 当事件为地址时
			if ("LOCATION".equals(content.getEvent())) {
				locationService.saveOneLocation(content);
				return rxml;
			}
			// 当事件为点击按钮时
			if ("CLICK".equals(content.getEvent())) {
				try {
					rxml = messageService.pushMessage(locationService.getPushMessage(content));
				} catch (IOException e) {
					e.printStackTrace();
				}
				return rxml;
			}
		}
		return rxml;
	}
}
