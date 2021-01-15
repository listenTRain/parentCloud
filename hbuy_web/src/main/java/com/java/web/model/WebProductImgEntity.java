package com.java.web.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author djin
 *    WebProductImg实体类
 * @date 2020-03-09 10:05:59
 */
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class WebProductImgEntity implements Serializable{

	  private static final long serialVersionUID = 1L;
	
      //图片id
	  private Long id;
      //图片
	  private String imgurl;
      //商品id
	  private Long productid;

	  /**
	   * 设置：图片id
	   */
	  public void setId(Long id) {
		  this.id = id;
	  }
	  /**
	   * 获取：图片id
	   */
	  public Long getId() {
	   	  return id;
	  }
	  /**
	   * 设置：图片
	   */
	  public void setImgurl(String imgurl) {
		  this.imgurl = imgurl;
	  }
	  /**
	   * 获取：图片
	   */
	  public String getImgurl() {
	   	  return imgurl;
	  }
	  /**
	   * 设置：商品id
	   */
	  public void setProductid(Long productid) {
		  this.productid = productid;
	  }
	  /**
	   * 获取：商品id
	   */
	  public Long getProductid() {
	   	  return productid;
	  }

	 
	  @Override
	  public String toString() {
		  return  ReflectionToStringBuilder.toString(this);
	  }

}
