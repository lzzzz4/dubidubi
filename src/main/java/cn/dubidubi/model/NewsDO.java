package cn.dubidubi.model;

import java.io.Serializable;
import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import cn.dubidubi.util.XStreamCDATA;

@XStreamAlias("item")
public class NewsDO implements Serializable {
	@XStreamOmitField
	private Integer id;

	@XStreamCDATA
	private String Title;
	@XStreamOmitField
	private String createTime;
	// 源
	@XStreamCDATA
	private String Description;
	@XStreamCDATA
	private String PicUrl;
	// 源网页
	@XStreamCDATA
	private String Url;

	private static final long serialVersionUID = 1L;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

}