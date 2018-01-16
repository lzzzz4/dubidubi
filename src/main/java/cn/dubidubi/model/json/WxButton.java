package cn.dubidubi.model.json;

import java.io.Serializable;
/**
 * @author 16224
 * @Description: 一级按钮
 * @date 2018年1月10日 下午3:25:36
 */
public class WxButton implements Serializable{
	private static final long serialVersionUID = 1L;
	private String type;
	private String name;
	private String key;
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
	@Override
	public String toString() {
		return "WxButton [type=" + type + ", name=" + name + ", key=" + key + "]";
	}
	
	
}
