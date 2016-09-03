package com.qkl.tfcc.provider.testUser.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.qkl.tfcc.api.entity.Page;
import com.qkl.tfcc.api.entity.PageDataYC;
import com.qkl.tfcc.api.po.TestUser;
import com.qkl.tfcc.provider.testUser.dao.TestUserDao;
import com.qkl.tfcc.provider.dbhelper.DaoSupport;;

/**测试用户的dao的实现
 * <p>Description：测试用户的dao的实现  </p>
 * @project_Name yc_udrs_provider
 * @class_Name TestUserDaoImpl.java
 * @author kezhiyi
 * @date 2016年8月13日
 * @version v1.0
 */
@Repository
public class TestUserDaoImpl extends  DaoSupport<TestUser>   implements   TestUserDao{

	
	private String namespace = "TestUser";
	
	@Override
	public TestUser selectTestUserByUserId(long testUserId) {
		TestUser tu = getSqlSession().selectOne(namespace+"."+"selectTestUserByUserId", testUserId);			
		return tu;
	}

	@Override
	public Object selectTestUserList(Page page) {
//		List<PageData> tuList =
		Object	 tObject = getSqlSession().selectList(namespace+"."+"selectTestUserlistPage",page);
		// TODO Auto-generated method stub
		 return tObject;
	}

}
