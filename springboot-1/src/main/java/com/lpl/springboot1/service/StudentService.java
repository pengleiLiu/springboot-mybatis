package com.lpl.springboot1.service;

import java.util.List;

import com.lpl.springboot1.pojo.Student;

public interface StudentService {

	int insert(Student student);//增加数据
	
	int deleteByPrimaryKey(Long id);//删除数据
	
	Student updateByPrimaryKey(Student student);//修改数据
	
	Student selectByPrimaryKey(Long id);//查询数据
	
	List<Student> selectList();//查询学生列表
	
}
