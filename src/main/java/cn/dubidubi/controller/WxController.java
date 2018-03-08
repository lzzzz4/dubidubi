package cn.dubidubi.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;
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
import cn.dubidubi.service.NewsPyService;
import cn.dubidubi.util.SerializeXmlUtil;

/**
 * @author 16224
 * @Description: 接受微信响应接口
 * @date 2018年1月9日 下午3:23:06
 */
@Controller
@RequestMapping("/url")
public class WxController {
	@Autowired
	LocationService locationService;
	@Autowired
	MessageService messageService;
	@Autowired
	NewsPyService newsPyService;

	/**
	 * @Description:校验接口是否连通
	 * @data :@param parameter
	 * @data :@param response
	 * @date :2018年1月16日下午8:19:47
	 */
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	public void urlCheck(WXUrlParameter parameter, HttpServletResponse response) {
		System.out.println(parameter.getEchostr());
		try {
			response.getWriter().write(parameter.getEchostr());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @Description:通用接口，公众号xml信息都发送至此
	 * @data :@param request
	 * @data :@param response
	 * @data :@return
	 * @date :2018年1月16日下午8:19:17
	 */
	@RequestMapping(value = "/check", method = RequestMethod.POST, produces = "application/xml;charset=utf-8")
	@ResponseBody
	public String index(final HttpServletRequest request, final HttpServletResponse response) {
		String xml = null; // 微信的xml
		String rxml = "";// 响应的xml
		try (ServletInputStream inputStream = request.getInputStream()) {
			xml = IOUtils.toString(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 将xml转化为对象
		XStream xs = SerializeXmlUtil.createXstream();
		xs.processAnnotations(WxAll.class);
		xs.alias("xml", WxAll.class);
		// 得到微信推送的xml对象
		final WxAll content = (WxAll) xs.fromXML(xml);
		// 当种类为事件时
		if ("event".equals(content.getMsgType())) {
			// 当事件为地址时
			if ("LOCATION".equals(content.getEvent())) {
				locationService.saveOneLocation(content);
				return rxml;
			}
			// 当事件为点击按钮时
			if ("CLICK".equals(content.getEvent())) {
				switch (content.getEventKey()) {
				case "weather":
					try {
						rxml = messageService.getPushMessageXML(locationService.getPushMessage(content));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					break;
				case "100":
					try {
						rxml = messageService.getPushMessageXML(newsPyService.getPushNews(content, 100));
						LoggerFactory.getLogger(this.getClass()).info("发送澎湃新闻信息");
					} catch (IOException e) {
						LoggerFactory.getLogger(this.getClass()).warn("news controller错误");
						e.printStackTrace();
					}
					break;
				case "101":
					try {
						rxml = messageService.getPushMessageXML(newsPyService.getPushNews(content, 101));
						LoggerFactory.getLogger(this.getClass()).info("发送腾讯新闻信息");
					} catch (IOException e) {
						LoggerFactory.getLogger(this.getClass()).warn("news controller错误");
						e.printStackTrace();
					}
					break;
				default:
					break;
				}
				return rxml;
			}
		}
		return rxml;
	}
}
