package com.java.web.model;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author djin
 *    WebSeckill实体类
 * @date 2020-03-09 10:05:58
 */
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class WebSeckillEntity implements Serializable{

	  private static final long serialVersionUID = 1L;
	
      //秒杀主键
	  private Long id;
      //商品id
	  private Long productid;
      //秒杀名额
	  private Integer num;
      //秒杀价
	  private Float seckillprice;
      //开始时间
      @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss" ,timezone = "GMT+8")
	  private Date starttime;
      //结束时间
      @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss" ,timezone = "GMT+8")
	  private Date endtime;
      //0未开始，1已开始，2已经结束
	  private String status;
      //秒杀详情页
	  private String href;

	  /**
	   * 设置：秒杀主键
	   */
	  public void setId(Long id) {
		  this.id = id;
	  }
	  /**
	   * 获取：秒杀主键
	   */
	  public Long getId() {
	   	  return id;
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
	  /**
	   * 设置：秒杀名额
	   */
	  public void setNum(Integer num) {
		  this.num = num;
	  }
	  /**
	   * 获取：秒杀名额
	   */
	  public Integer getNum() {
	   	  return num;
	  }
	  /**
	   * 设置：秒杀价
	   */
	  public void setSeckillprice(Float seckillprice) {
		  this.seckillprice = seckillprice;
	  }
	  /**
	   * 获取：秒杀价
	   */
	  public Float getSeckillprice() {
	   	  return seckillprice;
	  }
	  /**
	   * 设置：开始时间
	   */
	  public void setStarttime(Date starttime) {
		  this.starttime = starttime;
	  }
	  /**
	   * 获取：开始时间
	   */
	  public Date getStarttime() {
	   	  return starttime;
	  }
	  /**
	   * 设置：结束时间
	   */
	  public void setEndtime(Date endtime) {
		  this.endtime = endtime;
	  }
	  /**
	   * 获取：结束时间
	   */
	  public Date getEndtime() {
	   	  return endtime;
	  }
	  /**
	   * 设置：0未开始，1已开始，2已经结束
	   */
	  public void setStatus(String status) {
		  this.status = status;
	  }
	  /**
	   * 获取：0未开始，1已开始，2已经结束
	   */
	  public String getStatus() {
	   	  return status;
	  }
	  /**
	   * 设置：秒杀详情页
	   */
	  public void setHref(String href) {
		  this.href = href;
	  }
	  /**
	   * 获取：秒杀详情页
	   */
	  public String getHref() {
	   	  return href;
	  }

	 
	  @Override
	  public String toString() {
		  return  ReflectionToStringBuilder.toString(this);
	  }

}
