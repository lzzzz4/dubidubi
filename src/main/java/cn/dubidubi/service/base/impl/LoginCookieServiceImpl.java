package cn.dubidubi.service.base.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.util.Base64;

import cn.dubidubi.model.base.dto.UserLoginDTO;
import cn.dubidubi.service.base.LoginCookieService;

@Service
/**
 * 
 * @author lzj
 * @date 2017年11月17日 下午8:54:13
 * @Description: 将用户对象序列化后调用base64编码为字符串加入cookie中,从cookie中解码取出对象
 */
public class LoginCookieServiceImpl implements LoginCookieService {
	@Override
	public void addLoginCookie(UserLoginDTO userLoginDTO, HttpServletResponse response) throws IOException {
		String baseString = toBase64(userLoginDTO);
		Cookie cookie = new Cookie("rememberUser", baseString);
		cookie.setMaxAge(2592000);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	@Override
	public UserLoginDTO getUserFromCookies(Cookie[] cookies) throws ClassNotFoundException {
		if (cookies == null) {
			return null;
		}
		int length = cookies.length;
		String base64 = null;
		for (int i = 0; i < length; i++) {
			if (cookies[i].getName().equals("rememberUser")) {
				base64 = cookies[i].getValue();
				break;
			}
		}
		if (base64 == null) {
			return null;
		}
		UserLoginDTO userLoginDTO = null;
		try (ByteArrayInputStream in = new ByteArrayInputStream(Base64.decodeFast(base64));
				ObjectInputStream inputStream = new ObjectInputStream(in);) {
			userLoginDTO = (UserLoginDTO) inputStream.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return userLoginDTO;
	}

	private String toBase64(UserLoginDTO userLoginDTO) {
		String string = null;
		try (ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(arrayOutputStream);) {
			objectOutputStream.writeObject(userLoginDTO);
			string = org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(arrayOutputStream.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return string;
	}
}
