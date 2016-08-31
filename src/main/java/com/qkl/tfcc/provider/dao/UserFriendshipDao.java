package com.qkl.tfcc.provider.dao;

import com.qkl.tfcc.api.po.user.UserFriendship;
import com.qkl.tfcc.provider.dbhelper.DAO;

public interface UserFriendshipDao extends DAO<UserFriendship> {

	
	/** 判断推荐人是否含有上级会员
	 * @return findIsHaveUpFriendship  判断推荐人是否含有上级会员
	 * @create author kezhiyi
	 * @create date 2016年8月25日
	 */
	public int  findIsExistUpFriendship(String recomusercode);
	/** 查询推荐人上级会员
	 * @return findUpFriendship  查询推荐人上级会员
	 * @create author kezhiyi
	 * @create date 2016年8月25日
	 */
	public UserFriendship  findUpFriendship(String recomusercode);
	
	
	/** 添加用户关系信息
	 * @return addUserFriendship 添加用户关系信息
	 * @create author kezhiyi
	 * @create date 2016年8月25日
	 */ 
	public void addUserFriendship(UserFriendship userFriendship);
	
	/** 添加用户关系信息
	 * @return addUserFriendship 添加用户关系信息
	 * @create author kezhiyi
	 * @create date 2016年8月25日
	 */ 
	public void modifyCalflag(UserFriendship userFriendship);
	
}
