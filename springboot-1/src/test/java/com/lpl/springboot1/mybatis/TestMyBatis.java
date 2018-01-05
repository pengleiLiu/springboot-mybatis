package com.lpl.springboot1.mybatis;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.pagehelper.PageHelper;
import com.lpl.springboot1.dao.StudentMapper;
import com.lpl.springboot1.pojo.Student;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMyBatis {

	@Autowired
	private StudentMapper studentMapper;
	
	@Test
	public void testInsert(){	
		List<Student> selectList = studentMapper.selectList();
		
		
		System.out.println(selectList);
		
	}
}
