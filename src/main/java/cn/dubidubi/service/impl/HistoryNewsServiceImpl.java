package cn.dubidubi.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dubidubi.dao.HistoryNewsMapper;
import cn.dubidubi.model.dto.NewsDTO;
import cn.dubidubi.service.HistoryNewsService;

/**
 * @author linzj
 * @Description: 新闻的历史记录
 * @date 2018年3月8日 上午9:08:37
 */
@Service
public class HistoryNewsServiceImpl implements HistoryNewsService {
	@Autowired
	private HistoryNewsMapper historyNewsMapper;

	/**
	 * @Description: 保存昨天所有的数据至hi_news_info
	 * @data :
	 * @date :2018年3月8日上午9:10:29
	 */
	@Override
	public void saveAllNewsOfyesterday() {
		String yesterdayBegin = LocalDateTime.now().minusDays(1)
				.format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss"));
		String yesterdayEnd = LocalDateTime.now().minusMinutes(1)
				.format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss"));
		NewsDTO newsDTO = new NewsDTO();
		newsDTO.setStartTime(yesterdayBegin);
		newsDTO.setEndTime(yesterdayEnd);
		historyNewsMapper.saveNewsFromNewsInfo(newsDTO);
	}
}
