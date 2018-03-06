package cn.dubidubi.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
public class newsController{
	@Scheduled(cron = "30 * * * * ?")
	public void getNews(HttpServletRequest request) {
		String path = request.getSession().getServletContext().getRealPath("");
		System.out.println("web路径为" + path);
		URL url = this.getClass().getClassLoader().getResource("py/newsSpider.py");
		String pypath = url.getPath().substring(1);
		System.out.println(pypath);
		String[] cmd = { "python", pypath, path + "news/" };
		try {
			Process process = Runtime.getRuntime().exec(cmd);
			InputStream inputStream = process.getInputStream();
			String textOfPy = IOUtils.toString(inputStream);
			process.waitFor();
			System.out.println("py结果" + textOfPy);
			IOUtils.closeQuietly(inputStream);
		} catch (IOException e) {
			LoggerFactory.getLogger(this.getClass()).info("执行爬取澎湃python时出现异常");
			e.printStackTrace();
		} catch (InterruptedException e) {
			LoggerFactory.getLogger(this.getClass()).info("执行爬取澎湃python时出现异常");
			e.printStackTrace();
		}
	}
}
