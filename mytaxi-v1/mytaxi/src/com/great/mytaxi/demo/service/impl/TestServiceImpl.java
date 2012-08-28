package com.great.mytaxi.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.great.mytaxi.common.cond.KeyCond;
import com.great.mytaxi.common.util.impl.KeyDaoImpl;
import com.great.mytaxi.demo.bean.Person;
import com.great.mytaxi.demo.dao.TestDao;
import com.great.mytaxi.demo.service.TestService;

@Service("testServiceImpl")
@Transactional
public class TestServiceImpl implements TestService {

	@Autowired
	private TestDao testDaoImpl;
	@Autowired
	private KeyDaoImpl keyDaoImpl;
	
	
	public void add(Person person) {
		KeyCond keyCond = new KeyCond(); 
		keyCond.setFlagStr(keyCond.USER_FALG);
		keyCond.setName("test");
		String key = (String)keyDaoImpl.getSeqNo(keyCond);
		System.out.print("key"+key);
		testDaoImpl.insert(person);
	}

}
