package cn.dubidubi.util;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import cn.dubidubi.model.base.dto.MailDTO;

/**
 * @author linzj
 * @Description: 邮件发送api
 * @date 2018年3月9日 下午8:25:41
 */
public class MailUtils {
	public static boolean sendMail(MailDTO dto) {
		boolean isSucess = false;
		Email email = new HtmlEmail();
		email.setHostName("smtp.qq.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator("1622472966@qq.com", "lyvihlbjmafcbdab"));
		email.setSSLOnConnect(true);
		try {
			// 发件人
			email.setFrom("1622472966@qq.com");
			// 邮箱头
			email.setSubject(dto.getTitle());
			// 邮箱身体
			email.setContent("<h2>hhhh公司的验证码</h2><br><p><strong>验证码为" + dto.getContent() + "</strong></p><br>谢谢您的姿瓷",
					"text/html;charset=utf-8");
			// email.setContent("<img
			// src='http://img.99mm.net/small/2017/2389.jpg'></img>",
			// "text/html;charset=utf-8");
			email.addTo(dto.getMail());
			email.send();
			isSucess = true;
		} catch (EmailException e) {
			e.printStackTrace();
		}
		return isSucess;
	}

	public static boolean sendPicMail(MailDTO dto) {
		boolean isSucess = false;
		Email email = new HtmlEmail();
		email.setHostName("smtp.qq.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator("1622472966@qq.com", "lyvihlbjmafcbdab"));
		email.setSSLOnConnect(true);
		try {
			// 发件人
			email.setFrom("1622472966@qq.com");
			// 邮箱头
			email.setSubject(dto.getTitle());
			// 邮箱身体
			email.setContent(dto.getContent(), "text/html;charset=utf-8");
			email.addTo(dto.getMail());
			email.send();
			isSucess = true;
		} catch (EmailException e) {
			e.printStackTrace();
		}
		return isSucess;
	}

	/**
	 * @Description: 生成随机数字验证码
	 * @data :@return
	 * @date :2018年3月9日下午8:31:46
	 */
	public static String randomSecurityCode() {
		return Integer.toString(RandomUtils.nextInt(1000, 10000));
	}
}
