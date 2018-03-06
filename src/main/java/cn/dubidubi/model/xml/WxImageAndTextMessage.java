package cn.dubidubi.model.xml;

import java.io.Serializable;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import cn.dubidubi.model.NewsDO;
import cn.dubidubi.util.XStreamCDATA;

@XStreamAlias("xml")
public class WxImageAndTextMessage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XStreamCDATA
	private String ToUserName;
	@XStreamCDATA
	private String FromUserName;
	private Long CreateTime;
	@XStreamCDATA
	private String MsgType;
	private Integer ArticleCount;
	private List<NewsDO> Articles;

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

	public Integer getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(Integer articleCount) {
		ArticleCount = articleCount;
	}

	public List<NewsDO> getArticles() {
		return Articles;
	}

	public void setArticles(List<NewsDO> articles) {
		Articles = articles;
	}

}
