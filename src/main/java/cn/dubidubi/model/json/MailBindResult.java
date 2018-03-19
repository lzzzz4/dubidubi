package cn.dubidubi.model.json;

import java.io.Serializable;

/**
 * @author linzj
 * @Description: 邮箱绑定的结果对象
 * @date 2018年3月9日 下午7:11:39
 */
public class MailBindResult implements Serializable {

	private static final long serialVersionUID = 1L;
	private String result; // 结果码

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
