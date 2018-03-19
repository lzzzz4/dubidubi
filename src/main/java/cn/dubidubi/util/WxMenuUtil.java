package cn.dubidubi.util;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.kevinsawicki.http.HttpRequest;

import cn.dubidubi.model.json.WxButton;
import cn.dubidubi.model.json.WxMenu;
import cn.dubidubi.service.WxBaseService;
import cn.dubidubi.service.impl.WxBaseServiceImpl;
import cn.dubidubi.util.wx.WxProperties;

@Service
/**
 * @author linzj
 * @Description: 自定义菜单,menu菜单中需要button数组
 * @date 2018年1月21日 下午7:48:22
 */
public class WxMenuUtil {
	/**
	 * @Description: 自定义菜单按钮
	 * @data :@param args
	 * @date :2018年3月6日下午1:44:50
	 */
	public static void main(String[] args) {
		// 一级生活按钮
		WxButton life = new WxButton();
		life.setName("生活");
		WxButton[] lifes = new WxButton[2];
		// 二级天气按钮
		WxButton wxButton = new WxButton();
		wxButton.setType("click"); // 种类，点击事件
		wxButton.setName("获取当前天气"); // 按钮名
		wxButton.setKey("weather"); // key 实际接受到的key
		// 二级绑定邮箱
		WxButton mails = new WxButton();
		mails.setType("view");
		mails.setName("绑定邮箱");
		System.out.println("绑定邮箱的url为" + WxProperties.Mail_Url);
		mails.setUrl(WxProperties.Mail_Url);
		lifes[0] = wxButton;
		lifes[1] = mails;
		life.setSub_button(lifes);
		// 资源一级按钮
		WxButton resource = new WxButton();
		resource.setName("资源");
		// 设置资源二级按钮 百度图片源
		WxButton[] resources = new WxButton[1];
		WxButton baiduPic = new WxButton();
		baiduPic.setName("搜索图片");
		baiduPic.setType("view");
		baiduPic.setUrl(WxProperties.Pic_Url);
		resources[0] = baiduPic;
		resource.setSub_button(resources);
		// 新闻一级按钮
		WxButton news = new WxButton();
		news.setName("新闻");
		WxButton[] news_sub = new WxButton[2];
		// 澎湃子按钮
		WxButton pengpai = new WxButton();
		pengpai.setName("澎湃新闻源");
		pengpai.setType("click");
		pengpai.setKey("100");
		news_sub[0] = pengpai;
		// 腾讯子按钮
		WxButton tencent_news = new WxButton();
		tencent_news.setName("腾讯新闻源");
		tencent_news.setType("click");
		tencent_news.setKey("101");
		news_sub[1] = tencent_news;
		news.setSub_button(news_sub);
		// 将一级按钮放入数组中,将数组放入menu对象中,生成json对象
		WxButton[] button = new WxButton[3];
		button[0] = life; // 将天气加入数组
		button[1] = news; // 将新闻加入数组
		button[2] = resource;// 将资源加入数组
		WxMenu wxMenu = new WxMenu();
		wxMenu.setButton(button);
		WxBaseService wxBaseService = new WxBaseServiceImpl();
		String access_Token = wxBaseService.getAccessToken();
		System.out.println(access_Token);
		HttpRequest request = HttpRequest
				.post("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + access_Token);
		request.trustAllCerts();
		request.trustAllHosts();
		String json = JSON.toJSONString(wxMenu);
		System.out.println(json);
		request.send(json);
		String body = request.body();
		System.out.println(body);
	}
}
