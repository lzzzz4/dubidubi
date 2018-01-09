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
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.util.Base64;

import cn.dubidubi.model.base.UserDO;
import cn.dubidubi.model.base.dto.AjaxResultDTO;
import cn.dubidubi.model.base.dto.UserLoginDTO;
import cn.dubidubi.service.base.LoginCookieService;

@Controller
@RequestMapping("/login")
/**
 * @author 16224
 * @Description: 登录模块
 * @date 2018年1月9日 下午12:40:11
 */
public class LoginController extends BaseExceptionController {
	@Autowired
	LoginCookieService loginCookieService;

	// 默认跳转路径
	private static final String defaultPath = "redirect:/deployment/index.do";
	// 认证失败的跳转路径
	private static final String defeatPath = "login";

	/**
	 * @Description: ajax方式访问url 
	 * 404 认证错误 
	 * 403 账户被锁定错误 
	 * 500 无上传对象错误 
	 * 200 成功
	 * @return ajax返回值对象
	 * @throws IOException 
	 */
	@RequestMapping(value = "/doLogin", headers = "X-Requested-With=XMLHttpRequest")
	@ResponseBody
	public AjaxResultDTO ajaxLogin(UserLoginDTO userLoginDTO, HttpServletRequest request,HttpServletResponse response)
			throws AuthorizationException, IOException {
		AjaxResultDTO ajaxResultDTO = new AjaxResultDTO();
		Subject subject = SecurityUtils.getSubject();
		if (StringUtils.isNotBlank(userLoginDTO.getAccount()) && StringUtils.isNotBlank(userLoginDTO.getPassword())) {
			UsernamePasswordToken token = new UsernamePasswordToken(userLoginDTO.getAccount(),
					userLoginDTO.getPassword());
			// 调取realm
			try {
				subject.login(token);
			} catch (LockedAccountException e) {
				// 账户被锁定
				ajaxResultDTO.setCode(403);
				e.printStackTrace();
				return ajaxResultDTO;
			} catch (AuthenticationException e) {
				// 认证错误
				ajaxResultDTO.setCode(404);
				e.printStackTrace();
				return ajaxResultDTO;
			}
		} else {
			// 无上传数值错误
			ajaxResultDTO.setCode(500);
		}
		// 往session中放入用户数据
		UserDO userDO = (UserDO) subject.getPrincipal();
		request.getSession().setAttribute("user", userDO);
		// 设置状态为成功
		ajaxResultDTO.setCode(200);
		// 设置cookie
		loginCookieService.addLoginCookie(userLoginDTO, response);
		// 得到跳转前的url
		SavedRequest savedRequest = WebUtils.getSavedRequest(request);
		// 当savedrequest对象为空
		if (savedRequest == null) {
			ajaxResultDTO.setUrl(defaultPath);
		}
		String URL = savedRequest.getRequestUrl();
		// 判断url是否为空
		if (URL != null) {
			int URLStart = URL.indexOf("/", 1);
			String realURL = URL.substring(URLStart, URL.length());
			ajaxResultDTO.setUrl(realURL);
		} else {
			ajaxResultDTO.setUrl(defaultPath);
		}
		return ajaxResultDTO;
	}

	/**
	 * @Description: 直接访问与cookie请求的url
	 * @param userLoginDTO
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@RequestMapping("/doLogin")
	public String doLogin(UserLoginDTO userLoginDTO, Model model, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ClassNotFoundException, AuthorizationException {
		boolean isSavedCookie = false;
		if (userLoginDTO == null || StringUtils.isBlank(userLoginDTO.getAccount())
				|| StringUtils.isBlank(userLoginDTO.getPassword())) {
			userLoginDTO = loginCookieService.getUserFromCookies(request.getCookies());
			if (userLoginDTO == null) {
				return defeatPath;
			}
			isSavedCookie = true;
		}
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(userLoginDTO.getAccount(), userLoginDTO.getPassword());
		try {
			subject.login(token);
		} catch (LockedAccountException e) {
			e.printStackTrace();
			model.addAttribute("locked", "Y");
			return defeatPath;
		} catch (AuthenticationException e) {
			e.printStackTrace();
			model.addAttribute("wrong", "Y");
			return defeatPath;
		}
		UserDO userDO = (UserDO) subject.getPrincipal();
		request.getSession().setAttribute("user", userDO);
		// 将登录信息写入cookie
		if (!isSavedCookie) {
			loginCookieService.addLoginCookie(userLoginDTO, response);
		}
		SavedRequest savedRequest = WebUtils.getSavedRequest(request);
		if (savedRequest == null) {
			return defaultPath;
		}
		String URL = savedRequest.getRequestUrl();
		if (URL != null) {
			int URLStart = URL.indexOf("/", 1);
			String realURL = URL.substring(URLStart, URL.length());
			return "redirect:" + realURL;
		} else {
			return defaultPath;
		}
	}
}
