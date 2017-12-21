package cn.dubidubi.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.dubidubi.model.dto.WXUrlParameter;

@Controller
@RequestMapping("/url")
public class WxUrlCheckController {
	@RequestMapping(value = "/check")
	public void urlCheck(WXUrlParameter parameter, HttpServletResponse response) {
		System.out.println(parameter.getEchostr());
		try {
			response.getWriter().write(parameter.getEchostr());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
