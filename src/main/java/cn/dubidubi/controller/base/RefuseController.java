package cn.dubidubi.controller.base;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
/**
 * @author 16224
 * @Description: 权限不够拒绝页面
 * @date 2018年1月10日 下午3:28:56
 */
public class RefuseController {
	@RequestMapping("/refuse")
	public String refusePage() {
		return "refuse";
	}
}
