package com.qkl.tfcc.provider.dao;

import com.qkl.tfcc.provider.dbhelper.DAO;
import com.qkl.util.help.pager.PageData;

public interface AccLimitDao extends DAO<PageData> {
    
    /**
     * @describe:查询账户限额
     * @author: zhangchunming
     * @date: 2016年9月21日上午9:25:01
     * @param pd
     * @return: PageData
     */
	public PageData getAccLimit(PageData pd);
	
	
}
