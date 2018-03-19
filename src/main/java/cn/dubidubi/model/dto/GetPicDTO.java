package cn.dubidubi.model.dto;

import java.io.Serializable;

/**
 * @author linzj
 * @Description: 得到用户输入的爬取图片的参数
 * @date 2018年3月15日 上午7:56:36
 */
public class GetPicDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String key;
	private String source;
	private Integer amount;
	private String time;
	private Integer width;
	private Integer height;

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "GetPicDTO [key=" + key + ", source=" + source + ", amount=" + amount + ", time=" + time + "]";
	}

}
