package cn.dubidubi.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.dubidubi.dao.MailInfoMapper;
import cn.dubidubi.model.base.dto.AjaxResultDTO;
import cn.dubidubi.model.dto.GetPicDTO;
import cn.dubidubi.model.dto.PicUrlToBase64DTO;
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
	@Autowired
	MailInfoMapper mailInfoMapper;

	/**
	 * @throws IOException
	 * @throws ExecutionException
	 * @throws InterruptedException
	 * @Description: 界面输入关键词,执行爬虫,异步将内容发送至用户的邮箱
	 * @data :@param getPicDTO
	 * @data :@param file
	 * @data :@param request
	 * @data :@return
	 * @date :2018年3月20日下午12:55:12
	 */
	@RequestMapping("/key")
	@ResponseBody
	public AjaxResultDTO key(GetPicDTO getPicDTO, @RequestParam(required = false) MultipartFile file,
			HttpServletRequest request) throws InterruptedException, ExecutionException, IOException {
		AjaxResultDTO resultDTO = new AjaxResultDTO();
		WxInfoDTO wxInfoDTO = (WxInfoDTO) request.getSession().getAttribute("user");
		if (mailInfoMapper.listMailByOpenid(wxInfoDTO.getOpenid()) == null
				|| mailInfoMapper.listMailByOpenid(wxInfoDTO.getOpenid()).size() == 0) {
			resultDTO.setCode(500);
		}

		// 当为九妹源时
		if ("2".equals(getPicDTO.getSource())) {
			System.out.println("九妹源");

			resultDTO.setUid(wxInfoDTO.getOpenid());
			Future<PicUrlToBase64DTO> list = pyBeautifuGirlSerivce.startPy(getPicDTO.getKey(), wxInfoDTO.getOpenid(),
					getPicDTO.getTime());
			pyBeautifuGirlSerivce.waitForComplete(list);
			resultDTO.setCode(9);
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

	/**
	 * @Description: 拉取微信的授权
	 * @data :@param code
	 * @data :@param state
	 * @data :@param request
	 * @data :@return
	 * @date :2018年3月20日下午1:03:34
	 */
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

	// public AjaxResultDTO isDownloadPicFinish() {
	// AjaxResultDTO dto = new AjaxResultDTO();
	// }

}
