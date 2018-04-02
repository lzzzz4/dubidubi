package cn.dubidubi.service;

import com.alibaba.fastjson.JSONArray;

public interface WxTempService {
	public void sendTempMessage(JSONArray jsonArray);
}