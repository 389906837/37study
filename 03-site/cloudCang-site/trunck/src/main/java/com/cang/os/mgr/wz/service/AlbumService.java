package com.cang.os.mgr.wz.service;

import com.cang.os.common.service.BaseService;
import com.cang.os.bean.wz.Album;
import org.springframework.web.multipart.MultipartFile;

public interface AlbumService extends BaseService<Album> {
	
	void save(MultipartFile titleImage, Album album);
 
    
}