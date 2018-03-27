package cn.dubidubi.model.dto;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.Callable;

public class PicUrlToBase64DTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String cron;
	private List<String> list;
	private String openId;
	private List<String> mail;

	public List<String> getMail() {
		return mail;
	}

	public void setMail(List<String> mail) {
		this.mail = mail;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	@Override
	public String toString() {
		return "PicUrlToBase64DTO [cron=" + cron + ", list=" + list + ", openId=" + openId + ", mail=" + mail + "]";
	}

}
