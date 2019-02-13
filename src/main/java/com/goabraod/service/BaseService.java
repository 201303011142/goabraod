package com.goabraod.service;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.base.entity.A;

public interface BaseService {

	//获取国家类型总数
	public A getTotalByType_Country(@Param("map")Map<String, Object>map);
	//获取带标签新闻的条数
	public A getIsType(@Param("map")Map<String, Object>map);
	//获取带标签新闻的条数
	public A getIsNoType(@Param("map")Map<String, Object>map);
}
