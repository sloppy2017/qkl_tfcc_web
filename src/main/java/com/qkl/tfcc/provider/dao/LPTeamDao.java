package com.qkl.tfcc.provider.dao;

import java.util.List;
import java.util.Map;

import com.qkl.tfcc.api.po.acc.LPTeam;
import com.qkl.tfcc.provider.dbhelper.DAO;

public interface LPTeamDao extends DAO<LPTeam> {
	
	/**
	 * 查询累计推荐的LP会员人数
	 * @param userCode
	 * @return
	 */
	public long selectLPcount(String userCode);
	
	
	/**
	 * 按条件查询列表
	 * @param map
	 * @return
	 */
	public List<LPTeam> select(Map<String, Object> map);
	

}
