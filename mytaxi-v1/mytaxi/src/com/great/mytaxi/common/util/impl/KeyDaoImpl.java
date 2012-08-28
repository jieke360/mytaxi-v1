package com.great.mytaxi.common.util.impl;

import javax.annotation.Resource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.great.mytaxi.common.cond.KeyCond;
import com.great.mytaxi.common.util.KeyDao;
import com.ibatis.sqlmap.client.SqlMapClient;

@Repository("keyDaoImpl")
public class KeyDaoImpl extends SqlMapClientDaoSupport implements KeyDao {
	
	public String getSeqNo(KeyCond keyCond ){
		
		Object obj = this.getSqlMapClientTemplate().queryForObject("common.keyDao.getKey", keyCond);
		return obj==null ? null : (String)obj;
	}

	@Resource
	public void setMySqlMapClient(SqlMapClient mySqlMapClient) {
		super.setSqlMapClient(mySqlMapClient);
	}
}
