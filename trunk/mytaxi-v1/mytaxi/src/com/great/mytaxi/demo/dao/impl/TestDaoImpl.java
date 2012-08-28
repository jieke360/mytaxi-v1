package com.great.mytaxi.demo.dao.impl;

import javax.annotation.Resource;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.great.mytaxi.demo.bean.Person;
import com.great.mytaxi.demo.dao.TestDao;
import com.ibatis.sqlmap.client.SqlMapClient;

@Repository("testDaoImpl")
public class TestDaoImpl extends SqlMapClientDaoSupport implements TestDao {

	public void insert(Person person) {
		
		/*if(StringUtils.isEmpty(person.getId())){		
			person.setId(UUID.getUUID());
		}*/
		
		Object obj = this.getSqlMapClientTemplate().insert("demo.person.insert",person);		
	}

	@Resource
	public void setMySqlMapClient(SqlMapClient mySqlMapClient) {
		super.setSqlMapClient(mySqlMapClient);
	}
}
