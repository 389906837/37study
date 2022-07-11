/**
 * 
 */
package com.cang.os.bean.wz;

import java.util.Date;

import com.cang.os.bean.BaseBean;

/**
 * @author hunter
 *
 */
/**相册图片*/
public class AlbumImage extends BaseBean {
	
	private String id;
	//相册ID
	private String albumId;
	//相册名称
	private String albumName;
	//相册描叙
	private String desc;
	//上传日期
	private Date uploadDate;
	//排序
	private Integer isort;
	//上传人
	private String uloadUser;
	//内容url
	private String contenturl;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAlbumId() {
		return albumId;
	}
	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	public Integer getIsort() {
		return isort;
	}
	public void setIsort(Integer isort) {
		this.isort = isort;
	}
	public String getUloadUser() {
		return uloadUser;
	}
	public void setUloadUser(String uloadUser) {
		this.uloadUser = uloadUser;
	}
	public String getAlbumName() {
		return albumName;
	}
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	public String getContenturl() {
		return contenturl;
	}
	public void setContenturl(String contenturl) {
		this.contenturl = contenturl;
	}
	
}
