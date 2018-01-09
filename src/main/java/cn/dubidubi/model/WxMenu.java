package cn.dubidubi.model;

import java.io.Serializable;

public class WxMenu implements Serializable{
	private static final long serialVersionUID = 1L;
	private WxButton[] button;
	public WxButton[] getButton() {
		return button;
	}
	public void setButton(WxButton[] button) {
		this.button = button;
	}
	
}
