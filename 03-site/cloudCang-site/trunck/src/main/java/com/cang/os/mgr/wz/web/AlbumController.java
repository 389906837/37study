/**
 * 
 */
package com.cang.os.mgr.wz.web;

import com.cang.os.common.core.Page;
import com.cang.os.common.core.ParamPage;
import com.cang.os.common.utils.MongoDbUtil;
import com.cang.os.bean.wz.Album;
import com.cang.os.mgr.wz.service.AlbumService;
import org.apache.commons.lang3.StringUtils;
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

/**
 * 相册管理
 * @author hunter
 *
 */
@Controller
@RequestMapping("/mgr/album")
public class AlbumController {
	
	@Autowired
    AlbumService albumService;
	
	/**
	 * 用户管理列表
	 * @param operatorVo
	 * @param paramPage
	 * @param map
	 * @return
	 */
	 @RequestMapping("/list")
     public String list(Album album, ParamPage paramPage, ModelMap map) {
	    Page<Album> page = new Page<Album>();
		page.setPageNum(paramPage.pageNo());
		page.setPageSize(paramPage.getLimit());
		
		Query query = new Query();
		if (StringUtils.isNotBlank(album.getSname())) {
			query.addCriteria(Criteria.where("sname").regex(MongoDbUtil.getLikeQueryCondition(album.getSname())));
		
		}
		
		if (null != album.getIsdispaly()) {
			query.addCriteria(Criteria.where("isdispaly").is(album.getIsdispaly()));
		}
		
		query.with(new Sort(Direction.ASC, "isort").and(new Sort(Direction.DESC, "uploadDate")));
		page=albumService.findPage(page, query) ;
		map.put("page", page);
    	return "mgr/wz/albumList";
    }
	 
	 /**
	  * 编辑相册管理
	  * @param id
	  * @return
	  */
	 @RequestMapping("/edit")
	 public ModelAndView edit(String id,ModelMap map){
		 if (StringUtils.isNotBlank(id)){
			 Album album = albumService.findById(id);
			 map.put("album", album);
			 
		 }
		
		 return new ModelAndView("mgr/wz/albumEdit");
	 }
	 
	 /**
	  * 保存相册
	  * @param id
	  * @return
	  */
	 @RequestMapping("/save")
	 public ModelAndView save(@RequestParam(value = "imgFile", required = false) MultipartFile imgFile,Album album,ModelMap map,String backUri) {
		 albumService.save( imgFile,album);
		 return new ModelAndView("redirect:/mgr/success").addObject("backUri",backUri);
	 }
	 
	 /**
	  * 删除相册
	  * @param id
	  * @return
	  */
	 @RequestMapping("/delete")
	 public ModelAndView delete(String id,String backUri){
		 albumService.removeById(id);
		 return new ModelAndView("redirect:/mgr/success").addObject("backUri",backUri);
	 }

}
