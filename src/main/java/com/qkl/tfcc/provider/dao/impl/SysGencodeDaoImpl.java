package com.qkl.tfcc.provider.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.qkl.tfcc.api.po.sys.SysGencode;
import com.qkl.tfcc.provider.dao.SysGencodeDao;
import com.qkl.tfcc.provider.dbhelper.DaoSupport;



@Repository
public class SysGencodeDaoImpl extends DaoSupport<SysGencode> implements SysGencodeDao {

	protected static final Log logger = LogFactory.getLog(UserDaoImpl.class);
	
	private String namespace = "SysGencode";

	

	
	@Override
	public List<SysGencode> findAll() {
		List<SysGencode> tSysGencodeList =getSqlSession().selectList(namespace+"."+"findall");
		return tSysGencodeList;
		
	}

	@Override
	public List<SysGencode> findByGroupCode(String groupcode) {
		List<SysGencode> tSysGencodeList =getSqlSession().selectList(namespace+"."+"find",groupcode);
		return tSysGencodeList;
	}
	
	
	
	

}
