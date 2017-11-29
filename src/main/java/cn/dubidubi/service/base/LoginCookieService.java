package cn.dubidubi.service.base;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import cn.dubidubi.model.base.dto.UserLoginDTO;

public interface LoginCookieService {

	void addLoginCookie(UserLoginDTO userLoginDTO, HttpServletResponse response) throws IOException;

	UserLoginDTO getUserFromCookies(Cookie[] cookies) throws ClassNotFoundException;
}