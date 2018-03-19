package cn.dubidubi.model.json;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author 16224
 * @Description: 一级按钮
 * @date 2018年1月10日 下午3:25:36
 */
public class WxButton implements Serializable {
	private static final long serialVersionUID = 1L;
	private String type;
	private String name;
	private String key;
	private String url;
	private WxButton[] sub_button;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "WxButton [type=" + type + ", name=" + name + ", key=" + key + ", url=" + url + ", sub_button="
				+ Arrays.toString(sub_button) + "]";
	}

	public WxButton[] getSub_button() {
		return sub_button;
	}

	public void setSub_button(WxButton[] sub_button) {
		this.sub_button = sub_button;
	}

}
