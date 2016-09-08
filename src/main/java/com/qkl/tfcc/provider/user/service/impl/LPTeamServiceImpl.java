package com.qkl.tfcc.provider.user.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qkl.tfcc.api.po.acc.LPTeam;
import com.qkl.tfcc.api.service.user.api.LPTeamService;
import com.qkl.tfcc.provider.dao.LPTeamDao;


@Service
public class LPTeamServiceImpl implements LPTeamService {
	
	@Autowired
	private LPTeamDao dao;
	
	@Override
	public long findLPcount(String userCode) {
		// TODO Auto-generated method stub
		return dao.selectLPcount(userCode);
	}

	@Override
	public List<LPTeam> find(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return dao.select(map);
	}

}
