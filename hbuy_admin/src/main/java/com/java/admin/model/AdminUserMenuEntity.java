package com.java.admin.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author djin
 *    AdminUserMenu实体类
 * @date 2020-03-07 10:04:53
 */
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class AdminUserMenuEntity implements Serializable{

	  private static final long serialVersionUID = 1L;
	
      //后台用户主键
	  private Integer userid;
      //后台菜单主键
	  private Integer menuid;
      //主键id
	  private Integer umid;

	  /**
	   * 设置：后台用户主键
	   */
	  public void setUserid(Integer userid) {
		  this.userid = userid;
	  }
	  /**
	   * 获取：后台用户主键
	   */
	  public Integer getUserid() {
	   	  return userid;
	  }
	  /**
	   * 设置：后台菜单主键
	   */
	  public void setMenuid(Integer menuid) {
		  this.menuid = menuid;
	  }
	  /**
	   * 获取：后台菜单主键
	   */
	  public Integer getMenuid() {
	   	  return menuid;
	  }
	  /**
	   * 设置：主键id
	   */
	  public void setUmid(Integer umid) {
		  this.umid = umid;
	  }
	  /**
	   * 获取：主键id
	   */
	  public Integer getUmid() {
	   	  return umid;
	  }

	 
	  @Override
	  public String toString() {
		  return  ReflectionToStringBuilder.toString(this);
	  }

}
