package cn.dubidubi.controller;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.dubidubi.model.base.dto.AjaxResultDTO;
import cn.dubidubi.model.dto.GetPicDTO;
import cn.dubidubi.model.dto.WxInfoDTO;
import cn.dubidubi.service.PyBeautifulGirlService;
import cn.dubidubi.service.WxBaseService;

/**
 * @author linzj
 * @Description: 根据用户输入的关键词爬
 * @date 2018年3月15日 上午7:59:14
 */
@Controller
@RequestMapping("/pic")
public class GetPicByKeyController {
	@Autowired
	WxBaseService wxBaseService;
	@Autowired
	PyBeautifulGirlService pyBeautifuGirlSerivce;

	@RequestMapping("/key")
	@ResponseBody
	public AjaxResultDTO key(GetPicDTO getPicDTO, @RequestParam(required = false) MultipartFile file,
			HttpServletRequest request) {
		AjaxResultDTO resultDTO = new AjaxResultDTO();
		// 当为九妹源时
		if ("2".equals(getPicDTO.getSource())) {
			System.out.println("九妹源");
			pyBeautifuGirlSerivce.startPy(getPicDTO.getKey());
			resultDTO.setCode(9);
			resultDTO.setUrl("/index.html");
		} else {
			System.out.println("百度源");
			int pn = RandomUtils.nextInt(0, 10) * getPicDTO.getAmount();
			pyBeautifuGirlSerivce.startPy(0, 0, getPicDTO.getKey(), pn, getPicDTO.getAmount());
			resultDTO.setCode(200);
			resultDTO.setUrl("/9mei_suc.html");
		}
		System.out.println(resultDTO);
		return resultDTO;
	}

	@RequestMapping("/access")
	public String assess(String code, String state, HttpServletRequest request) {
		String json = wxBaseService.codeToAccessToken(code);
		// 将数据传入session
		JSONObject tempInfo = JSON.parseObject(json);
		WxInfoDTO wxInfoDTO = new WxInfoDTO();
		wxInfoDTO.setNickname(tempInfo.getString("nickname"));
		wxInfoDTO.setCity(tempInfo.getString("city"));
		wxInfoDTO.setHeadimgurl(tempInfo.getString("headimgurl"));
		wxInfoDTO.setOpenid(tempInfo.getString("openid"));
		request.getSession().setAttribute("user", wxInfoDTO);
		LoggerFactory.getLogger(this.getClass()).info("关键词查询图片,获取用户授权");
		// System.out.println(wxInfoDTO);
		return "redirect:/pic.html";
	}
}
