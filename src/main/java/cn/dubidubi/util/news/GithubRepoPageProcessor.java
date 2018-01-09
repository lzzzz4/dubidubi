package cn.dubidubi.util.news;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class GithubRepoPageProcessor implements PageProcessor {
	// 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

	@Override
	// process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
	public void process(Page page) {
		List<String> strings = page.getHtml().xpath("//div[@class='news_li']/h2/a/text()").all();
		for (String string : strings) {
			System.out.println(string);
		}
	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		Spider.create(new GithubRepoPageProcessor())
				// 从"https://github.com/code4craft"开始抓
				.addUrl("http://www.thepaper.cn/")
				// 开启5个线程抓取
				.addPipeline(new ConsolePipeline()).thread(5)
				// 启动爬虫
				.run();
	}

}
