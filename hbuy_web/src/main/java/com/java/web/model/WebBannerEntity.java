package com.java.web.model;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author djin
 *    WebBanner实体类
 * @date 2020-03-09 10:06:00
 */
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class WebBannerEntity implements Serializable{

	  private static final long serialVersionUID = 1L;
	
      //主键
	  private Long id;
      //图片地址
	  private String imgurl;
      //跳转链接
	  private String href;
      //备注
	  private String remark;
      //排序
	  private Integer sort;
      //修改时间
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
	   * 设置：图片地址
	   */
	  public void setImgurl(String imgurl) {
		  this.imgurl = imgurl;
	  }
	  /**
	   * 获取：图片地址
	   */
	  public String getImgurl() {
	   	  return imgurl;
	  }
	  /**
	   * 设置：跳转链接
	   */
	  public void setHref(String href) {
		  this.href = href;
	  }
	  /**
	   * 获取：跳转链接
	   */
	  public String getHref() {
	   	  return href;
	  }
	  /**
	   * 设置：备注
	   */
	  public void setRemark(String remark) {
		  this.remark = remark;
	  }
	  /**
	   * 获取：备注
	   */
	  public String getRemark() {
	   	  return remark;
	  }
	  /**
	   * 设置：排序
	   */
	  public void setSort(Integer sort) {
		  this.sort = sort;
	  }
	  /**
	   * 获取：排序
	   */
	  public Integer getSort() {
	   	  return sort;
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
