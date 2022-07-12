package com.cloud.cang.mgr.sp.service.impl;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
import com.cloud.cang.mgr.common.vo.SelectVo;
import com.cloud.cang.mgr.sh.dao.MerchantInfoDao;
import com.cloud.cang.mgr.sp.dao.CategoryDao;
import com.cloud.cang.mgr.sp.dao.CommodityInfoDao;
import com.cloud.cang.mgr.sp.domain.CategoryDomain;
import com.cloud.cang.mgr.sp.service.CategoryService;
import com.cloud.cang.mgr.sp.vo.CategoryVo;
import com.cloud.cang.mgr.vr.dao.CommoditySkuDao;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sp.Category;
import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.model.vr.CommoditySku;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl extends GenericServiceImpl<Category, String> implements
		CategoryService {

	private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

	@Autowired
	CategoryDao categoryDao;

	@Autowired
	CommoditySkuDao commoditySkuDao;

	@Autowired
	CommodityInfoDao commodityInfoDao;

	@Autowired
	MerchantInfoDao merchantInfoDao;
	
	@Override
	public GenericDao<Category, String> getDao() {
		return categoryDao;
	}

	/**
	 * 获取分类
	 * @param smerchantId 商户Id
	 * @return
	 */
	@Override
	public List<SelectVo> selectByMerchantId(String smerchantId) {
		return categoryDao.selectByMerchantId(smerchantId);
	}

	@Override
	public Page<CategoryDomain> selectPageByDomainWhere(Page<CategoryDomain> page, CategoryVo categoryVo) {

		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return (Page<CategoryDomain>) categoryDao.selectByDomainWhere(categoryVo);
	}

	@Override
	public void batchLogicDelByIds(String[] checkboxId, String merchantId) {
		Category category = null;
		List<Category> smallCategory = categoryDao.selectSmallCategoryByMerchantId(merchantId);

		String user = SessionUserUtils.getSessionAttributeForUserDtl().getRealName();

		// 遍历分类，查看下面是否有子类
		for (String id : checkboxId) {
			Category category1 = categoryDao.selectByPrimaryKey(id);
			if (CollectionUtils.isNotEmpty(smallCategory)) {
				for (Category category2 : smallCategory) {
					if (null != category1.getId() && category1.getId().equals(category2.getSparentId())) {
						throw new ServiceException(MessageSourceUtils.getMessageByKey("spcon.delete.category.exception",null));
					}
				}
			}
		}

		// 视觉商品
		MerchantInfo merchantInfo = merchantInfoDao.selectByPrimaryKey(merchantId);
		if (null != merchantInfo) {
			List<CommoditySku> commoditySkuList = null;
			if (merchantInfo.getIvmSkuType() == 2) {		// 部分视觉商品
				Map<String, String> map = new HashMap<>();
				map.put("smerchantId", merchantId);
				commoditySkuList = commoditySkuDao.selectByMerchantId(map);

			} else {    // 全部视觉商品
				Map<String, String> map = new HashMap<>();
				map.put("idelFlag","0");
				map.put("istatus","10");
				map.put("ionlineStatus","20");
				commoditySkuList = commoditySkuDao.selectByMapWhere(map);
			}

			if (CollectionUtils.isNotEmpty(commoditySkuList)) {
				for (String id : checkboxId) {
					for (CommoditySku vr : commoditySkuList) {
						if (id.equals(vr.getSbigCategoryId())) {
							throw new ServiceException(MessageSourceUtils.getMessageByKey("spcon.delete.category.parent.error",null));
						} else if (id.equals(vr.getSsmallCategoryId())) {
							throw new ServiceException(MessageSourceUtils.getMessageByKey("spcon.delete.category.status.error",null));
						}
					}
				}
			}
		}


		// 普通商品
		Map<String, String> commodityMap = new HashMap<>();
		commodityMap.put("istatus", "10");
		commodityMap.put("smerchantId", merchantId);
		List<CommodityInfo> commodityInfoList = commodityInfoDao.selectByMapWhere(commodityMap);
		if (CollectionUtils.isNotEmpty(commodityInfoList)) {
			for (String id : checkboxId) {
				for (CommodityInfo com : commodityInfoList) {
					if (id.equals(com.getSbigCategoryId())) {
						throw new ServiceException(MessageSourceUtils.getMessageByKey("spcon.delete.category.big.status.error",null));
					} else if (id.equals(com.getSsmallCategoryId())) {
						throw new ServiceException(MessageSourceUtils.getMessageByKey("spcon.delete.category.small.status.error",null));
					}
				}
			}
		}

		// 校验通过，开始删除分类
		for (String id : checkboxId) {
			if (StringUtils.isNotBlank(id)) {

				Date updateStamp = DateUtils.getCurrentDateTime();
				category = new Category();
				category.setId(id);
				category.setIdelFlag(1);//逻辑删除，将表中是否删除改为1
				category.setSupdateUser(user);
				category.setTupdateTime(updateStamp);
				categoryDao.updateByIdSelective(category);

				// 判断是否是大类
				if (CollectionUtils.isNotEmpty(smallCategory)) {
					for (Category smallCategory1 : smallCategory) {
						if (smallCategory1.getId().equals(id)) {    //小类
							CommodityInfo commodityInfo = new CommodityInfo();
							commodityInfo.setSsmallCategoryId(id);
							commodityInfo.setTupdateTime(updateStamp);
							commodityInfo.setSupdateUser(user);
							commodityInfo.setSmerchantId(merchantId);
							commodityInfoDao.emptyCommoditySmallCategory(commodityInfo);

							CommoditySku commoditySku = new CommoditySku();
							commoditySku.setSsmallCategoryId(id);
							commoditySku.setTupdateTime(updateStamp);
							commoditySku.setSupdateUser(user);
							commoditySkuDao.emptyVrCommoditySmallCategory(commoditySku);

						} else {
							CommodityInfo commodityInfo = new CommodityInfo();
							commodityInfo.setSbigCategoryId(id);
							commodityInfo.setTupdateTime(updateStamp);
							commodityInfo.setSupdateUser(user);
							commodityInfo.setSmerchantId(merchantId);
							commodityInfoDao.emptyCommodityBigCategory(commodityInfo);

							CommoditySku commoditySku = new CommoditySku();
							commoditySku.setSbigCategoryId(id);
							commoditySku.setTupdateTime(updateStamp);
							commoditySku.setSupdateUser(user);
							commoditySkuDao.emptyVrCommodityBigCategory(commoditySku);
						}
					}

				}

			}
		}
	}

	/**
	 * 根据商户ID查询商品父类集合
	 * @param merchantId 商户ID
	 * @return
	 */
	@Override
	public List<Category> selectBigCategoryByMerchantId(String merchantId) {
		return categoryDao.selectBigCategoryByMerchantId(merchantId);
	}

	/**
	 * 根据商户ID查询商品子类集合
	 * @param merchantId 商户ID
	 * @return
	 */
	@Override
	public List<Category> selectSmallCategoryByMerchantId(String merchantId) {
		return categoryDao.selectSmallCategoryByMerchantId(merchantId);
	}

	/**
	 * 根据大类ID查询小类
	 * @param pid
	 * @return
	 */
	@Override
	public List<Category> selectSmallCategoryByPid(String pid) {
		return categoryDao.selectSmallCategoryByPid(pid);
	}

	@Override
	public int updateBySelectiveVo(Category category) {
		return categoryDao.updateByIdSelectiveVo(category);
	}

	/**
	 * 查询有所有效的大类信息
	 *
	 * @return
	 */
	@Override
	public List<Category> selectAllValidBigCategory(String merchantId) {
		CategoryDomain category = new CategoryDomain();
		category.setIisDisplay(1);
		category.setIdelFlag(0);
		category.setSmerchantId(merchantId);
		category.setIisParent(1);
		return categoryDao.selectValidBigCategory(category);
	}

	@Override
	public List<Category> queryBigCategor() {
		return categoryDao.queryBigCategor();
	}

	@Override
	public List<Category> queryData(String name) {
		return categoryDao.queryData(name);
	}

	@Override
	public List<Category> selectBigCategoryByCategoryCode(String scategoryCode, String smerchantId) {
		Map map = new HashMap();
		map.put("scategoryCode", scategoryCode);
		map.put("smerchantId", smerchantId);
		return categoryDao.selectBigCategoryByCategoryCode(map);
	}

	@Override
	public List<Category> selectSmallCategoryByBigCategory(String sparentId, String smerchantId) {
		Map map = new HashMap();
		map.put("sparentId", sparentId);
		map.put("smerchantId", smerchantId);
		return categoryDao.selectSmallCategoryByBigCategory(map);
	}

	/**
	 * 初始化分类数据
	 *
	 * @return
	 */
	@Override
	public ResponseVo<String> initCategory() {
		// 1.新增分类编号，新旧数据割接
		Category categoryVo = new Category();
		categoryVo.setIdelFlag(0);
		List<Category> allCategory = categoryDao.selectByEntityWhere(categoryVo);
		if (CollectionUtils.isNotEmpty(allCategory)) {
			for (Category c : allCategory) {
				if (StringUtils.isBlank(c.getScode())) {
					Category categoryTemp = new Category();
					String code = CoreUtils.newCode(EntityTables.SP_CATEGORY_SMALL);//商品大/小类编号
					if (StringUtils.isNotBlank(code)) {
						categoryTemp.setScode(code);
						categoryTemp.setId(c.getId());
						categoryDao.updateByIdSelective(categoryTemp);
					}
				}
			}
			logger.debug("分类编号数据初始化完成");
		} else {
			return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("spcon.init.category.error",null));
		}
		return ResponseVo.getSuccessResponse(MessageSourceUtils.getMessageByKey("spcon.init.category.success",null));
	}

}