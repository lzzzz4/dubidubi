package cn.dubidubi.util.wx;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

/**
 * @author linzj
 * @Description: 读取配置文档
 * @date 2018年1月11日 上午8:45:54
 */
public class WxProperties {
	private static String appId;
	private static String appsecret;
	static {
		InputStream inputStream = WxProperties.class.getResourceAsStream("wx.properties");
		Properties properties = new Properties();
		try {
			properties.load(inputStream);
			appId = properties.getProperty("appId");
			appsecret = properties.getProperty("appsecret");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(inputStream);
		}

	}

	public static String getAppId() {
		return appId;
	}

	public static String getAppsecret() {
		return appsecret;
	}


}
