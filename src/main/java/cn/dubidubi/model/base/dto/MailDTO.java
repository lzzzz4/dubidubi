package cn.dubidubi.model.base.dto;

import java.io.Serializable;
/**
 * @author 16224
 * @Description: 邮件传输对象
 * @date 2018年1月10日 下午3:28:04
 */
public class MailDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String mail;
	private String content;
	private String time;
	private String title;

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "MailDTO [mail=" + mail + ", content=" + content + ", time=" + time + ", title=" + title + "]";
	}

}
