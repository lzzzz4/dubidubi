package cn.dubidubi.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import cn.dubidubi.dao.NewsDOMapper;
import cn.dubidubi.model.NewsDO;
import cn.dubidubi.model.xml.WxAll;
import cn.dubidubi.model.xml.WxImageAndTextMessage;
import cn.dubidubi.service.NewsPyService;

@Service
public class NewsPyServiceImpl implements NewsPyService {
	@Autowired
	NewsDOMapper newsDOMapper;

	/**
	 * @Description: 定时调用py代码,获取资源
	 * @data:
	 * @date: 2018年3月6日下午1:41:36
	 */
	@Override
	// 测试每30秒执行一次
	@Scheduled(cron = "0/30 * * * * ?")
	// 运行每30分钟执行一次
	// @Scheduled(cron = "* 30 * * * ?")
	public void getNews() {
		// controller中得到路径
		// String path =
		// request.getSession().getServletContext().getRealPath("");
		String path = this.getClass().getResource("/").getPath();
		int end = path.indexOf("WEB-INF");
		path = path.substring(1, end);
		System.out.println("web路径为" + path);
		URL url = this.getClass().getClassLoader().getResource("py/newsSpider.py");
		String pypath = url.getPath().substring(1);
		System.out.println(pypath);
		String[] cmd = { "python3", pypath, path + "news/" };
		try {
			Process process = Runtime.getRuntime().exec(cmd);
			InputStream inputStream = process.getInputStream();
			// 得到py的执行结果
			String textOfPy = IOUtils.toString(inputStream);
			// 等待py执行结束
			process.waitFor();
			System.out.println(textOfPy);
			IOUtils.closeQuietly(inputStream);
		} catch (IOException e) {
			LoggerFactory.getLogger(this.getClass()).warn("执行爬取澎湃python时出现异常");
			e.printStackTrace();
		} catch (InterruptedException e) {
			LoggerFactory.getLogger(this.getClass()).warn("执行爬取澎湃python时出现异常");
			e.printStackTrace();
		}
	}

	/**
	 * @Description: 得到图文对象
	 * @data: @return
	 * @date: 2018年3月6日下午4:14:02
	 */
	@Override
	public WxImageAndTextMessage getPushNews(WxAll wxAll) {
		NewsDO newsDO = newsDOMapper.selectByPrimaryKey(1200);
		WxImageAndTextMessage wxImageAndTextMessage = new WxImageAndTextMessage();
		wxImageAndTextMessage.setArticleCount(1);
		wxImageAndTextMessage.setFromUserName(wxAll.getToUserName());
		wxImageAndTextMessage.setMsgType("news");
		wxImageAndTextMessage.setToUserName(wxAll.getFromUserName());
		wxImageAndTextMessage.setCreateTime(Timestamp.valueOf(LocalDateTime.now()).getTime());
		List<NewsDO> list = new ArrayList<>();
		list.add(newsDO);
		wxImageAndTextMessage.setArticles(list);
		return wxImageAndTextMessage;
	}
}
