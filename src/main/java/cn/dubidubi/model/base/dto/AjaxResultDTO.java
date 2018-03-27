package cn.dubidubi.model.base.dto;

import java.io.Serializable;

/**
 * @author 16224
 * @Description: 登录时返回的ajax结果对象
 * @date 2018年1月10日 下午3:27:30
 */
public class AjaxResultDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer code;
	private String url;
	private String uid;

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

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Override
	public String toString() {
		return "AjaxResultDTO [code=" + code + ", url=" + url + ", uid=" + uid + "]";
	}

}
