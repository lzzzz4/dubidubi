package cn.dubidubi.dao.base;

import cn.dubidubi.model.base.UserDO;

public interface UserMapper {
	//得到密码
	String getPasswordByAccount(String account);
	//得到用户对象
	UserDO getUserDOByAccount(String account);
}
