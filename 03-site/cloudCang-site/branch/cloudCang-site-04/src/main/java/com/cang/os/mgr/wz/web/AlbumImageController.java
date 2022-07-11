/**
 * 
 */
package com.cang.os.mgr.wz.web;

import java.util.List;

import com.cang.os.bean.wz.AlbumImage;
import com.cang.os.common.core.Page;
import com.cang.os.common.core.ParamPage;
import com.cang.os.common.utils.MongoDbUtil;
import com.cang.os.bean.wz.Album;
import com.cang.os.mgr.wz.service.AlbumService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cang.os.common.core.OperatorConstant;
import com.cang.os.mgr.wz.service.AlbumImageService;

/**
 * 相片管理
 * @author zhouhong
 *
 */
@Controller
@RequestMapping("/mgr/albumImage")
public class AlbumImageController {
	
	private static final Logger logger = LoggerFactory.getLogger(AlbumImageController.class);
	
	@Autowired
    AlbumService albumService;
	
	@Autowired
	AlbumImageService albumImageService;
	
	/**
	 * 相片列表
	 * @param operatorVo
	 * @param paramPage
	 * @param map
	 * @return
	 */
	 @RequestMapping("/list")
     public String list(AlbumImage albumImage, ParamPage paramPage, ModelMap map) {
	    Page<AlbumImage> page = new Page<AlbumImage>();
		page.setPageNum(paramPage.pageNo());
		page.setPageSize(paramPage.getLimit());
		Query query = new Query();
		if (StringUtils.isNotBlank(albumImage.getAlbumId())) {
			query.addCriteria(Criteria.where("albumId").is(albumImage.getAlbumId()));
		
		}
		if (StringUtils.isNotBlank(albumImage.getDesc())) {
			query.addCriteria(Criteria.where("desc").regex(MongoDbUtil.getLikeQueryCondition(albumImage.getDesc())));
		
		}
		query.with(new Sort(Direction.ASC, "isort").and(new Sort(Direction.DESC, "uploadDate")));
		page=albumImageService.findPage(page, query) ;
		map.put("page", page);
		map.put("albums", getAlbum());
    	return "mgr/wz/albumImageList";
    }
	 
	 
	 private List<Album> getAlbum(){
		  return albumService.findAll();
	 }
	 
	 /**
	  * 编辑相片
	  * @param id
	  * @return
	  */
	 @RequestMapping("/edit")
	 public ModelAndView edit(String id,ModelMap map){
		 if (StringUtils.isNotBlank(id)){
			 AlbumImage albumImage = albumImageService.findById(id);
			 map.put("albumImage", albumImage);
			 
		 }
		 map.put("albums", getAlbum());
		return new ModelAndView("mgr/wz/albumImageEdit");
	 }
	 
	 /**
	  * 保存相册
	  * @param id
	  * @return
	  */
	 @RequestMapping("/save")
	 public ModelAndView save(@RequestParam(value = "imgFile", required = false) MultipartFile imgFile,AlbumImage albumImage,ModelMap map,String backUri) {
		 try {
			albumImageService.save( imgFile,albumImage);
		} catch (Exception e) {
			logger.error("", e);
		  map.put(OperatorConstant.RETURN_FLAG, e.getMessage());
		  return edit(albumImage.getId(),map);
		}
		 return new ModelAndView("redirect:/mgr/success").addObject("backUri",backUri);
	 }
	 
	 /**
	  * 删除相片
	  * @param id
	  * @return
	  */
	 @RequestMapping("/delete")
	 public ModelAndView delete(String id,String backUri){
		 AlbumImage albumImage = albumImageService.findById(id);
		 albumImageService.removeById(id);
		 Album album = albumService.findById(albumImage.getAlbumId());
		 Album update = new Album();
		 update.setIcount((album.getIcount() == null?0:album.getIcount()) -1);
		 update.setId(albumImage.getAlbumId());
		 albumService.update(update);
		 return new ModelAndView("redirect:/mgr/success").addObject("backUri",backUri);
	 }
	 


}
