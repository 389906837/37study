package com.cloudCang.test.dao;


import java.io.UnsupportedEncodingException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.cang.os.bean.sys.Role;
import com.cang.os.common.core.Page;
import com.cang.os.mgr.sys.service.OperatorService;
import com.cang.os.mgr.sys.service.RoleService;
import com.cloudCang.test.BaseTest;


public class TestBaseDao extends BaseTest{

   @Autowired
   RoleService roleService;
   @Autowired
   OperatorService operatorService;

	
	@Test
	public void testAdd(){
		
		Role role = new Role();
		role.setSroleName("sys");
		role.setSremark("超级管理员");
		roleService.save(role);
		/*Operator operator = new Operator();
		
		operator.setBisAdmin(1);
		operator.setBisDelete(0);
		operator.setBisFreeze(1);
		operator.setDmodifyDate(new Date());
		operator.setDaddDate(new Date());
		operator.setDendLoginTime(new Date());
		operator.setIsex(1);
		operator.setSaddOperator("sys");
		operator.setSmail("820624785@qq.com");
		operator.setSmobile("15121061739");
		operator.setSoperatorNo("a123123");
		operator.setSpassword("123123");
		operator.setSrealName("leilei123");
		operator.setSuserName("sys");
		this.operatorService.save(operator);*/
	}
	@Test
	public void testFindAll() throws UnsupportedEncodingException {
		Page<Role> page = new Page<Role>();
		page.setPageNum(1);
		page.setPageSize(3);

		Query query = new Query(Criteria.where("sroleName").is("admin"));
		page=roleService.findPage(page, query) ;
		System.out.println(page);
		for (Role role : page.getResults()) {
			System.out.println("id:"+role.getId()+" sroleName:"+role.getSroleName()+"   remark:"+role.getSremark());
		}
		System.out.println("获取全部的数据----------------------");
	}
	
	/*@Test
	public void testFindById() throws UnsupportedEncodingException{
		User user=this.commonDao.findById(User.class, "58085c154d7c881143eefaba");
		System.out.println(user.getUsername());
		Teacher teacher = teacherDao.findById("580d7e9d4d7c508d7e3c0478");
		System.out.println("获取单个对象----------------------");
		System.out.println(new String(teacher.getImg(),"utf-8"));
		System.out.println("获取单个对象----------------------");
	}*/
/*	@Test
	public void testUpdate(){
		User user=this.commonDao.findById(User.class, "1234567");
		user.setUsername("黄江淮");
		this.commonDao.saveOrUpdate(user);
		User newUser=this.commonDao.findById(User.class, "1234567");
		System.out.println(newUser.getUsername());
		System.out.println("修改数据成功");
		this.commonDao.saveOrUpdate(user);
	}
	@Test
	public void testRemove(){
		User user=this.commonDao.findById(User.class, "1234567");
		this.commonDao.remove(user);
		User oldUser=this.commonDao.findById(User.class, "1234567");
		if(oldUser==null){
			System.out.println(oldUser==null);
			System.out.println("删除对象成功");
		}
		this.commonDao.add(user);
	}
	
	@Test
	public void testCount(){
		Query query=new Query();
		Criteria criteria=new Criteria();
		criteria.and("username").is("ziyu");
		query.addCriteria(criteria);
		long total=this.commonDao.count(User.class, query);
		System.out.println("用户总数:"+total);
	}*/
}
