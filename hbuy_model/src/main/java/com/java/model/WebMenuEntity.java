package com.java.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author djin
 *    WebMenu实体类
 * @date 2020-03-07 10:04:53
 */
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class WebMenuEntity implements Serializable{

	  private static final long serialVersionUID = 1L;
	
      //主键
	  private Long id;
      //菜单名
	  private String title;
      //跳转链接
	  private String url;
      //菜单类型(1横向菜单,2纵向菜单)
	  private String menutype;
      //最新修改时间
      @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss" ,timezone = "GMT+8")
	  private Date updatetime;

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
	  public void setTitle(String title) {
		  this.title = title;
	  }
	  /**
	   * 获取：菜单名
	   */
	  public String getTitle() {
	   	  return title;
	  }
	  /**
	   * 设置：跳转链接
	   */
	  public void setUrl(String url) {
		  this.url = url;
	  }
	  /**
	   * 获取：跳转链接
	   */
	  public String getUrl() {
	   	  return url;
	  }
	  /**
	   * 设置：菜单类型(1横向菜单,2纵向菜单)
	   */
	  public void setMenutype(String menutype) {
		  this.menutype = menutype;
	  }
	  /**
	   * 获取：菜单类型(1横向菜单,2纵向菜单)
	   */
	  public String getMenutype() {
	   	  return menutype;
	  }
	  /**
	   * 设置：最新修改时间
	   */
	  public void setUpdatetime(Date updatetime) {
		  this.updatetime = updatetime;
	  }
	  /**
	   * 获取：最新修改时间
	   */
	  public Date getUpdatetime() {
	   	  return updatetime;
	  }

	 
	  @Override
	  public String toString() {
		  return  ReflectionToStringBuilder.toString(this);
	  }

}
