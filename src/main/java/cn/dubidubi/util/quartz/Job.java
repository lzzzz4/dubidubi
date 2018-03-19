package cn.dubidubi.util.quartz;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

@DisallowConcurrentExecution
public class Job implements org.quartz.Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDetail detail = context.getJobDetail();
		JobDataMap dataMap = detail.getJobDataMap();
		String str = dataMap.getString("hh");
		if (str == null) {
			System.out.println(LocalDateTime.now() + "执行,睡眠5秒 str为空");
			dataMap.put("hello", "hello");
		} else {
			dataMap.put("hello", RandomStringUtils.randomAlphabetic(5));
			System.out.println(LocalDateTime.now() + "执行,睡眠5秒" + dataMap.getString("hello"));
		}
		// try {
		// Thread.sleep(5000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
	}

}
