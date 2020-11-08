package com.koi.vo;

import java.io.Serializable;

/**
 * 响应类
 * @author ZLY
 *
 */
public class JsonReturn implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int code;
	
	private String text;
	
	private Object data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	
}
