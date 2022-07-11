/**
 * 
 */
package com.cang.os.bean.wz;

import java.util.Date;

import com.cang.os.bean.BaseBean;

/**
 * @author zhouhong
 *
 */
/**相册管理*/
public class Album extends BaseBean {
	//主键
	private String id;
	
	//相册名称
	private String sname;
	
	//排序
	private Integer isort;
	
	//封面图片
	private String coverimg;
	
	//是否删除
	private Integer isdelete;
	
	//是否显示
	private Integer isdispaly;
	private Date taddTime;   //添加日期
	private String saddUser;   //添加人
	private Date tupdateTime;   //修改日期
	private String supateUser;   //修改人
	private String scode;   //编码
	private Integer icount;//相片张数
	private String remark;//说明
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public Integer getIsort() {
		return isort;
	}
	public void setIsort(Integer isort) {
		this.isort = isort;
	}
	public String getCoverimg() {
		return coverimg;
	}
	public void setCoverimg(String coverimg) {
		this.coverimg = coverimg;
	}
	public Integer getIsdelete() {
		return isdelete;
	}
	public void setIsdelete(Integer isdelete) {
		this.isdelete = isdelete;
	}
	public Integer getIsdispaly() {
		return isdispaly;
	}
	public void setIsdispaly(Integer isdispaly) {
		this.isdispaly = isdispaly;
	}
	public Date getTaddTime() {
		return taddTime;
	}
	public void setTaddTime(Date taddTime) {
		this.taddTime = taddTime;
	}
	public String getSaddUser() {
		return saddUser;
	}
	public void setSaddUser(String saddUser) {
		this.saddUser = saddUser;
	}
	public Date getTupdateTime() {
		return tupdateTime;
	}
	public void setTupdateTime(Date tupdateTime) {
		this.tupdateTime = tupdateTime;
	}
	public String getSupateUser() {
		return supateUser;
	}
	public void setSupateUser(String supateUser) {
		this.supateUser = supateUser;
	}
	public String getScode() {
		return scode;
	}
	public void setScode(String scode) {
		this.scode = scode;
	}
	public Integer getIcount() {
		return icount;
	}
	public void setIcount(Integer icount) {
		this.icount = icount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	

}
