package cn.dubidubi.service.impl;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dubidubi.dao.MailInfoMapper;
import cn.dubidubi.model.MailInfo;
import cn.dubidubi.service.MailService;

@Service
/**
 * @author linzj
 * @Description: 关于邮箱的业务
 * @date 2018年3月10日 下午3:18:44
 */
public class MailServiceImpl implements MailService {
	@Autowired
	MailInfoMapper mailInfoMapper;

	@Override
	public void saveOneMail(MailInfo mailInfo) {
		Integer id = mailInfoMapper.getIdByMail(mailInfo.getMail());
		if (id == null)
			mailInfoMapper.saveOneMail(mailInfo);
		else
			LoggerFactory.getLogger(this.getClass()).info("邮箱已储存过" + mailInfo.getMail());
	}
}
