package cn.dubidubi.util;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import cn.dubidubi.model.base.dto.MailDTO;

public class MailUtils {
	public static boolean sendMail(MailDTO dto) {
		boolean isSucess = false;
		Email email = new HtmlEmail();
		email.setHostName("smtp.qq.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator("dubidubicn", "lyvihlbjmafcbdab"));
		email.setSSLOnConnect(true);
		try {
			email.setFrom("dubidubicn@qq.com");
			// 邮箱头
			email.setSubject("欢迎注册：");
			// 邮箱身体
			email.setContent("欢迎注册", "text/html;charset=utf-8");
			email.addTo(dto.getMail());
			email.send();
			isSucess = true;
		} catch (EmailException e) {
			e.printStackTrace();
		}
		return isSucess;
	}
}
