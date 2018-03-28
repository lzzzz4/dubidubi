package cn.dubidubi.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.http.client.utils.URLEncodedUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.mysql.fabric.xmlrpc.base.Data;

import cn.dubidubi.dao.MailInfoMapper;
import cn.dubidubi.job.MailJob;
import cn.dubidubi.model.dto.PicUrlToBase64DTO;
import cn.dubidubi.service.PyBeautifulGirlService;
import cn.dubidubi.util.quartz.Quartz;

/**
 * @author linzj
 * @Description: 爬取多个源的美女图片
 * @date 2018年3月13日 下午2:44:18
 */
@Service
public class PyBeautifulGirlServiceImpl implements PyBeautifulGirlService {
	@Autowired
	MailInfoMapper mailInfoMapper;

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
	public Future<PicUrlToBase64DTO> startPy(String word, String uid, String time) {
		List<String> list = null;
		try {
			word = URLEncoder.encode(word, "utf-8");
			String pyPath = this.getClass().getClassLoader().getResource("py/jiumei.py").getPath();
			// 得到项目路径
			String webPath = this.getClass().getResource("/").getPath();
			String[] cmd = { "python", StringUtils.substring(pyPath, 1), word,
					StringUtils.substring(webPath, 1, webPath.indexOf("WEB-INF")) + "pic/", uid };
			Process process = Runtime.getRuntime().exec(cmd);
			InputStream inputStream = process.getInputStream();
			list = IOUtils.readLines(inputStream);
			process.waitFor();
			IOUtils.closeQuietly(inputStream);
		} catch (UnsupportedEncodingException e) {
			LoggerFactory.getLogger(this.getClass()).warn("关键词encode错误");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		PicUrlToBase64DTO base64dto = new PicUrlToBase64DTO();
		base64dto.setList(list);
		base64dto.setOpenId(uid);
		base64dto.setCron(dateToCron(time));
		return new AsyncResult<PicUrlToBase64DTO>(base64dto);

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

	@Async
	@Override
	public void waitForComplete(Future<PicUrlToBase64DTO> future)
			throws InterruptedException, ExecutionException, IOException {
		while (true) {
			// 爬取九妹的java代码执行完成
			// startpy在新开的一个线程中执行，因此不会干扰主线程的执行，监听startpy是否完成,
			// 完成的话获取其中的值,并将该值存入quartz中
			if (future.isDone()) {
				PicUrlToBase64DTO base64dto = future.get();
				// 获得目标邮箱
				base64dto.setMail(mailInfoMapper.listMailByOpenid(base64dto.getOpenId()));
				ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(arrayOutputStream);
				objectOutputStream.writeObject(base64dto);
				// 得到了对象的序列化数据
				String str = Base64.encodeBase64URLSafeString(arrayOutputStream.toByteArray());
				String openId = base64dto.getOpenId();
				Quartz.addJob(randomString(openId, "job"), randomString(openId, "jobGroup"),
						randomString(openId, "trigger"), randomString(openId, "triggerGroup"), MailJob.class,
						base64dto.getCron(), "str", str);
				break;
			}
		}
	}

	/**
	 * @Description: 将日期转化为cron字符串
	 * @data :@param date
	 * @data :@return
	 * @date :2018年3月24日下午1:23:51
	 */
	@Override
	public String dateToCron(String date) {
		// 日期样式为 2018-03-24T13:23
		// System.out.println(date);
		String[] dateTime = date.split("T");
		// 操作时间
		String[] time = dateTime[1].split(":");
		StringBuilder builder = new StringBuilder();
		// 拼接前部
		builder.append("0 ").append(time[1] + " ").append(time[0] + " ");
		String[] date1 = dateTime[0].split("-");
		builder.append(date1[2] + " ").append(date1[1] + " ").append("? ");
		System.out.println(builder.toString());
		return builder.toString();
	}

	@Override
	public String randomString(String openId, String tail) {
		return RandomStringUtils.randomAlphabetic(5) + StringUtils.substring(openId, 2, 5) + tail;
	}
}
