package cn.dubidubi.model.dto;

import java.io.Serializable;

/**
 * 高德地图api获得地址的储存实体
 * 
 * @author 16224
 *
 */
public class GaoDeAddressDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String formatted_address;
	private String adcode;

	public String getFormatted_address() {
		return formatted_address;
	}

	public void setFormatted_address(String formatted_address) {
		this.formatted_address = formatted_address;
	}

	public String getAdcode() {
		return adcode;
	}

	public void setAdcode(String adcode) {
		this.adcode = adcode;
	}

	@Override
	public String toString() {
		return "GaoDeAddressDTO [formatted_address=" + formatted_address + ", adcode=" + adcode + "]";
	}

}
