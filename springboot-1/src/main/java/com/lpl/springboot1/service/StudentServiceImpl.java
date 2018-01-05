package com.lpl.springboot1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lpl.springboot1.dao.StudentMapper;
import com.lpl.springboot1.pojo.Student;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentMapper studentMapper;

	@Override
	@Transactional
	public int insert(Student student) {
		// TODO Auto-generated method stub
		int row = studentMapper.insert(student);
		//测试事务
		//int a = 1/0;
		return row;
	}

	@Override
	@CacheEvict(value="defaultCache",key="'student_id_'+#id")  
	public int deleteByPrimaryKey(Long id) {
		System.out.println("删除方法执行了");
		return studentMapper.deleteByPrimaryKey(id);
	}

	@Override
	@CachePut(value="defaultCache",key="'student_id_'+#student.id")
	public Student updateByPrimaryKey(Student student) {
		System.out.println("修改方法执行了");
		studentMapper.updateByPrimaryKey(student);
		return student;
	}

	@Override
	@Cacheable(value="defaultCache",key="'student_id_'+#id")//#id  
	public Student selectByPrimaryKey(Long id) {
		System.out.println("查询方法执行了");
		return studentMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Student> selectList() {
		// TODO Auto-generated method stub
		return studentMapper.selectList();
	}

}
