package cn.dubidubi.model.json;

import java.io.Serializable;

/**
 * @author linzj
 * @Description: 模版消息实体类
 * @date 2018年3月30日 下午1:02:37
 */
public class WxTemp implements Serializable {
	private static final long serialVersionUID = 1L;
	private String touser;
	private String template_id;
	private String url;
	private String topcolor;
	private WxWeatherTempData data;

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTopcolor() {
		return topcolor;
	}

	public void setTopcolor(String topcolor) {
		this.topcolor = topcolor;
	}

	public WxWeatherTempData getData() {
		return data;
	}

	public void setData(WxWeatherTempData data) {
		this.data = data;
	}

}
