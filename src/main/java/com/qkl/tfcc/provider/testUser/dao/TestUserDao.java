package com.qkl.tfcc.provider.testUser.dao;

import com.qkl.tfcc.api.po.TestUser;
import com.qkl.tfcc.provider.dbhelper.DAO;

/**测试用户的dao接口
 * <p>Description：测试用户的dao接口  </p>
 * @project_Name yc_udrs_provider
 * @class_Name TestUserDao.java
 * @author kezhiyi
 * @date 2016年8月13日
 * @version v1.0
 */
public interface TestUserDao extends DAO<TestUser> {

	/** 获取用户信息
	 * <p> (根据用户的主键获取用户信息)  </p>
	 * @Title: selectTestUserByUserId 
	 * @param testUserId long 测试用户的id
	 * @return TestUser 测试用户的对象
	 * @create author kezhiyi
	 * @create date 2016年8月13日
	 */ 
	public TestUser selectTestUserByUserId(long testUserId);
}
