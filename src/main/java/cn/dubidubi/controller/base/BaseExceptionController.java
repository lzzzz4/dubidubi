package cn.dubidubi.controller.base;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.dubidubi.model.base.dto.AjaxResultDTO;

/**
 * @author 16224
 * @Description: 总异常处理 1000 授权错误
 * @date 2018年1月9日 上午11:48:25
 */
public class BaseExceptionController {
	@ExceptionHandler(UnauthorizedException.class)
	@ResponseBody
	/**
	 * @Description:当ajax请求页面时,未授权用户抛出的异常处理
	 * @param response
	 * @return
	 */
	public AjaxResultDTO ajaxUnAuthorHandle(HttpServletResponse response) {
		AjaxResultDTO ajaxResultDTO = new AjaxResultDTO();
		// 设置错误码为1000,授权失败
		ajaxResultDTO.setCode(1000);
		return ajaxResultDTO;
	}
}
