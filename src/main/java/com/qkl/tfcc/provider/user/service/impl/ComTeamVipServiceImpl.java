package com.qkl.tfcc.provider.user.service.impl;



import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.qkl.tfcc.api.entity.Page;
import com.qkl.tfcc.api.po.user.ComTeamVip;
import com.qkl.tfcc.api.service.user.api.ComTeamVipService;
import com.qkl.tfcc.provider.dao.ComTeamVipDao;
import com.qkl.util.help.pager.PageData;


@Service("vipService")
public class ComTeamVipServiceImpl implements ComTeamVipService {

	@Autowired
	@Qualifier("vipDao")
	private ComTeamVipDao dao;
	public void setDao(ComTeamVipDao dao) {
		this.dao = dao;
	}


	@Override
	
	public ComTeamVip findcount(String userCode) {
		// TODO Auto-generated method stub
		return dao.findcount(userCode);
	}


	@Override
	public List<PageData> findVipList(Page page) {
		// TODO Auto-generated method stub
		return  (List<PageData>) dao.findVipList(page);
	}


	@Override
	public int findCount(Map<String, Object> mp) {
		// TODO Auto-generated method stub
		return dao.findfyCount(mp);
	}


	

	


	

}
