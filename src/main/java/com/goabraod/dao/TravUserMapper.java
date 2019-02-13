package com.goabraod.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.base.entity.A;
import com.base.entity.AB;
import com.base.entity.GoOrders;
import com.base.entity.SysUser;

public interface TravUserMapper {
	//获取登录
	public SysUser getLogin(@Param("userName")String userName);
	//获取越南落地签批文订单
	public List<GoOrders> getYOrderInfo(@Param("map")Map<String, Object>map);
	//获取总数
	public A getYOrderInfoSum(@Param("map")Map<String, Object>map);
	//获取航班信息
	public AB getFlightInfo(@Param("map")Map<String, Object>map);
	//写入下载状态
	public void updateDownloadStatus(@Param("map")Map<String, Object>map);
}
