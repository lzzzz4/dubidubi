package cn.dubidubi.dao.base;

import java.util.List;

public interface RolePermissionMapper {
	List<Integer> listPermissionIdByRoleId(int roleId);
}
