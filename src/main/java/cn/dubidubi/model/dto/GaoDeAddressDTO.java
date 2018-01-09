package cn.dubidubi.model.dto;

import java.io.Serializable;
/**
 * 高德地图api获得地址
 * @author 16224
 *
 */
public class GaoDeAddressDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String formatted_address;
	private String adcode;
	private String weather;
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
	
	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	@Override
	public String toString() {
		return "GaoDeAddress [formatted_address=" + formatted_address + ", adcode=" + adcode + ", weather=" + weather
				+ "]";
	}

	
}
