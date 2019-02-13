package com.goabraod.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.entity.A;
import com.goabraod.dao.BaseMapper;
import com.goabraod.service.BaseService;

@Service("baseService")
@Transactional
public class BaseServiceImpl implements BaseService{

	@Autowired
	private BaseMapper baseMapper;
	

	@Override
	public A getTotalByType_Country(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return baseMapper.getTotalByType_Country(map);
	}

	@Override
	public A getIsType(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return baseMapper.getIsType(map);
	}

	@Override
	public A getIsNoType(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return baseMapper.getIsNoType(map);
	}


}
