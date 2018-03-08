package cn.dubidubi.model;

import java.io.Serializable;
import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("item")
public class NewsDO implements Serializable {
	@XStreamOmitField
	private Integer id;
	@XStreamAlias("Title")
	private String title;
	@XStreamOmitField
	private Date createTime;
	@XStreamAlias("PicUrl")
	private String imgUrl;
	@XStreamAlias("Url")
	private String originalWebUrl;
	@XStreamAlias("Description")
	private String source;
	@XStreamOmitField
	private Integer sourceId;

	private static final long serialVersionUID = 1L;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl == null ? null : imgUrl.trim();
	}

	public String getOriginalWebUrl() {
		return originalWebUrl;
	}

	public void setOriginalWebUrl(String originalWebUrl) {
		this.originalWebUrl = originalWebUrl == null ? null : originalWebUrl.trim();
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source == null ? null : source.trim();
	}

	public Integer getSourceId() {
		return sourceId;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}
}