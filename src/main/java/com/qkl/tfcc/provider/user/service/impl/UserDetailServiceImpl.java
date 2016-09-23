package com.qkl.tfcc.provider.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.qkl.tfcc.api.po.sys.SysGencode;
import com.qkl.tfcc.api.po.user.User;
import com.qkl.tfcc.api.po.user.UserDetail;
import com.qkl.tfcc.api.po.user.UserFriendship;
import com.qkl.tfcc.api.service.acc.api.AccService;
import com.qkl.tfcc.api.service.user.api.UserDetailService;
import com.qkl.tfcc.api.service.user.api.UserService;
import com.qkl.tfcc.provider.dao.AccDao;
import com.qkl.tfcc.provider.dao.AccDetailDao;
import com.qkl.tfcc.provider.dao.SysGencodeDao;
import com.qkl.tfcc.provider.dao.UserDao;
import com.qkl.tfcc.provider.dao.UserDetailDao;
import com.qkl.tfcc.provider.dao.UserFriendshipDao;
import com.qkl.tfcc.provider.dao.UserLevelcntDao;
import com.qkl.tfcc.provider.dao.UserLoginErrDao;


@Service
public class UserDetailServiceImpl implements UserDetailService {

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
    public UserDetail findUserDetail(String userCode, String versionNo) {
        // TODO Auto-generated method stub
        return userDetailDao.findDetail(userCode);
    }

    
}
