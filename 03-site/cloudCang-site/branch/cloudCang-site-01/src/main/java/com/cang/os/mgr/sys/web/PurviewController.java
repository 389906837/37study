/**
 * 
 */
package com.cang.os.mgr.sys.web;

import java.util.List;

import com.cang.os.bean.sys.Purview;
import com.cang.os.common.core.Page;
import com.cang.os.common.core.ParamPage;
import com.cang.os.common.utils.DateUtils;
import com.cang.os.common.utils.MongoDbUtil;
import com.cang.os.common.utils.StringUtil;
import com.cang.os.common.utils.id.IdFactory;
import com.cang.os.mgr.sys.service.PurviewService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cang.os.common.core.OperatorConstant;

/**
 * @author zhouhong
 *
 */
@Controller
@RequestMapping("/mgr/purview")
public class PurviewController {
	private static final Logger log = LoggerFactory.getLogger(PurviewController.class);
	
	@Autowired
    PurviewService purviewService;
	
	 @RequestMapping("/list")
     public String list(Purview purview, ParamPage paramPage, ModelMap map) {
	    Page<Purview> page = new Page<Purview>();
		page.setPageNum(paramPage.pageNo());
		page.setPageSize(paramPage.getLimit());
		Query query = new Query();
		if (StringUtils.isNotBlank(purview.getSpurName())) {
			query.addCriteria(Criteria.where("spurName").regex(MongoDbUtil.getLikeQueryCondition(purview.getSpurName())));
		}
		
		if (StringUtils.isNotBlank(purview.getSparentId())) {
			query.addCriteria(Criteria.where("sparentId").is(purview.getSparentId()));
		}
		
		page=purviewService.findPage(page, query) ;
		map.put("page", page);
    	return "mgr/sys/purviewList";
    }
	 
	 
	 /**
	  * 编辑权限
	  * @param id
	  * @return
	  */
	 @RequestMapping("/edit")
	 public ModelAndView edit(String id,ModelMap map){
		 if (StringUtils.isNotBlank(id)){
			 Purview purview = purviewService.findById(id);
			 map.put("purview", purview);
			 
		 }
		 return new ModelAndView("mgr/sys/purviewEdit");
	 }
	 
	 @RequestMapping("/save")
	 public ModelAndView save(Purview purview,ModelMap map,String backUri){

	        try {
	            if (StringUtils.isBlank(purview.getSparentId())) {
	                map.put(OperatorConstant.RETURN_FLAG, "菜单ID为空！");
	                return new ModelAndView("mgr/sys/purviewEdit");
	            }
	            // 执行保存
	            if (StringUtils.isBlank(purview.getId())) {
	                if (isExistName(purview.getSpurCode())) {
	                    map.put(OperatorConstant.RETURN_FLAG, "权限编码已经存在");
		                return new ModelAndView("mgr/sys/purviewEdit");
	                }
	                purview.setSpurNo(IdFactory.getOutRandomSerialNumber());
	                purview.setDaddDate(DateUtils.getCurrentDateTime());
	                purview.setSpyName(StringUtil.getFullSpell(purview.getSpurName()));
	                purview.setSjpName(StringUtil.getFirstSpell(purview.getSpurName()));
	                purview.setId(null);
	                purviewService.insert(purview);
	            } else {// 执行更新
	                Purview presitPurview = purviewService.findById(purview.getId());
	                if (presitPurview != null)
	                {
	                    if (!presitPurview.getSpurCode().equalsIgnoreCase(purview.getSpurCode()))
	                    {
	                        if (isExistName(purview.getSpurCode())) {
	                        	 map.put(OperatorConstant.RETURN_FLAG, "权限编码已经存在");
	     		                return new ModelAndView("mgr/sys/purviewEdit");
	                        }
	                    }
	                }
	                purview.setSpyName(StringUtil.getFullSpell(purview.getSpurName()));
	                purview.setSjpName(StringUtil.getFirstSpell(purview.getSpurName()));
	                purviewService.update(purview);
	            }

	        } catch (Exception ex) {
	            log.error("",ex);
	            map.put(OperatorConstant.RETURN_FLAG, "权限码信息失败");
	            return new ModelAndView("mgr/sys/purviewEdit");
	          
	        }
	    
		 return new ModelAndView("redirect:/mgr/success").addObject("backUri",backUri);
	 }
	 
	 private boolean isExistName(String purCode){
		 Query query = new Query(Criteria.where("spurCode").is(purCode));
		 List<Purview> list = purviewService.find(query);
		 if (list != null && list.size() > 0){
			 return true;
		 }
		 return false;
	 }
	 
	 
	 /**
	  * 删除权限
	  * @param id
	  * @return
	  */
	 @RequestMapping("/delete")
	 public ModelAndView delete(String id,String backUri){
		 Query query = new Query(Criteria.where("id").is(id));
		 purviewService.remove(query);
		 return new ModelAndView("redirect:/mgr/success").addObject("backUri",backUri);
	 }

}
