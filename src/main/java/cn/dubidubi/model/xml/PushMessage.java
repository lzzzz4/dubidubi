package cn.dubidubi.model.xml;

import java.io.Serializable;

import cn.dubidubi.util.XStreamCDATA;

/**
 * @author 16224
 * @Description: 被动回复消息的xml对象
 * @date 2018年1月9日 下午12:48:27
 */
public class PushMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	@XStreamCDATA
	private String ToUserName;
	@XStreamCDATA
	private String FromUserName;
	private Long CreateTime;
	@XStreamCDATA
	private String MsgType;
	@XStreamCDATA
	private String Content;

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

	public Long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(Long createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	@Override
	public String toString() {
		return "PushMessage [ToUserName=" + ToUserName + ", FromUserName=" + FromUserName + ", CreateTime=" + CreateTime
				+ ", MsgType=" + MsgType + ", Content=" + Content + "]";
	}

}
