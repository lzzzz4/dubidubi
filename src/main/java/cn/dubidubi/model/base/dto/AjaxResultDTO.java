package cn.dubidubi.model.base.dto;

import java.io.Serializable;

public class AjaxResultDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer code;
	private String url;
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
