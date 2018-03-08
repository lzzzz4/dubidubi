package cn.dubidubi.dao;

import java.util.List;

import cn.dubidubi.model.NewsDO;
import cn.dubidubi.model.dto.NewsDTO;

public interface NewsDOMapper {
	NewsDO selectByPrimaryKey(Integer id);

	List<NewsDO> listByTime(NewsDTO newsDTO);

	void deleteByTime(NewsDTO newsDTO);
}