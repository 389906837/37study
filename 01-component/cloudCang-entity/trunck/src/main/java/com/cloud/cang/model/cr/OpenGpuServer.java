package com.cloud.cang.model.cr;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** API服务和GPU服务关联表(CR_OPEN_GPU_SERVER) **/
public class OpenGpuServer extends GenericEntity  {

	private static final long serialVersionUID = 1L;
	
	/*主键*/
	private String id;
	/*主键*/
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		 this.id= id;
	}
	
	/* GPU服务器ID */
	private String sgpuId;
	
	public String getSgpuId(){
		return sgpuId;
	}
	
	public void setSgpuId(String sgpuId){
		this.sgpuId= sgpuId;
	}
	/* 云服务器ID */
	private String sopenServerId;
	
	public String getSopenServerId(){
		return sopenServerId;
	}
	
	public void setSopenServerId(String sopenServerId){
		this.sopenServerId= sopenServerId;
	}

		
}