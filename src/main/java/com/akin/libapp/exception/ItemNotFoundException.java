package com.akin.libapp.exception;

public class ItemNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4682509539966699453L;

	private String className;
	private String id;
	
	public ItemNotFoundException(String className, String id) {
		this.className = className;
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
