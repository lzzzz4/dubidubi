package cn.dubidubi.model.base;

import java.io.Serializable;

public class PermissionDO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String remark;
	private String enabled;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "PermissionDO [id=" + id + ", remark=" + remark + ", enabled=" + enabled + "]";
	}

}
