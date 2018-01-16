package cn.dubidubi.model.base.dto;

import java.io.Serializable;
/**
 * @author 16224
 * @Description: 登录信息接受 传输对象
 * @date 2018年1月10日 下午3:28:19
 */
public class UserLoginDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String account;
	private String password;

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

}
