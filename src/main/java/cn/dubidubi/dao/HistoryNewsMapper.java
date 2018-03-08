package cn.dubidubi.dao;

import cn.dubidubi.model.HistoryNews;
import cn.dubidubi.model.dto.NewsDTO;

public interface HistoryNewsMapper {
	HistoryNews selectByPrimaryKey(Integer id);

	void saveNewsFromNewsInfo(NewsDTO newsDTO);
}