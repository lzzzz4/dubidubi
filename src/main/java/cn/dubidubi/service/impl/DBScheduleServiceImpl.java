package cn.dubidubi.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.dubidubi.service.DBScheduleService;
import cn.dubidubi.service.HistoryNewsService;
import cn.dubidubi.service.NewsPyService;

/**
 * @author linzj
 * @Description: 数据库定时处理
 * @date 2018年3月7日 下午2:11:18
 */
@Service
public class DBScheduleServiceImpl implements DBScheduleService {
	private static String sqlStorePath = "H:/";
	@Autowired
	private NewsPyService newsPyService;
	@Autowired
	private HistoryNewsService historyNewsService;

	/**
	 * @Description: 将news_info的数据移入hi_news_info
	 * @data :
	 * @date :2018年3月7日下午2:13:07
	 */
	@Override
	@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, readOnly = false)
	@Scheduled(cron = "0 0 0 * * ?")
	public void NewsdeleteAndMove() {
		// 先移动,后删除
		historyNewsService.saveAllNewsOfyesterday();
		newsPyService.deleteNewsByTime();
		LoggerFactory.getLogger(this.getClass()).info("将数据移至历史表");
	}

	/**
	 * @Description: windows下执行command,linux下执行sh脚本文件
	 * @data:
	 * @date: 2018年3月8日下午2:32:17
	 */
	@Override
	@Scheduled(cron = "* * 2/10 * * ?")
	public void backUpDB() {
		// bin/sh -c
		String Date = LocalDate.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd"));
		try {
			FileUtils.forceMkdir(new File(sqlStorePath + Date));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		// window下
		String cmd = "cmd /c -c mysqldump -uroot -pLinzijie123!! --databases gzh > " + sqlStorePath + Date + "/sb.sql";
		// linux下调用shell脚本
		// String cmd = "";
		try {
			Process process = Runtime.getRuntime().exec(cmd);
			InputStream inputStream = process.getInputStream();
			String log = IOUtils.toString(inputStream);
			process.waitFor();
			LoggerFactory.getLogger(this.getClass()).info("备份完成");
			IOUtils.closeQuietly(inputStream);
		} catch (IOException e) {
			LoggerFactory.getLogger(this.getClass()).warn("数据库备份错误" + sqlStorePath + Date);
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String Date = LocalDate.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd"));
		try {
			FileUtils.forceMkdir(new File(sqlStorePath + Date));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		// bin/sh -c
		String cmd = "cmd /c mysqldump -h119.29.28.81 -uroot -pLinzijie123!! --databases gzh > H:/sb.sql";

		try {
			System.out.println("开始备份1" + sqlStorePath + Date);
			Process process = Runtime.getRuntime().exec(cmd);
			System.out.println("开始备份2" + sqlStorePath + Date);
			InputStream inputStream = process.getInputStream();
			String log = IOUtils.toString(inputStream);
			System.out.println("开始备份3" + sqlStorePath + Date + log);
			process.waitFor();
			LoggerFactory.getLogger(DBScheduleService.class).info("备份完成");
			IOUtils.closeQuietly(inputStream);
		} catch (IOException e) {
			LoggerFactory.getLogger(DBScheduleService.class).warn("数据库备份错误");
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
