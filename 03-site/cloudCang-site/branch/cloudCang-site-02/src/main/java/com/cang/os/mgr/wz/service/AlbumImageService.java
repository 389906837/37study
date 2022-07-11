package com.cang.os.mgr.wz.service;

import com.cang.os.bean.wz.AlbumImage;
import com.cang.os.common.service.BaseService;
import org.springframework.web.multipart.MultipartFile;

public interface AlbumImageService extends BaseService<AlbumImage> {
	
	void save(MultipartFile titleImage, AlbumImage albumImage);
 
    
}