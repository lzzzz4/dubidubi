package cn.dubidubi.model.base;

import java.io.Serializable;
/**
 * @author 16224
 * @Description: 用户对象
 * @date 2018年1月10日 下午3:27:09
 */
public class UserDO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String account;
	private String password;
	private String username;
	private String enabled;
	private String info;
	private String salt;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Override
	public String toString() {
		return "UserDO [id=" + id + ", account=" + account + ", password=" + password + ", username=" + username
				+ ", enabled=" + enabled + ", info=" + info + ", salt=" + salt + "]";
	}

}
