package com.lpl.springboot1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.lpl.springboot1.pojo.Student;

public interface StudentMapper {

	int insert(Student student);//增加数据
	
	int deleteByPrimaryKey(@Param("id")Long id);//删除数据
	
	int updateByPrimaryKey(Student student);//修改数据
	
	Student selectByPrimaryKey(Long id);//查询数据
	
	List<Student> selectList();//查询学生列表
}
