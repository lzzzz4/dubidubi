package cn.dubidubi.model.json;

import java.io.Serializable;

public class WxWeatherTempData implements Serializable {
	private static final long serialVersionUID = 1L;
	private WxTempOne user;
	private WxTempOne weather;
	private WxTempOne temp; // 温度
	private WxTempOne time;

	public WxTempOne getUser() {
		return user;
	}

	public void setUser(WxTempOne user) {
		this.user = user;
	}

	public WxTempOne getWeather() {
		return weather;
	}

	public void setWeather(WxTempOne weather) {
		this.weather = weather;
	}

	public WxTempOne getTemp() {
		return temp;
	}

	public void setTemp(WxTempOne temp) {
		this.temp = temp;
	}

	public WxTempOne getTime() {
		return time;
	}

	public void setTime(WxTempOne time) {
		this.time = time;
	}

}
