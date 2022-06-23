package com.dy.test.autoTest;

public class ParamBean {
	private String type;
	private String name;
	private String description;
	private String listType;//集合类型
	
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

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getListType() {
		return listType;
	}

	public void setListType(String listType) {
		this.listType = listType;
	}

	@Override
	public String toString() {
		return "ParamBean [type=" + type + ", name=" + name + ", description=" + description + ", listType=" + listType
				+ "]";
	}

	
	

	
	
	
	
	
}
