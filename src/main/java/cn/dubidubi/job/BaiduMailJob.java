package cn.dubidubi.job;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import cn.dubidubi.dao.MailInfoMapper;
import cn.dubidubi.model.base.dto.MailDTO;
import cn.dubidubi.model.dto.PicUrlToBase64DTO;
import cn.dubidubi.util.MailUtils;

@DisallowConcurrentExecution
public class BaiduMailJob implements Job {
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDetail detail = context.getJobDetail();
		JobDataMap jobDataMap = detail.getJobDataMap();
		String str = jobDataMap.getString("str");

		try (ByteArrayInputStream in = new ByteArrayInputStream(Base64.decodeBase64(str));
				ObjectInputStream inputStream = new ObjectInputStream(in);) {
			PicUrlToBase64DTO pic = (PicUrlToBase64DTO) inputStream.readObject();
			System.out.println(pic);
			List<String> list = pic.getMail();
			List<String> picList = pic.getList();
			System.out.println(picList);
			MailDTO dto = new MailDTO();
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < picList.size(); i++) {
				// 为奇数
				if (i % 2 == 0 || i == 0) {
					builder.append("<img src='").append(picList.get(i)).append("'/><br/>");
				} else {
					builder.append("<h2>").append(picList.get(i)).append("</h2>");
				}
			}
			// 为订阅者的openid每个邮箱发送邮件
			for (String string : list) {
				System.out.println(string);

				dto.setMail(string);
				dto.setTitle("百度图片");
				// 发送所有的图片链接

				dto.setContent(builder.toString());
				MailUtils.sendPicMail(dto);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
