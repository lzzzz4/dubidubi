package cn.dubidubi.util.wx;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.utils.URLEncodedUtils;

/**
 * @author linzj
 * @Description: 读取配置文档
 * @date 2018年1月11日 上午8:45:54
 */
public class WxProperties {
	public static String APP_Id;
	public static String APP_SECRET;
	public static String Sql_Store_Path;
	public static String Mail_Url;
	public static String Pic_Url;
	static {
		InputStream inputStream = WxProperties.class.getClassLoader().getResourceAsStream("wx.properties");
		Properties properties = new Properties();
		try {
			properties.load(inputStream);
			APP_Id = properties.getProperty("appId");
			APP_SECRET = properties.getProperty("appsecret");
			Sql_Store_Path = properties.getProperty("sqlStorePath");
			Mail_Url = properties.getProperty("mailUrl");
			Pic_Url = properties.getProperty("picUrl");
		} catch (IOException e) {
			System.out.println("io异常");
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(inputStream);
		}

	}
}
