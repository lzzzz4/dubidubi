package cn.dubidubi.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.ibatis.transaction.Transaction;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import cn.dubidubi.dao.NewsDOMapper;
import cn.dubidubi.model.NewsDO;
import cn.dubidubi.model.dto.NewsDTO;
import cn.dubidubi.model.xml.WxAll;
import cn.dubidubi.model.xml.WxImageAndTextMessage;
import cn.dubidubi.service.NewsPyService;

@Service
public class NewsPyServiceImpl implements NewsPyService {
	@Autowired
	NewsDOMapper newsDOMapper;

	/**
	 * @Description: 定时调用py澎湃代码,获取资源
	 * @data:
	 * @date: 2018年3月6日下午1:41:36
	 */
	@Override

	// 运行每1小时执行一次
	@Scheduled(cron = "0 0 6/1 * * ?")
	public void getPengPaiNews() {
		// controller中得到路径
		// String path =
		// request.getSession().getServletContext().getRealPath("");
		String path = this.getClass().getResource("/").getPath();
		int end = path.indexOf("WEB-INF");
		path = path.substring(1, end);
		System.out.println("web路径为" + path);
		URL url = this.getClass().getClassLoader().getResource("py/newsSpider.py");
		String pypath = url.getPath().substring(1);
		// System.out.println(pypath);
		String[] cmd = { "python", pypath, path + "news/" };
		// String cmd = "python " + pypath + " " + path + "news/";
		try {
			Process process = Runtime.getRuntime().exec(cmd);
			InputStream inputStream = process.getInputStream();
			// 得到py的执行结果
			String textOfPy = IOUtils.toString(inputStream);
			// 等待py执行结束
			process.waitFor();
			System.out.println(textOfPy);
			IOUtils.closeQuietly(inputStream);
		} catch (IOException e) {
			LoggerFactory.getLogger(this.getClass()).warn("执行爬取澎湃python时出现异常");
			e.printStackTrace();
		} catch (InterruptedException e) {
			LoggerFactory.getLogger(this.getClass()).warn("执行爬取澎湃python时出现异常");
			e.printStackTrace();
		}
	}

	/**
	 * @Description: 得到发送微信图文对象
	 * @data: @return
	 * @date: 2018年3月6日下午4:14:02
	 */
	@Override
	public WxImageAndTextMessage getPushNews(WxAll wxAll, int code) {
		NewsDTO newsDTO = new NewsDTO();
		newsDTO.setStartTime(LocalDateTime.now().withMinute(0).withSecond(0)
				.format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss")));

		newsDTO.setEndTime(LocalDateTime.now().withMinute(59).withSecond(59)
				.format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss")));
		int amount = 0;
		if (code == 100) {
			newsDTO.setUserFlag(RandomUtils.nextInt(0, 17));
			amount = RandomUtils.nextInt(1, 9);
			newsDTO.setAmount(amount);
		} else {
			newsDTO.setUserFlag(RandomUtils.nextInt(0, 1));
			amount = RandomUtils.nextInt(1, 5);
			newsDTO.setAmount(amount);
		}

		// 依据时间范围与code得到图文信息
		newsDTO.setCode(code);
		List<NewsDO> list = newsDOMapper.listByTime(newsDTO);
		// 设置上传的xml对象
		WxImageAndTextMessage wxImageAndTextMessage = new WxImageAndTextMessage();
		wxImageAndTextMessage.setArticleCount(amount);
		wxImageAndTextMessage.setFromUserName(wxAll.getToUserName());
		wxImageAndTextMessage.setMsgType("news");
		wxImageAndTextMessage.setToUserName(wxAll.getFromUserName());
		wxImageAndTextMessage.setCreateTime(Timestamp.valueOf(LocalDateTime.now()).getTime());
		// 图文信息list
		wxImageAndTextMessage.setArticles(list);
		return wxImageAndTextMessage;
	}

	public static void main(String[] args) {
		// 得到起始时间
		// LocalDateTime startDateTime =
		// LocalDateTime.now().withMinute(0).withSecond(0);
		// String startTime =
		// startDateTime.format(DateTimeFormatter.ofPattern("YYYY-MM-dd
		// HH:mm:ss"));
		//
		// // 得到尾巴时间
		// LocalDateTime endDateTime =
		// LocalDateTime.now().withMinute(59).withSecond(59);
		// String endTime =
		// endDateTime.format(DateTimeFormatter.ofPattern("YYYY-MM-dd
		// HH:mm:ss"));
		// System.out.println(startTime + "---->" + endTime);

		// 随机数范围 左闭右开
		LocalDateTime dateTime = LocalDateTime.of(2018, 1, 2, 0, 0, 0);
		System.out.println(dateTime.minusDays(1));
	}

	@Override
	/**
	 * @Description: 删除昨日的新闻
	 * @data:
	 * @date: 2018年3月7日下午9:55:34
	 */
	public void deleteNewsByTime() {
		// LocalDateTime第二天0点执行
		String yesterdayBegin = LocalDateTime.now().minusDays(1)
				.format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss"));
		String yesterdayEnd = LocalDateTime.now().minusMinutes(1)
				.format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss"));
		NewsDTO newsDTO = new NewsDTO();
		newsDTO.setStartTime(yesterdayBegin);
		newsDTO.setEndTime(yesterdayEnd);
		newsDOMapper.deleteByTime(newsDTO);
	}

	/**
	 * @Description: 执行爬取腾讯py代码
	 * @data:
	 * @date: 2018年3月8日下午7:09:41
	 */
	@Override
	@Scheduled(cron = "0 0 6/1 * * ?")
	public void getTencentNews() {
		String pyPath = this.getClass().getClassLoader().getResource("py/txSpider.py").getPath();
		String[] cmd = { "python", pyPath.substring(1) };
		try {
			Process process = Runtime.getRuntime().exec(cmd);
			InputStream inputStream = process.getInputStream();
			String result = IOUtils.toString(inputStream);
			process.waitFor();
			LoggerFactory.getLogger(this.getClass()).info("执行腾讯py代码成功" + result);
			IOUtils.closeQuietly(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
