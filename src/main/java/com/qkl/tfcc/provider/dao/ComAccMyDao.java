package com.qkl.tfcc.provider.dao;

import java.math.BigDecimal;
import java.util.List;

import com.qkl.tfcc.api.entity.Page;
import com.qkl.tfcc.api.po.acc.ComAccMy;
import com.qkl.tfcc.provider.dbhelper.DAO;
import com.qkl.util.help.pager.PageData;

public interface ComAccMyDao extends DAO<ComAccMy> {

	
	 /**
	  * 查询TFCC的余额
	  * @param userCode
	  * @return
	  */
	public BigDecimal findTB(String userCode);
	

	/**
	 * 查询JFFC的余额
	 * @param userCode
	 * @return
	 */
	public BigDecimal findJB(String userCode);
	
	
	/**
	 * 查询我累计推荐会员奖励的TFCC
	 * @param userCode
	 * @return
	 */
	public BigDecimal findReward(String userCode);
	/**
	 * @describe:查询投资机构给我发放的奖励
	 * @author: zhangchunming
	 * @date: 2016年9月24日上午1:36:45
	 * @param userCode
	 * @return: BigDecimal
	 */
	public BigDecimal findFFReward(String userCode);
	

	/**
	 * 查询推荐网点奖励的SAN
	 * @param userCode
	 * @return
	 */
	public BigDecimal findWReward(String userCode);
	

	/**
	 * 查询推介会员购买SAN时送给我的奖励
	 * @param userCode
	 * @return
	 */
	public BigDecimal findTTReward(String userCode);

	/**
	 * 查询推荐各级别会员所得奖励列表
	 * @return
	 */
	public List<PageData> findAllPage(Page page);
	/**
	 * @describe:查询可用余额，冻结额度，总额度
	 * @author: zhangchunming
	 * @date: 2016年9月23日上午10:46:45
	 * @param pd
	 * @return: PageData
	 */
	public PageData getAmnt(PageData pd);
	
	/**
	 * 保存转账数据到转出表
	 * @return
	 */
	public int insertOutAcc(PageData pd);
}
