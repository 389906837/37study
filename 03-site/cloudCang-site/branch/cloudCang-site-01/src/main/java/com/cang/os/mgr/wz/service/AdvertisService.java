package com.cang.os.mgr.wz.service;

import com.cang.os.bean.wz.Advertis;
import org.springframework.web.multipart.MultipartFile;

import com.cang.os.common.service.BaseService;

public interface AdvertisService extends BaseService<Advertis> {
 
	void save(MultipartFile titleImage,MultipartFile icoImage, Advertis advertis);
}