package com.lpl.springboot1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lpl.springboot1.dao.StudentMapper;
import com.lpl.springboot1.pojo.Student;
import com.lpl.springboot1.service.StudentService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/users")  
public class HelloController {
	
	
	@Autowired
	private StudentService studentService;
	
    @RequestMapping("/hello")
    public String index() {
    	System.out.println(2);
        return "Hello World 斤斤计较";
    }
    
    @ApiOperation(value="获取用户列表", notes="")
   // @RequestMapping("/userList")
    @RequestMapping(value="/userList", method=RequestMethod.POST)
    public List<Student> getUser(){
    	//分页插件
    	PageHelper.startPage(1, 5);
    	List<Student> selectList = studentService.selectList();
    	
    	PageInfo<Student> pageStudent = new PageInfo<Student>(selectList);
    	
		//System.out.println(pageStudent);
		return selectList;
    }
    
    @RequestMapping("getStudentById.do")
	public Student getStudentById(Long id){
		Student selectByPrimaryKey = studentService.selectByPrimaryKey(id);
		return selectByPrimaryKey;
	}
	
	@RequestMapping("delStudent.do")
	public String delStudent(Long id){
		int deleteByPrimaryKey = studentService.deleteByPrimaryKey(id);
		return deleteByPrimaryKey + "";
	}
	
	@RequestMapping("updateStudent.do")
	public Student updateStudent(Student student){
		System.out.println(student);	
		Student result = studentService.updateByPrimaryKey(student);
		return result;
	}
	
	@RequestMapping("insert.do")
	public String insertStudent(Student student){
		int insert = studentService.insert(student);
		return insert+"";
	}
	
}
