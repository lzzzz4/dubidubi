package cn.dubidubi.controller.base;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.util.Base64;

import cn.dubidubi.model.base.UserDO;
import cn.dubidubi.model.base.dto.UserLoginDTO;
import cn.dubidubi.service.base.LoginCookieService;

@Controller
@RequestMapping("/login")

public class LoginController {
	@Autowired
	LoginCookieService loginCookieService;

	// 执行登录并判断是否被锁定
	@RequestMapping("/doLogin")
	public String doLogin(UserLoginDTO userLoginDTO, Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ClassNotFoundException {
		if (userLoginDTO == null || StringUtils.isBlank(userLoginDTO.getAccount())
				|| StringUtils.isBlank(userLoginDTO.getPassword())) {
			userLoginDTO = loginCookieService.getUserFromCookies(request.getCookies());
			if (userLoginDTO == null)
				return "login";
		}
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(userLoginDTO.getAccount(), userLoginDTO.getPassword());
		try {
			subject.login(token);
		} catch (LockedAccountException e) {
			e.printStackTrace();
			model.addAttribute("locked", "Y");
			return "login";
		} catch (AuthenticationException e) {
			e.printStackTrace();
			model.addAttribute("wrong", "Y");
			return "login";
		}
		UserDO userDO = (UserDO) subject.getPrincipal();
		request.getSession().setAttribute("user", userDO);
		loginCookieService.addLoginCookie(userLoginDTO, response);
		SavedRequest savedRequest = WebUtils.getSavedRequest(request);
		if (savedRequest == null) {
			return "index";
		}
		String URL = savedRequest.getRequestUrl();
		if (URL != null) {
			int URLStart = URL.indexOf("/", 1);
			String realURL = URL.substring(URLStart, URL.length());
			return "redirect:" + realURL;
		} else {
			return "index";
		}
	}
}
