package com.qkl.tfcc.provider.dao.impl;

import org.springframework.stereotype.Repository;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.qkl.tfcc.api.po.user.UserFriendship;
import com.qkl.tfcc.provider.dao.UserFriendshipDao;
import com.qkl.tfcc.provider.dbhelper.DaoSupport;




/**UserFriendshipDao的实现
 * <p>Description：UserFriendshipDao的实现  </p>
 * @project_Name qkl_tfcc_web
 * @class_Name UserFriendshipDaoImpl.java
 * @author kezhiyi
 * @date 2016年8月17日
 * @version v1.0
 */
@Repository
public class UserFriendshipDaoImpl extends DaoSupport<UserFriendship> implements UserFriendshipDao {

	protected static final Log logger = LogFactory.getLog(UserDaoImpl.class);
	
	private String namespace = "UserFriendship";
	
	
	@Override
	public int findIsExistUpFriendship(String recomusercode) {
		int eCnt=  getSqlSession().selectOne(namespace+"."+"findIsExist", recomusercode);
		return eCnt;
	}

	@Override
	public UserFriendship findUpFriendship(String recomusercode) {
		UserFriendship tUserFriendship =getSqlSession().selectOne(namespace+"."+"findUpFriendshipByPhone", recomusercode);
		return tUserFriendship;
	}

	@Override
	public void addUserFriendship(UserFriendship userFriendship) {
		getSqlSession().insert(namespace+"."+"add", userFriendship);			
	}

	@Override
	public void modifyCalflag(UserFriendship userFriendship) {
		getSqlSession().update(namespace+"."+"updatecalflag", userFriendship);		
	}

	@Override
	public UserFriendship findMaxFriendship(String recomusercode) {
		UserFriendship tUserFriendship =getSqlSession().selectOne(namespace+"."+"findMaxFriendshipByPhone", recomusercode);
		return tUserFriendship;
	}

	
	
	
	
}
