package com.java.web.model;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author djin
 *    WebSort实体类
 * @date 2020-03-09 10:05:55
 */
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class WebSortEntity implements Serializable{

	  private static final long serialVersionUID = 1L;
	
      //商品类型主键
	  private Long id;
      //类型名
	  private String sortname;
      //修改时间
      @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss" ,timezone = "GMT+8")
	  private Date updatetime;

	  /**
	   * 设置：商品类型主键
	   */
	  public void setId(Long id) {
		  this.id = id;
	  }
	  /**
	   * 获取：商品类型主键
	   */
	  public Long getId() {
	   	  return id;
	  }
	  /**
	   * 设置：类型名
	   */
	  public void setSortname(String sortname) {
		  this.sortname = sortname;
	  }
	  /**
	   * 获取：类型名
	   */
	  public String getSortname() {
	   	  return sortname;
	  }
	  /**
	   * 设置：修改时间
	   */
	  public void setUpdatetime(Date updatetime) {
		  this.updatetime = updatetime;
	  }
	  /**
	   * 获取：修改时间
	   */
	  public Date getUpdatetime() {
	   	  return updatetime;
	  }

	 
	  @Override
	  public String toString() {
		  return  ReflectionToStringBuilder.toString(this);
	  }

}
