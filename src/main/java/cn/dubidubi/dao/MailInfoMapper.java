package cn.dubidubi.dao;

import java.util.List;

import cn.dubidubi.model.MailInfo;
import cn.dubidubi.model.dto.PicUrlToBase64DTO;

public interface MailInfoMapper {
	MailInfo selectByPrimaryKey(Integer id);

	void saveOneMail(MailInfo mailInfo);

	Integer getIdByMail(String mail);

	List<String> listMailByOpenid(String openId);

}