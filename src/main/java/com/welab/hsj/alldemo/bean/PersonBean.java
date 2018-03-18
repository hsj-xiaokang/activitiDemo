package com.welab.hsj.alldemo.bean;

import java.io.Serializable;

/**
 * 测试流程变量的Javabean方式设置变量
 * 
 * 注意注意，主要的事情说三遍：实现序列化的id，避免添加的字段域造成无法读物反序列化的内容！！！
 * 
 * @author hsj
 *
 */
public class PersonBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private int age;

	public PersonBean(int id, String name, int age) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
	}

	public PersonBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "PersonBean [id=" + id + ", name=" + name + ", age=" + age + "]";
	}
	

}
