package com.cloud.cang.mgr.vr.service.impl;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.sys.domain.DataDicDomain;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.core.utils.FtpParamUtil;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.common.Constrants;
import com.cloud.cang.mgr.common.utils.ExcelCommUtil;
import com.cloud.cang.mgr.lm.domain.LabelBatchCommodityDomain;
import com.cloud.cang.mgr.lm.service.LabelBatchCommodityService;
import com.cloud.cang.mgr.sm.dao.DeviceStockDao;
import com.cloud.cang.mgr.sp.dao.BrandInfoDao;
import com.cloud.cang.mgr.sp.dao.CategoryDao;
import com.cloud.cang.mgr.sp.dao.CommodityInfoDao;
import com.cloud.cang.mgr.vr.dao.CommoditySkuDao;
import com.cloud.cang.mgr.vr.dao.MerchantSkuDao;
import com.cloud.cang.mgr.vr.domain.CommoditySkuDomain;
import com.cloud.cang.mgr.vr.service.CommoditySkuService;
import com.cloud.cang.mgr.vr.vo.CommoditySkuVo;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.lm.LabelBatch;
import com.cloud.cang.model.lm.LabelBatchCommodity;
import com.cloud.cang.model.sp.BrandInfo;
import com.cloud.cang.model.sp.Category;
import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.model.sys.ParameterGroupDetail;
import com.cloud.cang.model.vr.CommoditySku;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.FtpUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFPictureData;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CommoditySkuServiceImpl extends GenericServiceImpl<CommoditySku, String> implements
        CommoditySkuService {

    private static final Logger logger = LoggerFactory.getLogger(CommoditySkuServiceImpl.class);

    @Autowired
    CommoditySkuDao commoditySkuDao;

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    CommodityInfoDao commodityInfoDao;

    @Autowired
    MerchantSkuDao merchantSkuDao;

    @Autowired
    DeviceStockDao deviceStockDao;

    @Autowired
    BrandInfoDao brandInfoDao;

    @Autowired
    LabelBatchCommodityService labelBatchCommodityService;

    @Override
    public GenericDao<CommoditySku, String> getDao() {
        return commoditySkuDao;
    }


    /**
     * 分页查询商品SKU信息
     *
     * @param page           分页对象
     * @param commoditySkuVo 查询条件
     * @return
     */
    @Override
    public Page<CommoditySkuDomain> selectPageByDomainWhere(Page<CommoditySkuDomain> page, CommoditySkuVo commoditySkuVo) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        String sname = commoditySkuVo.getScommodityName();
        if (StringUtils.isNotBlank(sname)) {
            char[] chars = sname.toCharArray();
            commoditySkuVo.setScommodityName(StringUtils.join(chars, '%'));
        }
        return commoditySkuDao.selectPageByDomainWhere(commoditySkuVo);

    }

    /**
     * 根据ID批量删除（逻辑删除）
     *
     * @param checkboxId
     */
    @Override
    public void batchDelByIds(String[] checkboxId) {
        CommoditySku commoditySku = null;
        //判断该视觉商品是否有商户正常使用
        for (String id : checkboxId) {
            CommodityInfo commodityInfo = new CommodityInfo();
            commodityInfo.setSvrCommodityId(id);
            List<CommodityInfo> commodityInfoList = commodityInfoDao.selectByEntityWhere(commodityInfo);
            if (CollectionUtils.isNotEmpty(commodityInfoList)) {
                for (CommodityInfo commodityInfo1 : commodityInfoList) {
                    Integer commodityStatus = commodityInfo1.getIstatus();
                    if (commodityStatus != null && commodityStatus == 10) {
                        throw new ServiceException("选中视觉商品中存在商户正常使用的商品，无法删除");
                    }
                }
            }
        }

        // 校验商品状态通过，开始进行逻辑删除
        // 1.同时删除商户SKU库中对应商品记录
        // 2.同时删除商品表中对应商品
        for (String id : checkboxId) {
            commoditySku = new CommoditySku();
            commoditySku.setId(id);
            commoditySku.setIdelFlag(1);//逻辑删除，将表中是否删除改为1
            commoditySkuDao.updateByIdSelective(commoditySku);
            CommodityInfo commodityInfo = new CommodityInfo();
            commodityInfo.setSvrCommodityId(id);
            commodityInfo.setIdelFlag(1);
            commodityInfoDao.logicDeleteVrCommodityByVrId(id);//逻辑删除商品表中对应的视觉商品信息
            merchantSkuDao.deleteByVrCommodityId(id);            //	物理删除商户SKU库记录

            // 删除设备库存中对应的商品信息
            CommodityInfo commodityInfo1 = new CommodityInfo();
            commodityInfo1.setSvrCommodityId(id);
            List<CommodityInfo> commodityInfoList = commodityInfoDao.selectByEntityWhere(commodityInfo1);
            if (CollectionUtils.isNotEmpty(commodityInfoList)) {
                for (CommodityInfo commodityInfo2 : commodityInfoList) {
                    deviceStockDao.deleteByCommodityId(commodityInfo2.getId());
                }
            }
        }

    }

    @Override
    public Page<CommoditySkuDomain> selectPageByMerchantId(Page<CommoditySkuDomain> page, CommoditySkuVo commoditySkuVo) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        String sname = commoditySkuVo.getScommodityName();
        if (StringUtils.isNotBlank(sname)) {
            char[] chars = sname.toCharArray();
            commoditySkuVo.setScommodityName(StringUtils.join(chars, '%'));
        }
        return commoditySkuDao.selectPageByMerchantId(commoditySkuVo);
    }

    /**
     * 修改视觉商品信息
     * 如果修改状态中包含下架，则商品表中对应的状态也应该改变
     *
     * @param commoditySku
     * @param scommodityImgfile
     * @return
     */
    @Override
    @Transactional
    public ResponseVo<String> updateVrCommodity(CommoditySku commoditySku, MultipartFile scommodityImgfile) {
        String sbrandName = commoditySku.getSbrandName();//品牌--名称
        String sbrandId = commoditySku.getSbrandId();//品牌--id
        String bigCategory = commoditySku.getSbigCategoryName();//大类--id_名称
        String smallCategory = commoditySku.getSsmallCategoryName();//小类--id_名称
        if (StringUtils.isBlank(sbrandId) || StringUtils.isBlank(sbrandName)) {
            throw new ServiceException("品牌信息不能为空");
        }

        if (StringUtils.isNotBlank(bigCategory)) {
            commoditySku.setSbigCategoryId(bigCategory.split("_")[0]);//大类ID
            commoditySku.setSbigCategoryName(bigCategory.split("_")[1]);//大类名称

            //根据大类ID查询主类编号和主类名称
            Category category = categoryDao.selectByPrimaryKey(bigCategory.split("_")[0]);
            if (null != category) {
                String mainCategoryCode = category.getScategoryCode();  //主分类编号
                String mainCategoryName = category.getScategoryName();  //主分类名称
                if (StringUtils.isNotBlank(mainCategoryCode)) {
                    commoditySku.setScategoryCode(mainCategoryCode);
                }
                if (StringUtils.isNotBlank(mainCategoryName)) {
                    commoditySku.setScategoryName(mainCategoryName);
                }
            }
        } else {    // 大类为空时，主分类名称和编号为空
            commoditySku.setScategoryCode("");
            commoditySku.setScategoryName("");
        }
        if (StringUtils.isNotBlank(smallCategory)) {
            commoditySku.setSsmallCategoryId(smallCategory.split("_")[0]);//小类ID
            commoditySku.setSsmallCategoryName(smallCategory.split("_")[1]);//小类名称
        }
        if (null != scommodityImgfile && scommodityImgfile.getSize() > 0) {//图片上传
            String url = uploadHome(scommodityImgfile, "commoditySku");
            if (StringUtils.isNotBlank(url)) {
                commoditySku.setScommodityImg(url);//图片路径
            }
        }

        // 20180908修改商品重量与电子秤浮动值为非必填项，不填时默认为 0.00
        BigDecimal commodityFloat = commoditySku.getIcommodityFloat();     // 商品重量允许浮动值
        if (null == commodityFloat) {
            commoditySku.setIcommodityFloat(BigDecimal.ZERO);   // 不填时，默认为0.00
        }

        commoditySku.setTupdateTime(DateUtils.getCurrentDateTime());//修改时间
        commoditySku.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());//修改人
        commoditySkuDao.updateByIdSelectiveVo(commoditySku);
        //根据视觉编号修改标注平台视觉商品名称
        CommoditySku oldData = commoditySkuDao.selectByPrimaryKey(commoditySku.getId());
        LabelBatchCommodityDomain labelBatchCommodityDomain = new LabelBatchCommodityDomain();
        /*if (!oldData.getSvrCode().equals(commoditySku.getSvrCode()) || !oldData.getScommodityName().equals(commoditySku.getScommodityName())) {*/
        StringBuffer sb = new StringBuffer();
        if (StringUtils.isNotBlank(commoditySku.getSbrandName())) {
            sb.append(commoditySku.getSbrandName());
        }
        if (StringUtils.isNotBlank(commoditySku.getScommodityName())) {
            sb.append(commoditySku.getScommodityName());
        }
        if (StringUtils.isNotBlank(commoditySku.getStaste())) {
            sb.append(commoditySku.getStaste());
        }
        if (null != commoditySku.getIspecWeight()) {
            sb.append(commoditySku.getIspecWeight());
        }
        if (null != commoditySku.getSspecUnit()) {
            sb.append(commoditySku.getSspecUnit());
        }
        if (StringUtils.isNotBlank(commoditySku.getSpackageUnit())) {
            sb.append("*" + commoditySku.getSpackageUnit());
        }
        //同步视觉编号到LM_LABEL_BATCH_COMMODITY 表
        labelBatchCommodityDomain.setSvrCode(commoditySku.getSvrCode());
        //同步商品名称到LM_LABEL_BATCH_COMMODITY
        labelBatchCommodityDomain.setScommodityName(sb.toString());
        labelBatchCommodityDomain.setOldSvrCode(oldData.getSvrCode());
        labelBatchCommodityService.updateBySvrCode(labelBatchCommodityDomain);
    /*}*/
        // 返回ID（同步按钮需要用到ID），切勿返回编号
        return ResponseVo.getSuccessResponse(commoditySku.getId());
    }


    /**
     * 新增视觉商品信息
     *
     * @param commoditySku
     * @param scommodityImgfile
     * @return
     */
    @Override
    public ResponseVo<String> insertCommoditySku(CommoditySku commoditySku, MultipartFile scommodityImgfile) {
        String scode = CoreUtils.newCode(EntityTables.VR_COMMODITY_SKU);//商品sku编号
        String sbrandName = commoditySku.getSbrandName();//品牌--名称
        String sbrandId = commoditySku.getSbrandId();//品牌--id
        String bigCategory = commoditySku.getSbigCategoryName();//大类--id_名称
        String smallCategory = commoditySku.getSsmallCategoryName();//小类--id_名称
        if (StringUtils.isBlank(sbrandId) || StringUtils.isBlank(sbrandName)) {
            throw new ServiceException("品牌信息不能为空");
        }
        if (StringUtils.isNotBlank(scode)) {
            if (StringUtils.isNotBlank(bigCategory)) {
                commoditySku.setSbigCategoryId(bigCategory.split("_")[0]);//大类ID
                commoditySku.setSbigCategoryName(bigCategory.split("_")[1]);//大类名称

                //根据大类ID查询主类编号和主类名称
                Category category = categoryDao.selectByPrimaryKey(bigCategory.split("_")[0]);
                if (null != category) {
                    String mainCategoryCode = category.getScategoryCode();  //主分类编号
                    String mainCategoryName = category.getScategoryName();  //主分类名称
                    if (StringUtils.isNotBlank(mainCategoryCode)) {
                        commoditySku.setScategoryCode(mainCategoryCode);
                    }
                    if (StringUtils.isNotBlank(mainCategoryName)) {
                        commoditySku.setScategoryName(mainCategoryName);
                    }
                }
            }
            if (StringUtils.isNotBlank(smallCategory)) {
                commoditySku.setSsmallCategoryId(smallCategory.split("_")[0]);//小类ID
                commoditySku.setSsmallCategoryName(smallCategory.split("_")[1]);//小类名称
            }
            if (null != scommodityImgfile && scommodityImgfile.getSize() > 0) {
                //图片上传
                String url = uploadHome(scommodityImgfile, "commoditySku");
                if (StringUtils.isNotBlank(url)) {
                    commoditySku.setScommodityImg(url);
                }
            }
            BigDecimal commodityFloat = commoditySku.getIcommodityFloat();     // 商品重量允许浮动值
            if (null == commodityFloat) {
                commoditySku.setIcommodityFloat(BigDecimal.ZERO);   // 不填时，默认为0.00
            }
            commoditySku.setScode(scode);//商品编号
            commoditySku.setIstatus(20);/* 商品状态 10=在售 20=停用 */
            commoditySku.setIonlineStatus(10);/* 商品状态 10=草稿 20=已上架 30=已下架 */
            commoditySku.setIdelFlag(0);/* 是否删除0否1是 */
            commoditySku.setIverson(1);//版本号
            commoditySku.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());//添加人
            commoditySku.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());//修改人
            commoditySku.setTaddTime(DateUtils.getCurrentDateTime());/* 添加日期 */
            commoditySku.setTupdateTime(DateUtils.getCurrentDateTime());/* 修改日期 */
            commoditySkuDao.insert(commoditySku);
        }
        return ResponseVo.getSuccessResponse(commoditySku.getScode());
    }

    /**
     * 查询sku类型为全部的商户下的SKU商品
     *
     * @param page
     * @param commoditySkuVo
     * @return
     */
    @Override
    public Page<CommoditySkuDomain> selectPageByValidStatus(Page<CommoditySkuDomain> page, CommoditySkuVo commoditySkuVo) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        String sname = commoditySkuVo.getScommodityName();
        if (StringUtils.isNotBlank(sname)) {
            char[] chars = sname.toCharArray();
            commoditySkuVo.setScommodityName(StringUtils.join(chars, '%'));
        }
        return commoditySkuDao.selectPageByValidStatus(commoditySkuVo);
    }

    /**
     * 修改视觉商品状态
     *
     * @param checkboxId 视觉商品ID
     * @param status     最终修改的状态
     * @return
     */
    @Override
    public ResponseVo<String> updateCommodityStatus(String[] checkboxId, String status) {
        String id = checkboxId[0];
        if (StringUtils.isNotBlank(id)) {
            CommoditySku commoditySku = new CommoditySku();
            commoditySku.setId(id);

            // 10=在售 20=停用
            if (Constrants.SKU_COMMONDITY_DISABLE.equals(status)) {
                // 判断该商品是否有商户正常使用
                CommodityInfo commodityInfo = new CommodityInfo();
                commodityInfo.setSvrCommodityId(id);
                List<CommodityInfo> commodityInfoList = commodityInfoDao.selectByEntityWhere(commodityInfo);
                if (CollectionUtils.isNotEmpty(commodityInfoList)) {
                    for (CommodityInfo commodityInfo1 : commodityInfoList) {
                        Integer commodityIstatus = commodityInfo1.getIstatus();
                        if (commodityIstatus == 10) {
                            throw new ServiceException("该视觉商品有商户正常使用，无法停用");
                        }
                    }
                }


                // 修改商品表中对应视觉商品的状态
                commoditySku.setIstatus(20);
                changeCommodityStatus(id, 20, null);

            } else if (Constrants.SKU_COMMONDITY_ENABLE.equals(status)) {
                commoditySku.setIstatus(10);
                // 修改商品表中对应视觉商品的状态
                changeCommodityStatus(id, 10, null);

            }
            commoditySkuDao.updateByIdSelective(commoditySku);
            return ResponseVo.getSuccessResponse("ok");
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("修改状态出现错误！");
    }

    /**
     * 修改视觉商品上线状态
     * 10 草稿 20 已上架 30 已下架
     *
     * @param checkboxId
     * @param onlineStatus
     * @return
     */
    @Override
    public ResponseVo<String> updateVrOnlineStatus(String[] checkboxId, String onlineStatus) {
        String id = checkboxId[0];
        if (StringUtils.isNotBlank(id)) {
            CommoditySku commoditySku = new CommoditySku();
            commoditySku.setId(id);
            if (Constrants.SKU_COMMONDITY_SHELF.equals(onlineStatus)) { // 修改为上架
                commoditySku.setIonlineStatus(20);
                // 修改商品表中对应视觉商品的状态
                changeCommodityStatus(id, null, 20);

            } else if (Constrants.SKU_COMMONDITY_DROPOFF.equals(onlineStatus)) { // 修改为下架
                // 判断该视觉商品是否被商户添加到商品表中且未下架
                CommodityInfo commodityInfo = new CommodityInfo();
                commodityInfo.setSvrCommodityId(id);
                List<CommodityInfo> commodityInfoList = commodityInfoDao.selectByEntityWhere(commodityInfo);
                if (CollectionUtils.isNotEmpty(commodityInfoList)) {
                    for (CommodityInfo commodityInfo1 : commodityInfoList) {
                        Integer commodityInfo1Istatus = commodityInfo1.getIstatus();
                        if (commodityInfo1Istatus == 10) {    // 正常状态
                            throw new ServiceException("该视觉商品有商户正常使用，不能修改为下架");
                        }
                    }
                }


                commoditySku.setIonlineStatus(30);
                // 修改商品表中对应视觉商品的状态
                changeCommodityStatus(id, null, 30);
            }

            commoditySkuDao.updateByIdSelective(commoditySku);
            CommoditySku commoditySku1 = commoditySkuDao.selectByPrimaryKey(id);
            return ResponseVo.getSuccessResponse(commoditySku1.getScode());
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("修改视觉商品上线状态出现错误！");
    }

    /**
     * 同步视觉商品信息到已添加的商品表中
     *
     * @param checkboxId 视觉商品IDs
     * @param user       操作员
     * @return 视觉商品编号
     */
    @Override
    public ResponseVo<String> synchVrToCommodityInfo(String[] checkboxId, String user) {
        StringBuffer stringBuffer = new StringBuffer();
        for (String id : checkboxId) {
            if (StringUtils.isNotBlank(id)) {
                CommoditySku commoditySku = commoditySkuDao.selectByPrimaryKey(id);
                if (null != commoditySku) {
                    CommodityInfo commodityInfo = new CommodityInfo();
                    commodityInfo.setScommodityImg(commoditySku.getScommodityImg());                            // 商品图片
                    commodityInfo.setSname(commoditySku.getScommodityName());                                    // 商品名称
                    commodityInfo.setSbigCategoryId(commoditySku.getSbigCategoryId());                            // 商品大类ID
                    commodityInfo.setSbigCategoryName(commoditySku.getSbigCategoryName());                        // 商品大类名称
                    commodityInfo.setSbrandId(commoditySku.getSbrandId());                                        // 商品品牌ID
                    commodityInfo.setSbrandName(commoditySku.getSbrandName());                                // 商品品牌名称
                    commodityInfo.setSsmallCategoryId(commoditySku.getSsmallCategoryId());                    // 商品小类ID
                    commodityInfo.setSsmallCategoryName(commoditySku.getSsmallCategoryName());                // 商品小类名称
                    commodityInfo.setSvrCommodityCode(commoditySku.getScode());                                // 视觉商品编号
                    commodityInfo.setSvrCode(commoditySku.getSvrCode());                                    // 视觉识别码
                    commodityInfo.setSupdateUser(user);                                                        // 修改人名称
                    commodityInfo.setTupdateTime(DateUtils.getCurrentDateTime());                            // 修改时间
                    commodityInfo.setIlifeType(commoditySku.getIlifeType());                                // 保质期类型
                    commodityInfo.setIshelfLife(commoditySku.getIshelfLife());                                // 保质期
                    commodityInfo.setSspecUnit(commoditySku.getSspecUnit());                                // 规格单位
                    commodityInfo.setIspecWeight(commoditySku.getIspecWeight());                            // 规格/重量
                    commodityInfo.setSmanufacturer(commoditySku.getSmanufacturer());                        // 生产厂家
                    commodityInfo.setSpackageUnit(commoditySku.getSpackageUnit());                            // 最小销售包装单位
                    commodityInfo.setStaste(commoditySku.getStaste());                                        // 口味
                    commodityInfo.setSpackagingMaterial(commoditySku.getSpackagingMaterial());                // 包装材质
                    commodityInfo.setSorigin(commoditySku.getSorigin());                                    // 产地
                    commodityInfo.setSproductBarcode(commoditySku.getSproductBarcode());                    // 商品条形码
                    commodityInfo.setIidentificationType(commoditySku.getIidentificationType());            // 标识类型
                    commodityInfo.setSvrCommodityId(commoditySku.getId());
                    commodityInfo.setIcommodityFloat(commoditySku.getIcommodityFloat());        // 商品重量允许浮动值 正负值
                    commodityInfo.setIweigth(commoditySku.getIweigth());                        // 商品重量（G）
                    commodityInfoDao.updateByVrIdSelectiveVo(commodityInfo);
                    stringBuffer.append(commoditySku.getScode() + ",");

                }
            }
        }
        String scodes = stringBuffer.toString();
        return ResponseVo.getSuccessResponse(scodes.substring(0, scodes.length() - 1));
    }

    /**
     * 批量导入视觉商品信息
     *
     * @param file Excel文件（支持 2003，2007）
     * @return
     * @throws Exception
     */
    @Override
    public ResponseVo<String> batchUploadVrComInfo(MultipartFile file) throws Exception {
        Workbook workbook = ExcelCommUtil.getWorkbook(file);
        StringBuffer errorStrBuf = new StringBuffer();        // 记录错误表格信息
        StringBuffer correctStrBuf = new StringBuffer();        // 记录正确表格信息
        List<String[]> vrCommodityList = new ArrayList<>();    // Excel表格信息
        List<HSSFPictureData> pictureDataList03 = new ArrayList<>();    // 03表格图片
        List<XSSFPictureData> pictureDataList07 = new ArrayList<>();    // 07表格图片
        HSSFPictureData hssfPictureData = null;    // 03图片data流
        XSSFPictureData xssfPictureData = null;    // 07图片data流
        Set<ParameterGroupDetail> groupDetailSet = new HashSet<>(); // 商品标识类型

        if (null == workbook) {
            logger.error("工作薄不存在");
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("工作薄不存在");
        }

		/* 读取Excel文件中的内容 */
        if (workbook instanceof HSSFWorkbook) {
            int allSheetNum = workbook.getNumberOfSheets();
            if (allSheetNum > 0) {
                pictureDataList03 = ExcelCommUtil.getsheetPictures03(0, (HSSFWorkbook) workbook);
                vrCommodityList = ExcelCommUtil.readExcel(0, workbook, 1);
                logger.debug("读取03版本.xls完成");
            }
        } else if (workbook instanceof XSSFWorkbook) {
            int allSheetNum = workbook.getNumberOfSheets();
            if (allSheetNum > 0) {
                pictureDataList07 = ExcelCommUtil.getsheetPictures07(0, (XSSFWorkbook) workbook);
                vrCommodityList = ExcelCommUtil.readExcel(0, workbook, 1);
                logger.debug("读取07版本.xlsx完成");
            }
        }
        if (CollectionUtils.isEmpty(vrCommodityList)) {
            logger.error("当前：{}sheet文件没有内容", workbook.getSheetName(0));
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("上传文件中数据为空，请确认是否有数据");
        }


		/* 查询所有品牌 */
        List<BrandInfo> brandInfoList = brandInfoDao.selectAllValidBrand();

        /* 查询所有小类 */
        List<Category> smallCategoryList = categoryDao.selectAllValidSmallCategory();

        /* 查询所有大类 */
        List<Category> bigCategoryList = categoryDao.selectAllValidBigCategory();

        /* 查询所有标志类型 */
        DataDicDomain dataDicDomain = GrpParaUtil.getMain(GrpParaUtil.COMMODITY_MARK_TYPE);
        if (null != dataDicDomain) {
            groupDetailSet = dataDicDomain.getDataDicDetails();
        }

        for (int i = 0; i < vrCommodityList.size(); i++) {
            int j = i + 2; // 第j行数据
            String brandId = "";                        // 品牌ID
            String smallCategoryId = "";                // 小类ID
            String smallCategoryName = "";                // 小类名称
            String bigCategoryId = "";                  // 大类ID
            String bigCategoryName = "";                // 大类名称
            String mainCategoryCode = "";               // 主分类编号
            String mainCategoryName = "";               // 主分类名称
            Integer markTypeVal = null;                 // 标识类型值
            String packageUnit = "";                    // 最小销售包装单位
            String specUnit = "";                        // 规格单位
            String specWeight = "";                        // 规格重量
            Integer lifeType = null;                    // 保质期类型
            Integer shelfLife = null;                    // 保质期时间
            BigDecimal iweight = null;                    // 商品重量
            BigDecimal icommodityFloat = new BigDecimal(0.00);          // 商品重量允许浮动值
            CommoditySku commoditySku = new CommoditySku();
            // 从Excel中获取第i+1行的数据
            String[] row = vrCommodityList.get(i);
            String[] strings = new String[20];
            if (ArrayUtils.isNotEmpty(row)) {
                for (int k = 0; k < row.length; k++) {
                    strings[k] = row[k];
                }
            }
            String serialNum = strings[0];                    // 序号（目前不用）
            String picUrl = strings[1];                        // 图片（目前不用）
            String brandName = strings[2];                    // 品牌（必填）
            String vrComName = strings[3];                    // 商品名称（必填）
            String taste = strings[4];                        // 口味
            String specificationUnit = strings[5];            // 规格单位（必填）
            String packagingMaterial = strings[6];                // 包装材质
            String shelfLifeTemp = strings[7];                    // 保质期（必填）
            String placeOfProduction = strings[8];                // 产地
            String actualWeight = strings[9];                // 实际重量（必填）
            String commodityFloatVal = strings[10];            // 商品重量浮动值
            String smallCategoryCode = strings[11];            // 小类编号（必填）
            String systemVrNum = strings[12];                    // 系统视觉编号（必填）
            String barCode = strings[13];                    // 商品条形码
            String markType = strings[14];                    // 标志类型
            String manufacturer = strings[15];                // 生产厂家

            // 1.1 必填项非空校验
            if (StringUtils.isBlank(brandName)) {
                logger.error("批量上传视觉商品出现品牌名为空,第：{}行，序号：{} 出错", j, serialNum);
                errorStrBuf.append("第" + j + "行,序号为:" + serialNum + ",品牌名称为空;");
                continue;
            }
            if (StringUtils.isBlank(specificationUnit)) {
                logger.error("批量上传视觉商品出现规格单位为空,第：{}行，序号：{} 出错", j, serialNum);
                errorStrBuf.append("第" + j + "行,序号:" + serialNum + ",规格单位为空;");
                continue;
            }
            if (StringUtils.isBlank(actualWeight)) {
                logger.error("批量上传视觉商品出现实际重量为空,第：{}行，序号：{} 出错", j, serialNum);
                errorStrBuf.append("第" + j + "行,序号:" + serialNum + ",实际重量为空;");
                continue;
            }
            if (StringUtils.isBlank(systemVrNum)) {
                logger.error("批量上传视觉商品出现系统视觉编号为空,第：{}行，序号：{} 出错", j, serialNum);
                errorStrBuf.append("第" + j + "行,序号:" + serialNum + ",系统视觉编号为空;");
                continue;
            }
            if (StringUtils.isBlank(shelfLifeTemp)) {
                logger.error("批量上传视觉商品出现保质期为空,第：{}行，序号：{} 出错", j, serialNum);
                errorStrBuf.append("第" + j + "行,序号:" + serialNum + ",保质期为空;");
                continue;
            }
//            if (StringUtils.isBlank(smallCategoryCode)) {
//                logger.error("批量上传视觉商品出现小类编号为空,第：{}行，序号：{} 出错", j, serialNum);
//                errorStrBuf.append("第" + j + "行,序号:" + serialNum + ",小类编号为空;");
//                continue;
//            }
            if (StringUtils.isBlank(vrComName)) {
                logger.error("批量上传视觉商品出现视觉商品名为空,第：{}行，序号：{} 出错", j, serialNum);
                errorStrBuf.append("第" + j + "行,序号:" + serialNum + ",视觉商品名为空;");
                continue;
            }
            // 1.2 匹配品牌类型
            if (CollectionUtils.isNotEmpty(brandInfoList)) {
                for (BrandInfo b : brandInfoList) {
                    if (brandName.trim().equals(b.getSname())) {
                        brandId = b.getId();
                    }
                }
            }
            if (StringUtils.isBlank(brandId)) {
                logger.error("批量上传视觉商品未匹配到对应的品牌信息,第：{}行，序号：{} 出错", j, serialNum);
                errorStrBuf.append("第" + j + "行,序号:" + serialNum + ",未匹配到对应的品牌信息;");
                continue;
            }
            // 1.3 匹配分类（大类，小类，主分类）
            if (StringUtils.isNotBlank(smallCategoryCode) && CollectionUtils.isNotEmpty(smallCategoryList)) {
                categoryLoop:
                for (Category small : smallCategoryList) {
                    if (smallCategoryCode.equals(small.getScode())) {
                        smallCategoryId = small.getId();        // 小类ID
                        smallCategoryName = small.getSname();    // 小类名称
                        bigCategoryId = small.getSparentId();   // 大类ID
                        if (CollectionUtils.isNotEmpty(bigCategoryList) && StringUtils.isNotBlank(bigCategoryId)) {
                            for (Category big : bigCategoryList) {
                                if (bigCategoryId.equals(big.getId())) {
                                    bigCategoryName = big.getSname();           // 大类名称
                                    mainCategoryCode = big.getScategoryCode();  // 主分类编号
                                    mainCategoryName = big.getScategoryName();  // 主分类名称
                                    break categoryLoop;    // 跳出两层for循环
                                }
                            }
                        }
                    }
                }
            }


            // 20180910 小类信息改为非必填
//			if (StringUtils.isBlank(smallCategoryId) || StringUtils.isBlank(smallCategoryName) || StringUtils.isBlank(bigCategoryId) || StringUtils.isBlank(bigCategoryName) || StringUtils.isBlank(mainCategoryCode) || StringUtils.isBlank(mainCategoryName)) {
//				logger.error("批量上传视觉商品未匹配到对应的小类信息,第：{}行，从Excel表格中获取小类编号：{} 出错", j, smallCategoryCode);
//				errorStrBuf.append("第" + j + "行,序号:" + serialNum + ",未匹配到对应小类信息;");
//				continue;
//			}


            // 1.4 匹配对应标识类型
            if (CollectionUtils.isNotEmpty(groupDetailSet)) {
                markLoop:
                for (ParameterGroupDetail p : groupDetailSet) {
                    if (p.getSname().equals(markType)) {
                        markTypeVal = new Integer(p.getSvalue());
                        break markLoop;                // 跳出本次循环
                    }
                }
            }
            // 1.5.0 截取规格单位
            String[] specWeightUnitPackage = getSpec(specificationUnit);    // 获取规格重量，规格单位，最小销售包装单位
            if (StringUtils.isBlank(specWeightUnitPackage[0]) || StringUtils.isBlank(specWeightUnitPackage[1]) || StringUtils.isBlank(specWeightUnitPackage[2])) {
                logger.error("批量上传视觉商品出现规格单位格式不正确,第：{}行，从Excel表格中获取规格单位：{}", j, specificationUnit);
                errorStrBuf.append("第" + j + "行,序号:" + serialNum + ",规格单位格式不正确;");
                continue;
            }
            specWeight = specWeightUnitPackage[0];        // 规格重量
            specUnit = specWeightUnitPackage[1];        // 规格单位
            packageUnit = specWeightUnitPackage[2];        // 最小销售包装单位
            // 1.5.1 截取保质期
            String[] shelfLifeArray = getShelfLife(shelfLifeTemp);
            if (StringUtils.isBlank(shelfLifeArray[0]) || StringUtils.isBlank(shelfLifeArray[1])) {
                logger.error("批量上传视觉商品出现保质期格式不正确,第：{}行，从Excel表格中获取保质期内容：{}", j, shelfLifeTemp);
                errorStrBuf.append("第" + j + "行,序号:" + serialNum + ",保质期格式不正确;");
                continue;
            }
            shelfLife = new Integer(shelfLifeArray[0]);    // 保质期
            lifeType = new Integer(shelfLifeArray[1]);    // 保质期类型
            // 1.5.2 重量及浮动值保留两位小数
            try {
                iweight = new BigDecimal(actualWeight).setScale(2, BigDecimal.ROUND_HALF_UP);
                if (StringUtils.isNotBlank(commodityFloatVal)) {
                    icommodityFloat = new BigDecimal(commodityFloatVal).setScale(2, BigDecimal.ROUND_HALF_UP);
                }
            } catch (Exception e) {
                logger.error("批量上传视觉商品出现重量格式转化错误,第：{}行，从Excel表格中获取重量为：{}", j, actualWeight);
                errorStrBuf.append("第" + j + "行,序号:" + serialNum + ",重量格式转化错误;");
                continue;
            }

            // 1.6 判断系统视觉编号是否已经存在
            CommoditySku commoditySkuVo = new CommoditySku();
            commoditySkuVo.setSvrCode(systemVrNum);
            commoditySkuVo.setIdelFlag(0);
            List<CommoditySku> commoditySkuList = commoditySkuDao.selectByEntityWhere(commoditySkuVo);
            if (CollectionUtils.isNotEmpty(commoditySkuList)) {
                logger.error("批量上传视觉商品出现系统视觉识别编号已经存在,第：{}行，从Excel表格中获取视觉编号：{}", j, systemVrNum);
                errorStrBuf.append("第" + j + "行,序号:" + serialNum + ",系统视觉识别编号:" + systemVrNum + "已经存在;");
                continue;
            }

            // 1.7 如果有图片上传图片，返回图片地址
            if (CollectionUtils.isNotEmpty(pictureDataList03)) {
                try {
                    hssfPictureData = pictureDataList03.get(i);
                } catch (Exception e) {
                    logger.error("从03格式的Excel中获取图片出现异常，异常原因：{}", e);
                    throw new ServiceException("商品的商品图片需要全上传或全不上传");
                }
                if (null != hssfPictureData) {
                    String tempUrl = uploadExcelPic03(hssfPictureData, "commoditySku");
                    if (StringUtils.isNotBlank(tempUrl)) {
                        picUrl = tempUrl;
                    }
                }
            } else if (CollectionUtils.isNotEmpty(pictureDataList07)) {
                try {
                    xssfPictureData = pictureDataList07.get(i);
                } catch (Exception e) {
                    logger.error("从07格式的Excel中获取图片出现异常，异常原因：{}", e);
                    throw new ServiceException("商品的商品图片需要全上传或全不上传");
                }
                if (null != xssfPictureData) {
                    String tempUrl = uploadExcelPic07(xssfPictureData, "commoditySku");
                    if (StringUtils.isNotBlank(tempUrl)) {
                        picUrl = tempUrl;
                    }
                }
            }

            // 1.8 开始插入数据
            String scode = CoreUtils.newCode(EntityTables.VR_COMMODITY_SKU); // 商品sku编号
            if (StringUtils.isBlank(scode)) {
                logger.error("批量上传视觉商品没有获取到商品编号,第：{}行，序号：{}", j, serialNum);
                errorStrBuf.append("第" + j + "行,序号:" + serialNum + ",系统生成商品编号错误;");
                continue;
            }

            commoditySku.setScode(scode);                                // 2商品编号
            commoditySku.setSvrCode(systemVrNum);                        // 3系统视觉识别编号
            commoditySku.setScommodityName(vrComName);                    // 4商品名称
            commoditySku.setScommodityImg(picUrl);                        // 5图片地址
            commoditySku.setScategoryCode(mainCategoryCode);            // 6主分类编号
            commoditySku.setScategoryName(mainCategoryName);            // 7主分类名称
            commoditySku.setSbigCategoryId(bigCategoryId);                // 8大类ID
            commoditySku.setSbigCategoryName(bigCategoryName);            // 9大类名称
            commoditySku.setSsmallCategoryId(smallCategoryId);            // 10小类ID
            commoditySku.setSsmallCategoryName(smallCategoryName);        // 11小类名称
            commoditySku.setSpackageUnit(packageUnit);                    // 12最小销售包装单位
            commoditySku.setSspecUnit(specUnit);                        // 13规格单位
            commoditySku.setIspecWeight(specWeight);                    // 14规格/重量
            commoditySku.setSbrandId(brandId);                            // 15品牌ID
            commoditySku.setSbrandName(brandName);                        // 16品牌名称
            commoditySku.setIlifeType(lifeType);                        // 17保质期类型 10=天 20=月
            commoditySku.setIshelfLife(shelfLife);                        // 18保质期时间
            commoditySku.setSmanufacturer(manufacturer);                // 19生产厂家
            commoditySku.setStaste(taste);                                // 20口味
            commoditySku.setSpackagingMaterial(packagingMaterial);      // 21包装材质
            commoditySku.setSorigin(placeOfProduction);                    // 22产地
            commoditySku.setSproductBarcode(barCode);                    // 23商品条形码
            commoditySku.setIidentificationType(markTypeVal);            // 24标识类型 数据字典配置 10=特产 20=进口
            commoditySku.setIweigth(iweight);                            // 25商品实际重量
            commoditySku.setIcommodityFloat(icommodityFloat);            // 26商品重量允许浮动值
            commoditySku.setIstatus(20);                                // 28商品状态 10=在售 20=停用
            commoditySku.setIonlineStatus(10);                            // 29上线状态 10=草稿 20=已上架 30=已下架
            commoditySku.setIdelFlag(0);                                // 30是否删除0否1是
            commoditySku.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());//添加人
            commoditySku.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());//修改人
            commoditySku.setTaddTime(DateUtils.getCurrentDateTime());/* 添加日期 */
            commoditySku.setTupdateTime(DateUtils.getCurrentDateTime());/* 修改日期 */
            commoditySku.setIverson(1);                                    // 版本号
            commoditySkuDao.insert(commoditySku);
            correctStrBuf.append(scode + ",");
        }
        String correctStr = correctStrBuf.toString();
        String errorStr = errorStrBuf.toString();
        if (StringUtils.isNotBlank(errorStr)) {
            if (StringUtils.isNotBlank(correctStr)) {
                return new ResponseVo<>(false, ErrorCodeEnum.ERROR_COMMON_SYSTEM.getCode(), "部分数据导入成功，下列数据出现错误：" + errorStr);
            }
            return new ResponseVo<>(false, ErrorCodeEnum.ERROR_COMMON_SYSTEM.getCode(), "全部导入失败，下列数据出现错误：" + errorStr);
        }

        return ResponseVo.getSuccessResponse(correctStr.substring(0, correctStr.length() - 1));
    }

    /**
     * 从类似“10/月”，“30/天”中分别保质期，保质期类型
     *
     * @param shelfLifeTemp Excel表格中获取字符串
     * @return 数组[] 0:保质期，1:保质期类型
     */
    private String[] getShelfLife(String shelfLifeTemp) {
        String[] strings = new String[2];
        String lifeType = "";
        String shelfLife = "";
        if (shelfLifeTemp.contains("/")) {
            shelfLife = shelfLifeTemp.substring(0, shelfLifeTemp.indexOf("/"));
        }
        if (shelfLifeTemp.contains("天")) {
            lifeType = "10";
        } else if (shelfLifeTemp.contains("月")) {
            lifeType = "20";
        }
        strings[0] = shelfLife;    // 保质期
        strings[1] = lifeType;    // 保质期类型
        return strings;
    }

    /**
     * 从类似“200ml/瓶”，“300g/袋”，“30片/盒”，“300抽/盒”中分别获取重量，单位，最小销售包装单位
     *
     * @param specificationUnit Excel表格中获取字符串
     * @return 数组[] 0：重量或容积，1：单位，2：最小销售包装单位
     */
    private String[] getSpec(String specificationUnit) {
        String[] strings = new String[3];
        String specUnit = "";
        String specWeight = "";
        String spackageUnit = "";
        String specUnitWeight = "";
        if (specificationUnit.contains("/")) {
            spackageUnit = specificationUnit.substring(specificationUnit.indexOf("/") + 1);
            specUnitWeight = specificationUnit.substring(0, specificationUnit.indexOf("/"));
        }
        //判断是否包含中文单位
        String chineseChar = "[\u4e00-\u9fa5]+";
        Pattern chinesePattern = Pattern.compile(chineseChar);
        Matcher chineseMatcher = chinesePattern.matcher(specUnitWeight);

        if (chineseMatcher.find()) {    // 包含中文
            specUnit = chineseMatcher.group();
            if (StringUtils.isNotBlank(specUnit)) {
                specWeight = specUnitWeight.substring(0, specUnitWeight.indexOf(specUnit));
            }
            String tempSpecUnit = "";
            if (specUnit.equals("毫克")) {
                tempSpecUnit = "mg";
            } else if (specUnit.equals("克")) {
                tempSpecUnit = "g";
            } else if (specUnit.equals("千克")) {
                tempSpecUnit = "kg";
            } else if (specUnit.equals("毫升")) {
                tempSpecUnit = "ml";
            } else if (specUnit.equals("升")) {
                tempSpecUnit = "L";
            } else if (specUnit.equals("米")) {
                tempSpecUnit = "m";
            } else {
                tempSpecUnit = specUnit;
            }
            specUnit = tempSpecUnit;
        } else {    // 不包含中文
            String lowerCaseSpecUnitWeight = specUnitWeight.toLowerCase();

            // 重量
            if (lowerCaseSpecUnitWeight.contains("kg")) {
                specUnit = "kg";
                specWeight = lowerCaseSpecUnitWeight.substring(0, lowerCaseSpecUnitWeight.indexOf("kg"));
            } else if (lowerCaseSpecUnitWeight.contains("mg")) {
                specWeight = lowerCaseSpecUnitWeight.substring(0, lowerCaseSpecUnitWeight.indexOf("mg"));
                specUnit = "mg";
            } else if (lowerCaseSpecUnitWeight.contains("g")) {
                specWeight = lowerCaseSpecUnitWeight.substring(0, lowerCaseSpecUnitWeight.indexOf("g"));
                specUnit = "g";
            }

            // 容积
            if (lowerCaseSpecUnitWeight.contains("ml")) {
                specUnit = "ml";
                specWeight = lowerCaseSpecUnitWeight.substring(0, lowerCaseSpecUnitWeight.indexOf("ml"));
            } else if (lowerCaseSpecUnitWeight.contains("l")) {
                specUnit = "L";
                specWeight = lowerCaseSpecUnitWeight.substring(0, lowerCaseSpecUnitWeight.indexOf("l"));
            }
        }

        strings[0] = specWeight;            // 重量或容积
        strings[1] = specUnit;                // 单位
        strings[2] = spackageUnit;            // 最小销售包装单位
        return strings;
    }

    /**
     * 视觉商品状态修改为停用或者已下架时，修改所有商户下的商品状态为失效
     * 视觉商品状态修改为在售并且已上架时，修改所有商户下的商品状态为正常
     *
     * @param vrId         视觉商品ID
     * @param istatus      视觉商品修改后的启用状态 10在售 20停用
     * @param onlineStatus 视觉商品修改后的上线状态 10草稿 20已上架 30已下架
     */
    private void changeCommodityStatus(String vrId, Integer istatus, Integer onlineStatus) {
        CommoditySku commoditySku = commoditySkuDao.selectByPrimaryKey(vrId);
        if (null == commoditySku) {
            return;
        }
        // 修改前的视觉商品状态
        Integer vrIstatus = commoditySku.getIstatus();
        Integer vrOnlineIstatus = commoditySku.getIonlineStatus();

        CommodityInfo commodityInfo = new CommodityInfo();
        commodityInfo.setSvrCommodityId(vrId);
        List<CommodityInfo> commodityInfoList = commodityInfoDao.selectByEntityWhere(commodityInfo);
        // 如果该商品被加入到商品表中则进行修改，否则不做处理
        if (CollectionUtils.isEmpty(commodityInfoList)) {
            return;
        }

        // 修改为   下架（只有在商品状态为在售的状态才做更改）
        if (Integer.valueOf(20).equals(istatus) || Integer.valueOf(30).equals(onlineStatus) || Integer.valueOf(10).equals(onlineStatus)) {
            for (CommodityInfo commodity : commodityInfoList) {
                if (Integer.valueOf(10).equals(commodity.getIstatus())) {
                    commodityInfoDao.updateStatusToInvalid(vrId);
                    return;
                }
            }

            // 修改为   上架 (修改前 状态为在售，现在操作为上架)
        }
//		else if ((Integer.valueOf(10).equals(vrIstatus) && Integer.valueOf(20).equals(onlineStatus))) {
//			commodityInfoDao.updateStatusTovalid(vrId);
//
//			// 修改为   上架 (修改前 上线状态为上架，现在操作为启用)
//		} else if ((Integer.valueOf(10).equals(istatus) && Integer.valueOf(20).equals(vrOnlineIstatus))) {
//			commodityInfoDao.updateStatusTovalid(vrId);
//		}

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
            throw new ServiceException("没有找到文件，上传失败");
        }
        return null;
    }

    /**
     * excel03图片上传
     *
     * @param hssfPictureData 03图片data流
     * @param pathName        文件路径名
     * @return
     */
    private String uploadExcelPic03(HSSFPictureData hssfPictureData, String pathName) {
        if (null == hssfPictureData || ArrayUtils.isEmpty(hssfPictureData.getData())) {
            return "";
        }
        InputStream inputStream = null;
        try {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(pathName).append("/").append(DateUtils.dateParseShortString(new Date())).append("/");
            String serverPath = stringBuffer.toString();//------>pathName/2018-03-07/
            String tempName = DateUtils.getCurrentSTimeNumber() + ".jpg";//文件名=="时间"+".jpg"
            inputStream = new ByteArrayInputStream(hssfPictureData.getData());
            // 返回URL地址
            if (FtpUtils.uploadToFtpServer(inputStream, serverPath, tempName, FtpParamUtil.getFtpUser())) {
                StringBuffer stringBuffer1 = new StringBuffer();
                stringBuffer1.append("/").append(serverPath).append(tempName);// "/" + serverPath + tempName
                return stringBuffer1.toString();// 路径为------> /commodityInfo/2018-03-07/20180307151504492.jpg
            }
        } catch (Exception e) {
            logger.error("上传文件失败，错误原因：{}", e);
        }
        return "";
    }

    /**
     * excel07图片上传
     *
     * @param xssfPictureData 07图片data流
     * @param pathName        文件路径名
     * @return
     */
    private String uploadExcelPic07(XSSFPictureData xssfPictureData, String pathName) {
        if (null == xssfPictureData || ArrayUtils.isEmpty(xssfPictureData.getData())) {
            return "";
        }
        InputStream inputStream = null;
        try {
            String serverPath = pathName + "/" + DateUtils.dateParseShortString(new Date()) + "/";    //------>pathName/2018-03-07/
            String tempName = DateUtils.getCurrentSTimeNumber() + ".jpg";//文件名=="时间"+".jpg"
            inputStream = new ByteArrayInputStream(xssfPictureData.getData());
            // 最后返回URL地址
            if (FtpUtils.uploadToFtpServer(inputStream, serverPath, tempName, FtpParamUtil.getFtpUser())) {
                StringBuffer stringBuffer1 = new StringBuffer();
                stringBuffer1.append("/").append(serverPath).append(tempName);// "/" + serverPath + tempName
                return stringBuffer1.toString();// 路径为------> /commodityInfo/2018-08-07/20180307151504492.jpg
            }
        } catch (Exception e) {
            logger.error("上传文件失败，错误原因：{}", e);
        }
        return "";
    }
}