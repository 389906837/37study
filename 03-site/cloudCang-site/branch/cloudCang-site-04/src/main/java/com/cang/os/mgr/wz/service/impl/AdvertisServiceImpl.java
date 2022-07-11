package com.cang.os.mgr.wz.service.impl;

import java.io.IOException;
import java.util.Date;

import com.cang.os.common.dao.BaseMongoDao;
import com.cang.os.common.exception.ServiceException;
import com.cang.os.common.service.BaseServiceImpl;
import com.cang.os.mgr.wz.dao.AdvertisDao;
import com.cang.os.mgr.wz.service.AdvertisService;
import com.cang.os.bean.wz.Advertis;
import com.cang.os.bean.wz.Region;
import com.cang.os.common.utils.FtpUser;
import com.cang.os.mgr.wz.service.RegionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cang.os.common.utils.DateUtils;
import com.cang.os.common.utils.FtpUtils;
import com.cang.os.security.utils.SessionUserUtils;

@Service
public class AdvertisServiceImpl extends BaseServiceImpl<Advertis> implements
        AdvertisService {

	@Autowired
    AdvertisDao advertisDao;
	
	@Autowired
    RegionService aegionService;
	
	@Value("${ftp.ip}")
	private String ip;
	
	@Value("${ftp.port}")
	private String port;
	
	@Value("${ftp.userName}")
	private String userName;
	
	@Value("${ftp.password}")
	private String password;
	
	@Value("${ftp.imagePath}")
	private String ftpImagePath;

	
	@Override
	public BaseMongoDao<Advertis> getDao() {
		return advertisDao;
	}
	

	@Override
	public void save(MultipartFile titleImage,MultipartFile icoImage, Advertis advertis) {
		Advertis oldAdvertis= null;
		if (StringUtils.isNotBlank(advertis.getId()) && ((titleImage != null && titleImage.getSize() > 0) || icoImage != null && icoImage.getSize() > 0)){
			oldAdvertis=advertisDao.findById(advertis.getId());
		}
		 
        if (titleImage != null && titleImage.getSize() > 0) {
            // 1.文件上传
            String url = upload(titleImage, advertis.getSregionId(),"cotent");
            advertis.setScontentUrl(url);

        }else{
            if (oldAdvertis != null){
            	
            	advertis.setScontentUrl(oldAdvertis.getScontentUrl());
            	
            }
        }
        
        if (icoImage != null && icoImage.getSize() > 0) {
            // 1.文件上传
            String url = upload(icoImage, advertis.getSregionId(),"ico");
            advertis.setSicoUrl(url);

        }else{
            if (oldAdvertis != null){
            	
            	advertis.setSicoUrl(oldAdvertis.getSicoUrl());
            	
            }
        }
        
        // 如果id为空就就添加
        if (StringUtils.isBlank(advertis.getId())) {
            advertis.setTaddTime(DateUtils.getCurrentDateTime());
			advertis.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
			advertis.setTupdateTime(DateUtils.getCurrentDateTime());
            advertis.setSupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
			advertis.setIdelFlag(0);
			advertis.setId(null);
            this.insert(advertis);
		
        } else {
            // 修改
			advertis.setTupdateTime(DateUtils.getCurrentDateTime());
            advertis.setSupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
			advertis.setIdelFlag(0);
			advertisDao.update(advertis);
         
       
             
        }

		
	}
	




	public String upload(MultipartFile imgFile, String regionId,String path) {
		 if (StringUtils.isBlank(regionId)){
	         throw new ServiceException("没有找到运营区域");
	     }
	    if (imgFile == null){
	        throw new ServiceException("没有找到上传文件");
	    }
	    String url ="";
	     try {
	    	  Region region = aegionService.findById(regionId);
	          String code = region.getScode();
	          String serverPath ="article/"+ DateUtils.dateParseShortString(new Date()) + "/"+ code + "/"+path+"/";
	          String fileFileName = imgFile.getOriginalFilename();
	          fileFileName = fileFileName.substring(fileFileName.lastIndexOf("."));
	          String tempName = DateUtils.getCurrentSTimeNumber()+fileFileName;
	          FtpUser ftpUser = new FtpUser(ip, port, userName,  password);
	         if(FtpUtils.uploadToFtpServer(imgFile.getInputStream(), serverPath, tempName, ftpUser)){
	              url =ftpImagePath+ "/"+serverPath+ tempName;
	              return url;
	         }
	     } catch (IOException e) {
	         throw new ServiceException("没有找到文件，上传失败");
	     }
	     return null;
	}

	
	

}