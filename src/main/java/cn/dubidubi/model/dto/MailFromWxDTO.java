package cn.dubidubi.model.dto;

import java.io.Serializable;

/**
 * @author linzj
 * @Description:邮件的传输对象
 * @date 2018年3月9日 下午7:27:31
 */
public class MailFromWxDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String openId;
	private String mail;
	private String code;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
