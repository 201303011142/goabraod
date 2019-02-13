package com.goabraod.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.entity.A;
import com.base.entity.AB;
import com.base.entity.GoOrders;
import com.base.entity.SysUser;
import com.goabraod.dao.BaseMapper;
import com.goabraod.dao.TravUserMapper;
import com.goabraod.service.TravUserService;


@Service("TravService")
@Transactional
public class TravUserServiceImpl implements TravUserService{

//	private final Logger logger=Logger.getLogger(UserServiceImpl.class);

	@Autowired
	private TravUserMapper travUserMapper;
	
	@Autowired
	private BaseMapper baseMapper;
	
	@Override
	public A getTotalByType_Country(Map<String, Object> map) {		
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
	
	@Override
	public SysUser getLogin(String userName) {
		// TODO Auto-generated method stub
		return travUserMapper.getLogin(userName);
	}
	
	@Override
	public List<GoOrders> getYOrderInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return travUserMapper.getYOrderInfo(map);
	}
	
	@Override
	public AB getFlightInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return travUserMapper.getFlightInfo(map);
	}
	
	@Override
	public void updateDownloadStatus(Map<String, Object> map) {
		// TODO Auto-generated method stub
		travUserMapper.updateDownloadStatus(map);
	}
	
	@Override
	public A getYOrderInfoSum(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return travUserMapper.getYOrderInfoSum(map);
	}
	
}
