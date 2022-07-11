package com.cang.os.mgr.wz.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.imageio.ImageIO;

import com.cang.os.bean.wz.AlbumImage;
import com.cang.os.common.dao.BaseMongoDao;
import com.cang.os.common.exception.ServiceException;
import com.cang.os.common.service.BaseServiceImpl;
import com.cang.os.common.utils.DateUtils;
import com.cang.os.common.utils.FtpUser;
import com.cang.os.common.utils.FtpUtils;
import com.cang.os.mgr.wz.dao.AlbumDao;
import com.cang.os.mgr.wz.dao.AlbumImageDao;
import com.cang.os.security.utils.SessionUserUtils;
import com.cang.os.bean.wz.Album;
import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cang.os.mgr.wz.service.AlbumImageService;

@Service
public class AlbumImageServiceImpl extends BaseServiceImpl<AlbumImage> implements
		AlbumImageService {
	
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
	
	@Value("${img.size1}")
	private Integer imgSize1;
	
	@Value("${img.size2}")
	private Integer imgSize2;

	@Autowired
    AlbumImageDao albumImageDao;
	
	@Autowired
    AlbumDao albumDao;

	
	@Override
	public BaseMongoDao<AlbumImage> getDao() {
		return albumImageDao;
	}


	@Override
	public void save(MultipartFile titleImage, AlbumImage albumImage) {
		Album album = albumDao.findById(albumImage.getAlbumId());
		if (null != titleImage && titleImage.getSize() > 0){
			albumImage.setContenturl(upload(titleImage, album,"albumImage"));
		}
		
		if (StringUtils.isBlank(albumImage.getId())){
			albumImage.setId(null);
			albumImage.setUloadUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
			albumImage.setUploadDate(new Date());
			albumImageDao.insert(albumImage);
			Album upateAlbum = new Album();
			upateAlbum.setId(albumImage.getAlbumId());
			upateAlbum.setIcount(album.getIcount()+1);
			albumDao.update(upateAlbum);
		}else {
			AlbumImage temp = albumImageDao.findById(albumImage.getId());
			if (!temp.getAlbumId().equals(albumImage.getAlbumId())){
				Album old = albumDao.findById(temp.getAlbumId());
				Album upateAlbum1 = new Album();
				upateAlbum1.setId(temp.getAlbumId());
				upateAlbum1.setIcount(old.getIcount() -1);
				albumDao.update(upateAlbum1);
				
				Album upateAlbum = new Album();
				upateAlbum.setId(albumImage.getAlbumId());
				upateAlbum.setIcount(album.getIcount()+1);
				albumDao.update(upateAlbum);
			}
			
			albumImage.setUloadUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
			albumImage.setUploadDate(new Date());
			albumImageDao.update(albumImage);
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
	          String timenumber = DateUtils.getCurrentSTimeNumber();
	          String tempName = timenumber+"_"+fileFileName;
	          FtpUser ftpUser = new FtpUser(ip, port, userName,  password);
	          BufferedImage bi = ImageIO.read(imgFile.getInputStream());
	          if (bi == null){
	        	  throw new ServiceException("上传文件不是图片文件！");
	          }
	          //消除
	          bi = null;
	         
	          cutImg(ftpUser,imgFile,serverPath,timenumber,fileFileName ,imgSize1,imgSize1);
	          cutImg(ftpUser,imgFile,serverPath,timenumber,fileFileName ,imgSize2,imgSize2);
             
	          
	         if(FtpUtils.uploadToFtpServer(imgFile.getInputStream(), serverPath, tempName, ftpUser)){
	        	
	              url =ftpImagePath+ "/"+serverPath+ tempName;
	              return url;
	         }
	     } catch (IOException e) {
	         throw new ServiceException("没有找到文件，上传失败");
	     }
	     return null;
	}
	
	
	private void cutImg(FtpUser ftpUser,MultipartFile imgFile,String serverPath,String timenumber,String fileFileName,Integer height,Integer width) throws IOException{
		 
		 ByteArrayOutputStream bs = new ByteArrayOutputStream(); 
		 Thumbnails.of(imgFile.getInputStream()).size(height, width).toOutputStream(bs);
		 InputStream inputStream = new ByteArrayInputStream(bs.toByteArray());
		 FtpUtils.uploadToFtpServer(inputStream, serverPath, timenumber+"_"+height+"x"+width+fileFileName, ftpUser);
		 if (bs != null){
			 bs.close();
		 }
	
		
	}

	
	

}