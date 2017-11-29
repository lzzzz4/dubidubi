package cn.dubidubi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dubidubi.dao.base.PermissionMapper;
import cn.dubidubi.dao.base.RolePermissionMapper;
import cn.dubidubi.dao.base.UserMapper;
import cn.dubidubi.dao.base.UserRoleMapper;
import cn.dubidubi.model.base.PermissionDO;
import cn.dubidubi.model.base.UserDO;
import cn.dubidubi.service.UserLoginService;

@Service

public class UserLoginServiceImpl implements UserLoginService {
	@Autowired
	UserMapper userMapper;
	@Autowired
	UserRoleMapper userRoleMapper;
	@Autowired
	RolePermissionMapper rolePermissionMapper;
	@Autowired
	PermissionMapper permissionMapper;
	@Override
	public String getPasswordByAccount(String account) {
		if (account == null) {
			return null;
		}
		String password = userMapper.getPasswordByAccount(account);
		return password;
	}

	// 得到放入session中的user对象
	@Override
	public UserDO getUserDOToSessionByAccount(String account) {
		if (account == null) {
			return null;
		}
		return userMapper.getUserDOByAccount(account);
	}

	@Override
	public Integer getRoleIdByUserId(int id) {
		Integer roleid = userRoleMapper.GetRoleIdByUserId(id);
		return roleid;
	}

	@Override
	public List<PermissionDO> listPermissionByRoleId(int roleId) {
		List<PermissionDO> list = permissionMapper.listPermissionByRole(roleId);
		return list;
	}

}
