package com.qkl.tfcc.provider.testUser.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qkl.tfcc.api.entity.Page;
import com.qkl.tfcc.api.po.TestUser;
import com.qkl.tfcc.api.service.testUser.api.TestUserService;
import com.qkl.tfcc.provider.testUser.dao.TestUserDao;
import com.qkl.util.help.pager.PageData;

/**测试用的接口实现
 * <p>Description： 测试用的接口实现 </p>
 * @project_Name yc_udrs_provider
 * @class_Name TestUserServiceImpl.java
 * @author kezhiyi
 * @date 2016年8月13日
 * @version v1.0
 */
@Service
public class TestUserServiceImpl implements TestUserService{

	@Autowired
	private TestUserDao testUserDao;
	
	@Override
	public TestUser queryTestUserByUserId(long testUserId) {
		return testUserDao.selectTestUserByUserId(testUserId);
	}

	@Override
	public List<PageData> queryTestUserList(Page page) {
		return (List<PageData>) testUserDao.selectTestUserList(page);
	}

}
