package cn.dubidubi.service.base;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class SystemLogout extends LogoutFilter {
	@Autowired
	LoginRealm loginRealm;

	@Override
	protected boolean preHandle(ServletRequest arg0, ServletResponse arg1) throws Exception {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			int length = cookies.length;
			for (int i = 0; i < length; i++) {
				if (cookies[i].getName().equals("rememberUser")) {
					cookies[i].setMaxAge(0);
					cookies[i].setPath("/");
					response.addCookie(cookies[i]);
					break;
				}
			}
		}
		return super.preHandle(arg0, arg1);
	}

}
