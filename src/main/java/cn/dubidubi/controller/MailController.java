package cn.dubidubi.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.kevinsawicki.http.HttpRequest;

import cn.dubidubi.model.MailInfo;
import cn.dubidubi.model.base.dto.MailDTO;
import cn.dubidubi.model.dto.MailFromWxDTO;
import cn.dubidubi.model.dto.WxInfoDTO;
import cn.dubidubi.model.json.MailBindResult;
import cn.dubidubi.service.MailService;
import cn.dubidubi.service.WxBaseService;
import cn.dubidubi.util.MailUtils;
import cn.dubidubi.util.wx.WxProperties;

/**
 * @author linzj
 * @Description:用于邮箱绑定后台
 * @date 2018年3月9日 下午4:46:13
 */
@Controller
@RequestMapping("/mail")
public class MailController {
	@Autowired
	MailService mailService;
	@Autowired
	WxBaseService wxBaseService;

	/**
	 * @Description: 发送邮箱验证码
	 * @data :@param mailFromWxDTO
	 * @data :@param request
	 * @data :@return
	 * @date :2018年3月9日下午8:44:34
	 */
	@RequestMapping("/mailConfirm")
	@ResponseBody
	public MailBindResult mailConfirm(MailFromWxDTO mailFromWxDTO, HttpServletRequest request) {
		MailBindResult mailBindResult = new MailBindResult();
		MailDTO dto = new MailDTO();
		String code = MailUtils.randomSecurityCode();
		dto.setContent(code);
		dto.setMail(mailFromWxDTO.getMail());
		dto.setTitle("欢迎绑定邮箱");
		request.getSession().setAttribute("code", code);
		LoggerFactory.getLogger(this.getClass()).info("开始绑定邮箱:" + mailFromWxDTO.getMail());
		// 将正确的验证码放入对象
		MailUtils.sendMail(dto);
		System.out.println("验证码为" + code);
		mailBindResult.setResult(code);
		return mailBindResult;
	}

	/**
	 * @Description: 增加邮箱
	 * @data :@return
	 * @date :2018年3月10日上午11:56:58
	 */
	@RequestMapping("/addMail")
	@ResponseBody
	public MailBindResult addMail(MailInfo mailInfo, HttpServletRequest request) {
		MailBindResult mailBindResult = new MailBindResult();
		// 储存邮箱
		WxInfoDTO wxInfoDTO = (WxInfoDTO) request.getSession().getAttribute("user");
		mailInfo.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		mailInfo.setNickName(wxInfoDTO.getNickname());
		mailInfo.setOpenId(wxInfoDTO.getOpenid());
		mailInfo.setPicUrl(wxInfoDTO.getHeadimgurl());
		mailService.saveOneMail(mailInfo);
		mailBindResult.setResult("200");
		return mailBindResult;
	}

	/**
	 * @Description: 拉取授权并跳转至index.html
	 * @data :
	 * @date :2018年3月10日下午12:05:08
	 */
	@RequestMapping("/getAuth")
	public String getAuth(String code, String state, HttpServletRequest request) {
		// 用code换取access_token
		String userInfo = wxBaseService.codeToAccessToken(code);
		JSONObject tempInfo = JSON.parseObject(userInfo);
		WxInfoDTO wxInfoDTO = new WxInfoDTO();
		wxInfoDTO.setNickname(tempInfo.getString("nickname"));
		wxInfoDTO.setCity(tempInfo.getString("city"));
		wxInfoDTO.setHeadimgurl(tempInfo.getString("headimgurl"));
		wxInfoDTO.setOpenid(tempInfo.getString("openid"));
		request.getSession().setAttribute("user", wxInfoDTO);
		LoggerFactory.getLogger(this.getClass()).info("增加邮箱,获取用户授权");
		System.out.println(wxInfoDTO);
		return "redirect:/index.html";
	}
}
