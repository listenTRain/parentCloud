package com.java.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author djin
 *    WebOrder实体类
 * @date 2020-03-09 10:05:59
 */
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class WebOrderEntity implements Serializable{

	  private static final long serialVersionUID = 1L;
	
      //主键
	  private Integer id;
      //订单编号
	  private String orderno;
      //用户ID
	  private Long userid;
      //0已经创建 1未支付  2已经支付  3已经收货  4已经评价   5已失效
	  private String orderstatus;
      //金额
	  private Float cost;
      //秒杀id
	  private Long secid;
      //商品ids
	  private String proids;
      //下单时间
      @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss" ,timezone = "GMT+8")
	  private Date createdate;
      //失效时间
      @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss" ,timezone = "GMT+8")
	  private Date enddate;
      //来源  1为购物车   2为秒杀
	  private Integer flag;

	  /**
	   * 设置：主键
	   */
	  public void setId(Integer id) {
		  this.id = id;
	  }
	  /**
	   * 获取：主键
	   */
	  public Integer getId() {
	   	  return id;
	  }
	  /**
	   * 设置：订单编号
	   */
	  public void setOrderno(String orderno) {
		  this.orderno = orderno;
	  }
	  /**
	   * 获取：订单编号
	   */
	  public String getOrderno() {
	   	  return orderno;
	  }
	  /**
	   * 设置：用户ID
	   */
	  public void setUserid(Long userid) {
		  this.userid = userid;
	  }
	  /**
	   * 获取：用户ID
	   */
	  public Long getUserid() {
	   	  return userid;
	  }
	  /**
	   * 设置：0已经创建 1未支付  2已经支付  3已经收货  4已经评价   5已失效
	   */
	  public void setOrderstatus(String orderstatus) {
		  this.orderstatus = orderstatus;
	  }
	  /**
	   * 获取：0已经创建 1未支付  2已经支付  3已经收货  4已经评价   5已失效
	   */
	  public String getOrderstatus() {
	   	  return orderstatus;
	  }
	  /**
	   * 设置：金额
	   */
	  public void setCost(Float cost) {
		  this.cost = cost;
	  }
	  /**
	   * 获取：金额
	   */
	  public Float getCost() {
	   	  return cost;
	  }
	  /**
	   * 设置：秒杀id
	   */
	  public void setSecid(Long secid) {
		  this.secid = secid;
	  }
	  /**
	   * 获取：秒杀id
	   */
	  public Long getSecid() {
	   	  return secid;
	  }
	  /**
	   * 设置：商品ids
	   */
	  public void setProids(String proids) {
		  this.proids = proids;
	  }
	  /**
	   * 获取：商品ids
	   */
	  public String getProids() {
	   	  return proids;
	  }
	  /**
	   * 设置：下单时间
	   */
	  public void setCreatedate(Date createdate) {
		  this.createdate = createdate;
	  }
	  /**
	   * 获取：下单时间
	   */
	  public Date getCreatedate() {
	   	  return createdate;
	  }
	  /**
	   * 设置：失效时间
	   */
	  public void setEnddate(Date enddate) {
		  this.enddate = enddate;
	  }
	  /**
	   * 获取：失效时间
	   */
	  public Date getEnddate() {
	   	  return enddate;
	  }
	  /**
	   * 设置：来源  1为购物车   2为秒杀
	   */
	  public void setFlag(Integer flag) {
		  this.flag = flag;
	  }
	  /**
	   * 获取：来源  1为购物车   2为秒杀
	   */
	  public Integer getFlag() {
	   	  return flag;
	  }

	 
	  @Override
	  public String toString() {
		  return  ReflectionToStringBuilder.toString(this);
	  }

}
