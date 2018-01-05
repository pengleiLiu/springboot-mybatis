package com.lpl.springboot1.pojo;

import java.io.Serializable;

public class Student implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;//主键
	private String name;//姓名
	private Integer age;//年龄
	private String sex;//性别 0女 1男
	
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public Integer getAge() {
		return age;
	}
	public String getSex() {
		return sex;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
}

