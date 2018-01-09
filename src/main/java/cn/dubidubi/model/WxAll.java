package cn.dubidubi.model;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 全部的微信xml对象
 * @author 16224
 *
 */
@XStreamAlias("xml")
public class WxAll implements Serializable {
	private static final long serialVersionUID = 1L;
	private String ToUserName;
	private String FromUserName;
	private String CreateTime;
	private String MsgType;
	private String Event;
	private Double Latitude;
	private Double Longitude;
	private Double Precision;
	private String EventKey;

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public String getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

	public String getEvent() {
		return Event;
	}

	public void setEvent(String event) {
		Event = event;
	}

	public Double getLatitude() {
		return Latitude;
	}

	public void setLatitude(Double latitude) {
		Latitude = latitude;
	}

	public Double getLongitude() {
		return Longitude;
	}

	public void setLongitude(Double longitude) {
		Longitude = longitude;
	}

	public Double getPrecision() {
		return Precision;
	}

	public void setPrecision(Double precision) {
		Precision = precision;
	}

	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}

	@Override
	public String toString() {
		return "WxAll [ToUserName=" + ToUserName + ", FromUserName=" + FromUserName + ", CreateTime=" + CreateTime
				+ ", MsgType=" + MsgType + ", Event=" + Event + ", Latitude=" + Latitude + ", Longitude=" + Longitude
				+ ", Precision=" + Precision + ", EventKey=" + EventKey + "]";
	}

}
