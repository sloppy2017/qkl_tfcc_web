package com.qkl.tfcc.provider.dao;

import java.util.List;
import java.util.Map;

import com.qkl.tfcc.api.entity.Page;
import com.qkl.tfcc.api.po.user.ComTeamVip;
import com.qkl.tfcc.provider.dbhelper.DAO;
import com.qkl.util.help.pager.PageData;




public interface ComTeamVipDao extends DAO<ComTeamVip>{

	    
		/**
		 * 查询各类会员的数量
		 * @param userCode
		 * @return
		 */
		public ComTeamVip findcount(String  userCode);
		/**
		 * 查询各类会员信息列表
		 * @param mp
		 * @return
		 */
		public Object findVipList(Page page);
		
		
	
}
 