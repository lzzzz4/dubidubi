package cn.dubidubi.util.news;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

public class RunPy {
	public static void main(String[] args) throws IOException, InterruptedException {
		URL path = RunPy.class.getClassLoader().getResource("py/newsSpider.py");
		String filePath = StringUtils.substring(path.getPath(), 1);
		System.out.println(filePath);
		// String WEBPath =
		// request.getSession().getServletContext().getRealPath("/");
		String WEBPath = "H:/news/";
		System.out.println(WEBPath);
		String[] strings = { "python", filePath, WEBPath };
		Process process = Runtime.getRuntime().exec(strings);
		System.out.println("开始执行");
		String pystr = new String(IOUtils.toString(process.getInputStream(), Charset.forName("utf-8")));
		System.out.println(pystr);
		process.waitFor();
		System.out.println("结束执行");
		LoggerFactory.getLogger(RunPy.class).info("python爬取澎湃执行完毕");
	}

}
