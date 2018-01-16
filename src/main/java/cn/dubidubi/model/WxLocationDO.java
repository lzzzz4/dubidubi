package cn.dubidubi.model;

import java.io.Serializable;

/**
 * @author 16224
 * @Description: 微信储存到库中的定位对象
 * @date 2018年1月9日 下午12:47:06
 */
public class WxLocationDO implements Serializable {
	private Integer id;

	private String fromUserName;

	private Double longitude;

	private Double latitude;

	private String createTime;

	private String address;
	private String adcode;
	private static final long serialVersionUID = 1L;
	public String getAdcode() {
		return adcode;
	}

	public void setAdcode(String adcode) {
		this.adcode = adcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName == null ? null : fromUserName.trim();
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime == null ? null : createTime.trim();
	}
}