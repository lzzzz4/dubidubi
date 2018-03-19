package cn.dubidubi.dao;

import cn.dubidubi.model.MailInfo;

public interface MailInfoMapper {
	MailInfo selectByPrimaryKey(Integer id);

	void saveOneMail(MailInfo mailInfo);

	Integer getIdByMail(String mail);
}