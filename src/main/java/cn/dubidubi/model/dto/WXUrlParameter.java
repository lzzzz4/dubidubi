package cn.dubidubi.model.dto;
/**
 * @author 16224
 * @Description: 验证url接口的 get参数对象
 * @date 2018年1月10日 下午3:26:31
 */
public class WXUrlParameter {
	private String signature;
	private String timestamp;
	private String nonce;
	private String echostr;

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public String getEchostr() {
		return echostr;
	}

	public void setEchostr(String echostr) {
		this.echostr = echostr;
	}

	@Override
	public String toString() {
		return "WXUrlParameter [signature=" + signature + ", timestamp=" + timestamp + ", nonce=" + nonce + ", echostr="
				+ echostr + "]";
	}

}
