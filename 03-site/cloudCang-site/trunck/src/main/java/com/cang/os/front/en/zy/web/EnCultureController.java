/**
 * 
 */
package com.cang.os.front.en.zy.web;

import java.util.List;

import com.cang.os.bean.wz.AlbumImage;
import com.cang.os.bean.wz.Album;
import com.cang.os.mgr.wz.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cang.os.mgr.wz.service.AlbumImageService;

/**
 * @author hunter
 *
 */
@Controller
@RequestMapping("/en/zy")
public class EnCultureController {
	
	@Autowired
    AlbumService albumService;
	
	@Autowired
	AlbumImageService albumImageService;
	

	/**
	 * 企业文化
	 * @param map
	 * @return
	 */
	@RequestMapping("/culture")
   public String culture(ModelMap map) {
	    Query query = new Query();
		query.addCriteria(Criteria.where("isdispaly").is(1));
		query.with(new Sort(Direction.ASC, "isort").and(new Sort(Direction.DESC, "taddTime")));
		List<Album> albums = albumService.find(query);
		map.put("albums", albums);
		return "front/en/zy/enCulture";
   }
	 /**
	  * 相册
	  * @param albumId
	  * @param map
	  * @return
	  */
	 @RequestMapping("/albumImage/{albumId}")
	 public String albumImage(@PathVariable String albumId,ModelMap map) {
		 
		    Album album = albumService.findById(albumId);
		    map.put("album", album);
		    Query query = new Query();
			query.addCriteria(Criteria.where("albumId").is(albumId));
			query.with(new Sort(Direction.ASC, "isort").and(new Sort(Direction.DESC, "uploadDate")));
			List<AlbumImage> albumimgs =  albumImageService.find(query);
			map.put("albumimgs", albumimgs);
			return "front/en/zy/albumImage";
	   }

}
