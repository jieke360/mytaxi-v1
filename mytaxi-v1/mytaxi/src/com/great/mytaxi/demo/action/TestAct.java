package com.great.mytaxi.demo.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.great.mytaxi.common.web.BaseAct;
import com.great.mytaxi.demo.bean.Person;
import com.great.mytaxi.demo.service.TestService;

@Controller("demo.test")
public class TestAct extends BaseAct {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private TestService testServiceImpl;
	
	private Person person;
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public String add(){
		
		System.out.print("添加");
		testServiceImpl.add(person);
		return "add";
	}
}
