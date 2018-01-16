package cn.dubidubi.model.json;

import java.io.Serializable;
/**
 * @author 16224
 * @Description: 菜单类实体对象,包括多个按钮
 * @date 2018年1月10日 下午3:25:50
 */
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
