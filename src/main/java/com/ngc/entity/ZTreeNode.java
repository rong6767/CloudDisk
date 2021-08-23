package com.ngc.entity;

import java.io.Serializable;

public class ZTreeNode implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7314062363606859687L;
	private Integer id;
    private Integer pid;
    private String name;
    private Boolean open;
    private Boolean isParent;
	public ZTreeNode() {
		super();
	}
	public ZTreeNode(Integer id, Integer pid, String name, Boolean open, Boolean isParent) {
		super();
		this.id = id;
		this.pid = pid;
		this.name = name;
		this.open = open;
		this.isParent = isParent;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getOpen() {
		return open;
	}
	public void setOpen(Boolean open) {
		this.open = open;
	}
	public Boolean getIsParent() {
		return isParent;
	}
	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}
	@Override
	public String toString() {
		return "ZTreeNode [id=" + id + ", pid=" + pid + ", name=" + name + ", open=" + open + ", isParent=" + isParent
				+ "]";
	}
    
	
}
