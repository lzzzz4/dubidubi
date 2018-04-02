package cn.dubidubi.model.json;

import java.io.Serializable;

public class WxTempOne implements Serializable {
	private static final long serialVersionUID = 1L;
	private String value;
	private String color;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "WxTempOne [value=" + value + ", color=" + color + "]";
	}

}
