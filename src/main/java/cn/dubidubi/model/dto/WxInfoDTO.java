package cn.dubidubi.model.dto;

import java.io.Serializable;

public class WxInfoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String openid;
	private String nickname;
	private String city;
	private String headimgurl;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	@Override
	public String toString() {
		return "WxInfoDTO [openid=" + openid + ", nickname=" + nickname + ", city=" + city + ", headimgurl="
				+ headimgurl + "]";
	}

}
