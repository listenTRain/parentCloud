package com.java.admin.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author djin
 *    AdminMenus实体类
 * @date 2020-03-07 10:04:53
 */
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class AdminMenusEntity implements Serializable{

	  private static final long serialVersionUID = 1L;
	
      //主键
	  private Long id;
      //菜单名
	  private String text;
      //图标名
	  private String iconcls;
      //跳转地址
	  private String url;
      //菜单展开状态
	  private String state;
      //父节点
	  private Long parentid;
      //1是授权模块  0非授权模块
	  private String flag;

	  /**
	   * 设置：主键
	   */
	  public void setId(Long id) {
		  this.id = id;
	  }
	  /**
	   * 获取：主键
	   */
	  public Long getId() {
	   	  return id;
	  }
	  /**
	   * 设置：菜单名
	   */
	  public void setText(String text) {
		  this.text = text;
	  }
	  /**
	   * 获取：菜单名
	   */
	  public String getText() {
	   	  return text;
	  }
	  /**
	   * 设置：图标名
	   */
	  public void setIconcls(String iconcls) {
		  this.iconcls = iconcls;
	  }
	  /**
	   * 获取：图标名
	   */
	  public String getIconcls() {
	   	  return iconcls;
	  }
	  /**
	   * 设置：跳转地址
	   */
	  public void setUrl(String url) {
		  this.url = url;
	  }
	  /**
	   * 获取：跳转地址
	   */
	  public String getUrl() {
	   	  return url;
	  }
	  /**
	   * 设置：菜单展开状态
	   */
	  public void setState(String state) {
		  this.state = state;
	  }
	  /**
	   * 获取：菜单展开状态
	   */
	  public String getState() {
	   	  return state;
	  }
	  /**
	   * 设置：父节点
	   */
	  public void setParentid(Long parentid) {
		  this.parentid = parentid;
	  }
	  /**
	   * 获取：父节点
	   */
	  public Long getParentid() {
	   	  return parentid;
	  }
	  /**
	   * 设置：1是授权模块  0非授权模块
	   */
	  public void setFlag(String flag) {
		  this.flag = flag;
	  }
	  /**
	   * 获取：1是授权模块  0非授权模块
	   */
	  public String getFlag() {
	   	  return flag;
	  }

	 
	  @Override
	  public String toString() {
		  return  ReflectionToStringBuilder.toString(this);
	  }

}
