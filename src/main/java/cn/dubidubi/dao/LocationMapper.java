package cn.dubidubi.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

import cn.dubidubi.model.WxLocationDO;

public interface LocationMapper {
	WxLocationDO selectByPrimaryKey(Integer id);

	// 保存地址信息
	@Insert("insert location_info(from_user_name,Longitude,Latitude,Create_time) values(#{fromUserName},#{longitude},#{latitude},#{createTime})")
	void saveOneLocation(WxLocationDO location);
	
	//查询是否存在该openid
	WxLocationDO getLocationByOpenId(String openId);
	
	//更新地址信息
	@Update("update location_info SET Longitude=#{longitude},Latitude=#{latitude},Create_time=#{createTime} where from_user_name=#{fromUserName}")
	void updateLocationByOpenId(WxLocationDO location);
}