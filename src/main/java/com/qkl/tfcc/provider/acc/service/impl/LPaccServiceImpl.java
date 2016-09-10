package com.qkl.tfcc.provider.acc.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qkl.tfcc.api.entity.Page;
import com.qkl.tfcc.api.service.acc.api.LPaccService;
import com.qkl.tfcc.provider.dao.LPaccDao;
import com.qkl.util.help.pager.PageData;


@Service
public class LPaccServiceImpl implements LPaccService {

	@Autowired
	private LPaccDao lpaccDaoImpl;
	
	@Override
	public long findLPBalance(String userCode) {
		// TODO Auto-generated method stub
		return lpaccDaoImpl.findLPBalance(userCode);
	}

	@Override
	public long findtotalReward(String userCode) {
		// TODO Auto-generated method stub
		return lpaccDaoImpl.findtotalReward(userCode);
	}

	@Override
	public long findrefReward(String userCode) {
		// TODO Auto-generated method stub
		return lpaccDaoImpl.findrefReward(userCode);
	}

	@Override
	public long findmyReward(String userCode) {
		// TODO Auto-generated method stub
		return lpaccDaoImpl.findmyReward(userCode);
	}

	@Override
	public List<PageData> findRewardInfo(Page page) {
		// TODO Auto-generated method stub
		return lpaccDaoImpl.findRewardInfo(page);
	}

	
}
