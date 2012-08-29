/**
 * 
 */
package com.great.mytaxi.fe.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author xu.jianpu
 * 
 * 2012-8-29  下午03:15:56
 */
@Entity
@Table(name="sys_code")
public class SysCodeInfo {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id ;
	
	private String code ;
	
	
	private String name ;
	
	private String parmValue;
	
	private String updateTime ;
	
	private String editor ;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParmValue() {
		return parmValue;
	}

	public void setParmValue(String parmValue) {
		this.parmValue = parmValue;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}
	
	
}
