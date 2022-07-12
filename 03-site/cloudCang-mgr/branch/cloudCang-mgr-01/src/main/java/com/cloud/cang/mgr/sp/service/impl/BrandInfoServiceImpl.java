package com.cloud.cang.mgr.sp.service.impl;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.utils.FtpParamUtil;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.common.vo.SelectVo;
import com.cloud.cang.mgr.sh.dao.MerchantInfoDao;
import com.cloud.cang.mgr.sp.dao.BrandInfoDao;
import com.cloud.cang.mgr.sp.dao.CommodityInfoDao;
import com.cloud.cang.mgr.sp.domain.BrandInfoDomain;
import com.cloud.cang.mgr.sp.service.BrandInfoService;
import com.cloud.cang.mgr.sp.vo.BrandInfoVo;
import com.cloud.cang.mgr.vr.dao.CommoditySkuDao;
import com.cloud.cang.model.sp.BrandInfo;
import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.model.vr.CommoditySku;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.FtpUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class BrandInfoServiceImpl extends GenericServiceImpl<BrandInfo, String> implements
		BrandInfoService {

	private static final Logger logger = LoggerFactory.getLogger(BrandInfoServiceImpl.class);

	@Autowired
	BrandInfoDao brandInfoDao;

	@Autowired
	CommodityInfoDao commodityInfoDao;

	@Autowired
	CommoditySkuDao commoditySkuDao;

	@Autowired
	MerchantInfoDao merchantInfoDao;

	
	@Override
	public GenericDao<BrandInfo, String> getDao() {
		return brandInfoDao;
	}

	/**
	 * 获取品牌
	 * @param smerchantId 商户Id
	 * @return
	 */
	@Override
	public List<SelectVo> selectByMerchantId(String smerchantId) {
		return brandInfoDao.selectByMerchantId(smerchantId);
	}

	@Override
	public Page<BrandInfoDomain> selectPageByDomainWhere(Page<BrandInfoDomain> page, BrandInfoVo brandInfoVo) {

		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return (Page<BrandInfoDomain>) brandInfoDao.selectByDomainWhere(brandInfoVo);
	}

	/**
	 * 如果有商品正在使用此品牌，不能删除
	 *
	 * @param checkboxId
	 */
	@Override
	public ResponseVo<String> batchLogicDelByIds(String[] checkboxId, String merchantId) {
//		CommoditySku commoditySku = null;
//		CommodityInfo commodityInfo = null;

		BrandInfo brandInfo = null;
		String updateUserName = SessionUserUtils.getSessionAttributeForUserDtl().getRealName();
		Date updateTime = DateUtils.getCurrentDateTime();
		ResponseVo<String> responseVo = ResponseVo.getSuccessResponse();

		for (String brandId : checkboxId) {

			// 判断是否被视觉商品使用
			CommoditySku commoditySkuVo = new CommoditySku();
			commoditySkuVo.setSbrandId(brandId);
			commoditySkuVo.setIdelFlag(0);
			List<CommoditySku> commoditySkuList = commoditySkuDao.selectByEntityWhere(commoditySkuVo);
			if (CollectionUtils.isNotEmpty(commoditySkuList)) {
				throw new ServiceException("该品牌被视觉商品使用，无法删除");
			}

			// 判断是否被普通商品使用
			CommodityInfo commodityInfoVo = new CommodityInfo();
			commodityInfoVo.setSbrandId(brandId);
			commodityInfoVo.setIdelFlag(0);
			List<CommodityInfo> commodityInfoList = commodityInfoDao.selectByEntityWhere(commodityInfoVo);
			if (CollectionUtils.isNotEmpty(commodityInfoList)) {
				throw new ServiceException("该品牌被商品使用，无法删除");
			}

			// 逻辑删除品牌信息
			brandInfo = new BrandInfo();
			brandInfo.setId(brandId);
			brandInfo.setIdelFlag(1);//逻辑删除，将表中是否删除改为1
			brandInfo.setSupdateUser(updateUserName);
			brandInfo.setTupdateTime(updateTime);
			brandInfo.setSmerchantId(merchantId);
			brandInfoDao.updateByIdSelective(brandInfo);
		}

		//检验品牌是否被视觉商品使用
//		MerchantInfo merchantInfo = merchantInfoDao.selectByPrimaryKey(merchantId);
//		if (null != merchantInfo) {
//			List<CommoditySku> commoditySkuList = null;
//			if (merchantInfo.getIvmSkuType() == 2) {		// 部分视觉商品
//				Map<String, String> map = new HashMap<>();
//				map.put("smerchantId", merchantId);
//				commoditySkuList = commoditySkuDao.selectByMerchantId(map);
//
//			} else {    // 全部视觉商品
//				Map<String, String> map = new HashMap<>();
//				map.put("idelFlag","0");
//				map.put("ionlineStatus","20");
//				commoditySkuList = commoditySkuDao.selectByMapWhere(map);
//			}
//
//			if (CollectionUtils.isNotEmpty(commoditySkuList)) {
//				for (String id : checkboxId) {
//					for (CommoditySku vr : commoditySkuList) {
//						if (id.equals(vr.getSbrandId())) {
//							throw new ServiceException("该品牌被部分视觉商品正常使用，无法删除");
//						}
//					}
//				}
//			}
//		}
//
//		//检验品牌是否被商品使用
//		Map<String, String> map = new HashMap<>();
//		map.put("smerchantId", merchantId);
//		map.put("idelFlag", "0");
//		map.put("istatus", "10");
//		List<CommodityInfo> commodityInfoList = commodityInfoDao.selectByMapWhere(map);
//		if (CollectionUtils.isNotEmpty(commodityInfoList)) {
//			for (String id : checkboxId) {
//				for (CommodityInfo com : commodityInfoList) {
//					if (id.equals(com.getSbrandId())) {
//						throw new ServiceException("该品牌被部分商品正常使用，无法删除");
//					}
//				}
//			}
//		}
//
//		for (String id : checkboxId) {
//			brandInfo = new BrandInfo();
//			brandInfo.setId(id);
//			brandInfo.setIdelFlag(1);//逻辑删除，将表中是否删除改为1
//			brandInfo.setSupdateUser(updateUserName);
//			brandInfo.setTupdateTime(updateTime);
//			brandInfo.setSmerchantId(merchantId);
//			brandInfoDao.updateByIdSelective(brandInfo);
//
//			// 将对应商品的品牌的清空
//			commodityInfo = new CommodityInfo();
//			commodityInfo.setSbrandId(id);
//			commodityInfo.setSupdateUser(updateUserName);
//			commodityInfo.setTupdateTime(updateTime);
//			commodityInfo.setSmerchantId(merchantId);
//			commodityInfoDao.emptyBrand(commodityInfo);
//
//			// 将对应视觉商品的品牌清空
//			commoditySku = new CommoditySku();
//			commoditySku.setSbrandId(id);
//			commoditySku.setTupdateTime(updateTime);
//			commoditySku.setSupdateUser(updateUserName);
//			commoditySkuDao.emptyBrand(commoditySku);
//
//		}

		return responseVo;
	}

	@Override
	public List<BrandInfo> selectBrandByMerchantId(String smerchantId) {
		return brandInfoDao.selectBrandByMerchantId(smerchantId);
	}

	@Override
	public List<BrandInfo> selectAllValidBrand() {
		return brandInfoDao.selectAllValidBrand();
	}

	/**
	 * 修改品牌信息
	 * 同步到视觉与商品列表
	 *
	 * @param brandInfo
	 * @param brandLogo
	 */
	@Override
	public void updateAndSynchToCommodity(BrandInfo brandInfo, MultipartFile brandLogo) {
		if (null != brandLogo && brandLogo.getSize() > 0) {//图片上传
			String url = uploadHome(brandLogo, "brand");
			if (StringUtils.isNotBlank(url)) {
				brandInfo.setSlogo(url);//图片路径
			}
		}
		Date date = DateUtils.getCurrentDateTime();
		String user = SessionUserUtils.getSessionAttributeForUserDtl().getRealName();
		String sname = brandInfo.getSname();
		String id = brandInfo.getId();
		brandInfo.setTupdateTime(date);//修改时间
		brandInfo.setSupdateUser(user);//修改人
		// 1.修改对应品牌信息
		brandInfoDao.updateByIdSelective(brandInfo);

		// 2.修改对应视觉商品
		CommoditySku commoditySku = new CommoditySku();
		commoditySku.setSbrandName(sname);
		commoditySku.setSbrandId(id);
		commoditySku.setTupdateTime(date);
		commoditySku.setSupdateUser(user);
		commoditySkuDao.updateBrandInfo(commoditySku);

		// 3.修改对应商品名称
		CommodityInfo commodityInfo = new CommodityInfo();
		commodityInfo.setSbrandName(sname);
		commodityInfo.setSbrandId(id);
		commodityInfo.setTupdateTime(date);
		commodityInfo.setSupdateUser(user);
		commodityInfoDao.updateBrandInfo(commodityInfo);

	}


	/**
	 * 处理上传图片
	 *
	 * @param file
	 * @return
	 */
	private String uploadHome(MultipartFile file, String pathName) {
		if (file == null) {
			throw new ServiceException("没有找到上传文件");
		}

		String filName = file.getOriginalFilename();//文件原名
		String[] fileNameSplit = filName.split("\\.");
		String exp = fileNameSplit[fileNameSplit.length - 1];//获取后缀名====>jpg|png|bmp
		//图片类型限制
		if (!exp.toLowerCase().equals("jpg") && !exp.toLowerCase().equals("png") && !exp.toLowerCase().equals("bmp")) {
			throw new ServiceException("文件类型错误，上传失败");
		}
		try {
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append(pathName).append("/").append(DateUtils.dateParseShortString(new Date())).append("/");
			String serverPath = stringBuffer.toString();//------>pathName/2018-03-07/

			String tempName = DateUtils.getCurrentSTimeNumber() + "." + exp;//文件名=="时间"+"."+"原图片名后缀"
			// 返回URL地址
			if (FtpUtils.uploadToFtpServer(file.getInputStream(), serverPath, tempName, FtpParamUtil.getFtpUser())) {
				StringBuffer stringBuffer1 = new StringBuffer();
				stringBuffer1.append("/").append(serverPath).append(tempName);// "/" + serverPath + tempName
				return stringBuffer1.toString();// 路径为------> /commodityInfo/2018-03-07/20180307151504492.jpg
			}
		} catch (IOException e) {
			logger.error("上传文件出现IOException异常：{}",e);
		}
		return null;
	}

}