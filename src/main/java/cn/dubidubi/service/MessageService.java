package cn.dubidubi.service;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import cn.dubidubi.model.PushMessage;

public interface MessageService {

	String pushMessage(PushMessage pushMessage) throws IOException;

}