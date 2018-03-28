package cn.dubidubi.job;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cn.dubidubi.model.base.dto.MailDTO;
import cn.dubidubi.model.dto.PicUrlToBase64DTO;
import cn.dubidubi.util.MailUtils;

@DisallowConcurrentExecution
public class MailJob implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// 得到传入的jobDataMap,每次执行任务需要 图片的地址与发件人的地址
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		String str = jobDataMap.getString("str");
		try (ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(Base64.decodeBase64(str));
				ObjectInputStream objectInputStream = new ObjectInputStream(arrayInputStream);) {
			PicUrlToBase64DTO toBase64DTO = (PicUrlToBase64DTO) objectInputStream.readObject();
			List<String> mail = toBase64DTO.getMail();
			int length = mail.size();
			for (int i = 0; i < length; i++) {
				MailDTO dto = new MailDTO();
				dto.setMail(mail.get(i));
				dto.setTitle("欢迎订阅图片服务");
				// 得到所有的图片链接
				List<String> list = toBase64DTO.getList();
				StringBuilder builder = new StringBuilder();
				for (String string : list) {
					builder.append("<img src='" + string + "' />");
				}
				dto.setContent(builder.toString());
				MailUtils.sendPicMail(dto);
			}
		} catch (IOException e) {

			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}

	}

}
