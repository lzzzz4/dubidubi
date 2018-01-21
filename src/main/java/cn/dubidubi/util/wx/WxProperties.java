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
	public static String APP_Id;
	public static String APP_SECRET;
	static {
		InputStream inputStream = WxProperties.class.getClassLoader().getResourceAsStream("wx.properties");
		Properties properties = new Properties();
		try {
			properties.load(inputStream);
			APP_Id = properties.getProperty("appId");
			APP_SECRET = properties.getProperty("appsecret");
		} catch (IOException e) {
			System.out.println("io异常");
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(inputStream);
		}

	}

	public static void main(String[] args) {
		System.out.println(WxProperties.APP_Id);
	}

}
