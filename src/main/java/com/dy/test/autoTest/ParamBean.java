package com.dy.test.autoTest;

import lombok.Data;

@Data
public class ParamBean {
	private String type;
	private String name;
	private String description;
	private String listType;//集合类型
	private String Clazz;//对应对象类名

	public ParamBean() {
		super();
	}

	public ParamBean(String type, String name, String description) {
		super();
		this.type = type;
		this.name = name;
		this.description = description;
	}

	public ParamBean(String name, String description) {
		this.name = name;
		this.description = description;
	}


	@Override
	public String toString() {
		return "ParamBean [type=" + type + ", name=" + name + ", description=" + description + ", listType=" + listType
				+ "]";
	}


	

	
	
	
	
	
}
