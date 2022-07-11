package com.cang.os.mgr.wz.service.impl;

import java.io.IOException;
import java.util.Date;

import com.cang.os.common.dao.BaseMongoDao;
import com.cang.os.common.exception.ServiceException;
import com.cang.os.common.service.BaseServiceImpl;
import com.cang.os.common.utils.DateUtils;
import com.cang.os.common.utils.FtpUser;
import com.cang.os.common.utils.FtpUtils;
import com.cang.os.common.utils.id.IdFactory;
import com.cang.os.mgr.wz.dao.AlbumDao;
import com.cang.os.security.utils.SessionUserUtils;
import com.cang.os.bean.wz.Album;
import com.cang.os.mgr.wz.service.AlbumService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AlbumServiceImpl extends BaseServiceImpl<Album> implements
        AlbumService {

	@Autowired
    AlbumDao albumDao;
	
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
	public BaseMongoDao<Album> getDao() {
		return albumDao;
	}


	@Override
	public void save(MultipartFile titleImage, Album album) {
		
		if (null != titleImage && titleImage.getSize() > 0){
			album.setCoverimg(upload(titleImage, album,"cover"));
		}
		
		if (StringUtils.isBlank(album.getId())){
			album.setId(null);
			album.setIcount(0);
			album.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
			album.setIsdelete(0);
			album.setScode(IdFactory.getOutRandomSerialNumber());
			album.setSupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
			album.setTaddTime(new Date());
			album.setTupdateTime(new Date());
			albumDao.insert(album);
		}else{
			album.setTupdateTime(new Date());
			album.setSupateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
			album.setIsdelete(0);
			album.setIcount(null);
			albumDao.update(album);
		}
		
	}
	
	
	public String upload(MultipartFile imgFile, Album album,String path) {
		
	    if (imgFile == null){
	        throw new ServiceException("没有找到上传文件");
	    }
	    String url ="";
	     try {
	          String code = album.getScode();
	          String serverPath ="album/"+ DateUtils.dateParseShortString(new Date()) + "/"+ code + "/"+path+"/";
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