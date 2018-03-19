package cn.dubidubi.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import cn.dubidubi.service.PyBeautifulGirlService;

/**
 * @author linzj
 * @Description: 爬取多个源的美女图片
 * @date 2018年3月13日 下午2:44:18
 */
@Service
public class PyBeautifulGirlServiceImpl implements PyBeautifulGirlService {
	/**
	 * @Description: 图片搜索函数，根据源指定函数
	 * @data :@param source
	 * @data :@param width
	 * @data :@param height
	 * @data :@param word
	 * @data :@param pn
	 * @data :@param rn
	 * @data :@return
	 * @date :2018年3月13日下午4:55:54
	 */
	@Override
	public List<String> startPy(int width, int height, String word, int pn, int rn) {
		List<String> list = null;
		list = baiduSourcePy(width, height, word, pn, rn);
		return list;
	}

	@Override
	@Async
	public void startPy(String word) {
		try {
			word = URLEncoder.encode(word, "utf-8");
			String pyPath = this.getClass().getClassLoader().getResource("py/jiumei.py").getPath();
			// 得到项目路径
			String webPath = this.getClass().getResource("/").getPath();
			String[] cmd = { "python", StringUtils.substring(pyPath, 1), word,
					StringUtils.substring(webPath, 1, webPath.indexOf("WEB-INF")) + "pic/" };
			Process process = Runtime.getRuntime().exec(cmd);
			InputStream inputStream = process.getInputStream();
			List<String> list = IOUtils.readLines(inputStream);
			process.waitFor();
			IOUtils.closeQuietly(inputStream);
			for (String string : list) {
				System.out.println(string);
			}
		} catch (UnsupportedEncodingException e) {
			LoggerFactory.getLogger(this.getClass()).warn("关键词encode错误");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @Description: 获得百度图片源,0 0表示不限制长宽
	 * @data :@param width
	 * @data :@param height
	 * @data :@return
	 * @date :2018年3月13日下午4:09:12
	 */
	private List<String> baiduSourcePy(int width, int height, String word, int pn, int rn) {
		// pn 从第几页开始 rn一页几个
		if (rn >= 8) {
			rn = 8;
		}
		// 第一页等于第0页
		if (pn < 1) {
			pn = 0;
		} else {
			pn--;
		}
		// 得到py的执行路径
		String source = this.getClass().getClassLoader().getResource("py/baiduPic.py").getPath();
		source = StringUtils.substring(source, 1);
		Process process = null;
		// 参数顺序为 word width height pn rn
		try {
			word = URLEncoder.encode(word, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String swidth = Integer.toString(width);
		String sheight = Integer.toString(height);
		if (swidth.equals("0")) {
			swidth = "no";
		}
		if (sheight.equals("0")) {
			sheight = "no";
		}
		String[] cmd = { "python", source, word, swidth, sheight, Integer.toString(pn), Integer.toString(rn) };
		try {
			process = Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 链接 标题(一组)...
		List<String> result = null; // 结果集
		try {
			InputStream inputStream = process.getInputStream();
			result = IOUtils.readLines(inputStream, "gbk");
			process.waitFor();
			for (int i = 0; i < result.size(); i++) {
				result.set(i, new String(result.get(i).getBytes(), "utf-8"));
			}
			IOUtils.closeQuietly(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		LoggerFactory.getLogger(PyBeautifulGirlServiceImpl.class).info("py爬取百度执行完成");
		return result;
	}
}
