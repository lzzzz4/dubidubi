package cn.dubidubi.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

import cn.dubidubi.model.WxLocationDO;

public interface LocationMapper {
	WxLocationDO selectByPrimaryKey(Integer id);

	/**
	 * @Description: 保存地址信息
	 * @param location
	 */
	@Insert("insert location_info(from_user_name,Longitude,Latitude,create_time,address,adcode) values(#{fromUserName},#{longitude},#{latitude},#{createTime},#{address},#{adcode})")
	void saveOneLocation(WxLocationDO location);

	/**
	 * @Description: 依据openid查询位置信息
	 * @param openId
	 * @return
	 */
	WxLocationDO getLocationByOpenId(String openId);

	/**
	 * @Description:更新位置信息
	 * @param location
	 */
	@Update("update location_info SET Longitude=#{longitude},Latitude=#{latitude},create_time=#{createTime},address=#{address},adcode =#{adcode} where from_user_name=#{fromUserName}")
	void updateLocationByOpenId(WxLocationDO location);
}