package com.qkl.tfcc.provider.user.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.qkl.tfcc.api.entity.Page;
import com.qkl.tfcc.api.service.user.api.LPmemberService;
import com.qkl.tfcc.provider.dao.LPmemberDao;
import com.qkl.util.help.pager.PageData;

@Service
public class LPmemberServiceImpl implements LPmemberService {

	@Autowired
	private LPmemberDao lpdao;
	
	@Override
	public long findLPmemberNum(String userCode) {
		// TODO Auto-generated method stub
		return lpdao.findLPmemberNum(userCode);
	}

	@Override
	public List<PageData> findLPmemberInfo(Page page) {
		// TODO Auto-generated method stub
		return lpdao.findLPmemberInfo(page);
	}


	 

}
