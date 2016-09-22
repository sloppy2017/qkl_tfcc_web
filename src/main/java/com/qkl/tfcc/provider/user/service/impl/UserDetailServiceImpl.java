package com.qkl.tfcc.provider.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.qkl.tfcc.api.po.sys.SysGencode;
import com.qkl.tfcc.api.po.user.User;
import com.qkl.tfcc.api.po.user.UserDetail;
import com.qkl.tfcc.api.po.user.UserFriendship;
import com.qkl.tfcc.api.service.acc.api.AccService;
import com.qkl.tfcc.api.service.user.api.UserService;
import com.qkl.tfcc.provider.dao.AccDao;
import com.qkl.tfcc.provider.dao.AccDetailDao;
import com.qkl.tfcc.provider.dao.SysGencodeDao;
import com.qkl.tfcc.provider.dao.UserDao;
import com.qkl.tfcc.provider.dao.UserDetailDao;
import com.qkl.tfcc.provider.dao.UserFriendshipDao;
import com.qkl.tfcc.provider.dao.UserLevelcntDao;
import com.qkl.tfcc.provider.dao.UserLoginErrDao;

public class UserDetailServiceImpl implements UserService {

	private Logger loger = LoggerFactory.getLogger(UserServiceImpl.class);
	private  List<SysGencode> tSysGencodeAll = new  ArrayList<SysGencode>();
	
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserDetailDao userDetailDao;	
	@Autowired
	private UserFriendshipDao userFriendshipDao;
	@Autowired
	private UserLoginErrDao userLoginErrDao;
	@Autowired
    private AccService accService;
	@Autowired
    private AccDao accDao;
	@Autowired
    private AccDetailDao accDetailDao;
	@Autowired
	private SysGencodeDao sysGencodeDao;
	@Autowired
	private UserLevelcntDao userLevelcntDao;

	@Override
	public UserDetail findUserDetailByUserCode(String userCode, String versionNo) {
		UserDetail findUserDetailByUserCode = userDetailDao.findUserDetailByUserCode(userCode);
		return findUserDetailByUserCode;
	}

	@Override
	public Map<String, Object> login(String phone, String password,
			String systemCode, String versionNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> findLockLoginStatus(String phone, long second,
			String versionNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> findLockSmsStatus(String phone, long second,
			String versionNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findbyPhone(String phone, String versionNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findUserByUserCode(String userCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean findIsExist(String phone, String versionNo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean findIsExistUpFriendship(String recomusercode,
			String versionNo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UserFriendship findUpFriendship(String recomusercode,
			String versionNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserFriendship findMaxFriendship(String recomusercode,
			String versionNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addUser(User user, UserDetail userDetail, String versionNo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addUserFriendShip(UserFriendship userFriendship,
			String versionNo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean realUser(String phone, String realName, String idNo,
			String versionNo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modifyPwd(String phone, String password, String versionNo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modifypwdByUserCode(String userCode, String password,
			String versionNo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modifyPhone(String userCode, String phone, String versionNo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modifyUserDetail(UserDetail userDetail, String versionNo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modifyLockLoginStatus(String userCode, String status,
			Date locktime, String versionNo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modifyLockSmsStatus(String phone, String status,
			Date locktime, String versionNo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modifyUserHeadPic(String userCode, String imgAddrss,
			String versionNo) {
		// TODO Auto-generated method stub
		return false;
	}

}
