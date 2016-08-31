package com.qkl.tfcc.provider.dao.impl;

import org.springframework.stereotype.Repository;

import com.qkl.tfcc.api.po.user.LoginErr;
import com.qkl.tfcc.provider.dao.UserLoginErrDao;
import com.qkl.tfcc.provider.dbhelper.DaoSupport;




/**UserLoginErrDao的实现
 * <p>Description：UserLoginErrDao的实现  </p>
 * @project_Name qkl_tfcc_web
 * @class_Name UserLoginErrDaoImpl.java
 * @author kezhiyi
 * @date 2016年8月17日
 * @version v1.0
 */
@Repository
public class UserLoginErrDaoImpl extends DaoSupport<LoginErr> implements UserLoginErrDao {

	@Override
	public void addLoginErr(LoginErr loginErr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modifyLoginErr(LoginErr loginErr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void findLoginErrByUserCode(String userCode) {
		// TODO Auto-generated method stub
		
	}

}
