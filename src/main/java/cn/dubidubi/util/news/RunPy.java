package cn.dubidubi.util.news;

import java.io.IOException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;

public class RunPy {
	public static void main(String[] args) throws IOException, InterruptedException {
		URL path = RunPy.class.getClassLoader().getResource("py/requestsStudy.py");
		String filePath = StringUtils.substring(path.getPath(), 1);
		System.out.println(filePath);
		String[] strings = { "python", filePath };
		Process process = Runtime.getRuntime().exec(strings);
		System.out.println("开始执行");
		process.waitFor();
		System.out.println("结束执行");
	}
}
