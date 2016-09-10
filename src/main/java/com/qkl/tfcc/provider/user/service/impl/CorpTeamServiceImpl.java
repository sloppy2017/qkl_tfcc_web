package com.qkl.tfcc.provider.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qkl.tfcc.api.entity.Page;
import com.qkl.tfcc.api.service.user.api.CorpTeamService;
import com.qkl.tfcc.provider.dao.CorpTeamDao;
import com.qkl.util.help.pager.PageData;

@Service
public class CorpTeamServiceImpl implements CorpTeamService {

	@Autowired
	private CorpTeamDao corpTeamDao;
	@Override
	public long findNum(String userCode) {
		// TODO Auto-generated method stub
		return corpTeamDao.findLPNum(userCode);
	}

	@Override
	public List<PageData> findlplist(Page page) {
		// TODO Auto-generated method stub
		return corpTeamDao.findlpInfo(page);
	}

}
