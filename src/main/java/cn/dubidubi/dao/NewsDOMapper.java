package cn.dubidubi.dao;

import cn.dubidubi.model.NewsDO;

/**
 * @author linzj
 * @Description: 操作新闻news_info 数据库
 * @date 2018年3月6日 下午6:14:10
 */
public interface NewsDOMapper {
	NewsDO selectByPrimaryKey(Integer id);
	
	
}