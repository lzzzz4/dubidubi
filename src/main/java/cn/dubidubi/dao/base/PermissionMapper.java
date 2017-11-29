package cn.dubidubi.dao.base;

import java.util.List;

import cn.dubidubi.model.base.PermissionDO;

public interface PermissionMapper {
	List<PermissionDO> listPermissionByRole(int roleid);
}
