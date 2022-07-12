package com.cloud.cang.mgr.common.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.generic.GenericController;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.vo.SelectVo;
import com.cloud.cang.mgr.sb.domain.DeviceInfoDomain;
import com.cloud.cang.mgr.sb.domain.SelectDeviceDomain;
import com.cloud.cang.mgr.sb.service.DeviceInfoService;
import com.cloud.cang.mgr.sb.service.GroupManageService;
import com.cloud.cang.mgr.sb.vo.DeviceInfoVo;
import com.cloud.cang.mgr.sh.service.MerchantInfoService;
import com.cloud.cang.mgr.sp.domain.CommodityDomain;
import com.cloud.cang.mgr.sp.domain.SelectCommodityDomain;
import com.cloud.cang.mgr.sp.service.BrandInfoService;
import com.cloud.cang.mgr.sp.service.CategoryService;
import com.cloud.cang.mgr.sp.service.CommodityInfoService;
import com.cloud.cang.mgr.sp.vo.CommodityVo;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sb.GroupManage;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhouhong
 * @Description: 通用选择控制层
 * @Date: 2018/2/9 16:18
 */
@Controller
@RequestMapping("/common")
public class CommonController extends GenericController {

    private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private DeviceInfoService deviceInfoService;
    @Autowired
    private BrandInfoService brandInfoService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private OperatorService operatorService;
    @Autowired
    private CommodityInfoService commodityInfoService;
    @Autowired
    private GroupManageService groupManageService;
    @Autowired
    private MerchantInfoService merchantInfoService;

    /**
     * @Author: zhouhong
     * @Description: 选择设备
     * @param: deviceIds 已选择设备ID
     * @param: allIstatus 是否选择所有状态
     * @Date: 2018/2/9 16:08
     */
    @RequestMapping("/selectDevice")
    public String selectDevice(String deviceIds, String deviceCodes, ModelMap map, String allIstatus) {
        logger.debug("选择设备页面:{}", deviceIds);
        if (StringUtil.isNotBlank(deviceIds)) {
            map.put("deviceIds", deviceIds + ",");
            map.put("deviceCodes", deviceCodes + ",");
            map.put("selectNums", deviceIds.split(",").length);//选择个数
        }
        map.put("allIstatus", allIstatus);
        return "sb/deviceInfo/deviceInfo-select-list";
    }

    /**
     * @param deviceIds       设备ID
     * @param deviceCodes     设备编号
     * @param queryDeviceType 查询设备类型 30=所有视觉柜子，40=带大屏的视觉柜子，50=云端识别设备（不带大屏）
     * @param map
     * @return
     */
    @RequestMapping("/rootSelectDevice")
    public String rootSelectDevice(String deviceIds, String deviceCodes, String queryDeviceType, ModelMap map) {
        logger.debug("选择设备页面:{}", deviceIds);
        if (StringUtil.isNotBlank(deviceIds)) {
            map.put("deviceIds", deviceIds + ",");
            map.put("deviceCodes", deviceCodes + ",");
            map.put("selectNums", deviceIds.split(",").length);//选择个数
        }
        map.put("queryDeviceType", queryDeviceType);

        return "sb/deviceInfo/deviceInfo-rootSelect-list";
    }

    /**
     * @Author: zhouhong
     * @Description: 选择商品分类
     * @param: deviceIds 已选择分类ID
     * @Date: 2018/2/9 16:08
     */
    @RequestMapping("/selectCommodityCategory")
    public String selectCommodityCategory(String categoryIds, ModelMap map) {
        logger.debug("选择商品分类页面:{}", categoryIds);
        //查询所有分类
        //获取商品分类
        List<SelectVo> categoryList = categoryService.selectByMerchantId(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
        map.put("categoryList", categoryList);
        if (StringUtil.isNotBlank(categoryIds)) {
            map.put("categoryIds", categoryIds);
        }
        return "sp/category/category-select-list";
    }

    /**
     * @Author: zhouhong
     * @Description: 选择商品分类
     * @param: deviceIds 已选择分类ID
     * @Date: 2018/2/9 16:08
     */
    @RequestMapping("/selectCommodityBrand")
    public String selectCommodityBrand(String brandIds, ModelMap map) {
        logger.debug("选择商品分类页面:{}", brandIds);
        //查询所有分类
        //获取商品品牌
        List<SelectVo> brandList = brandInfoService.selectByMerchantId(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
        map.put("brandList", brandList);
        if (StringUtil.isNotBlank(brandIds)) {
            map.put("brandIds", brandIds);
        }
        return "sp/brand/brand-select-list";
    }


    /**
     * @Author: zhouhong
     * @Description: 选择商品
     * @param: deviceIds 已选择商品ID
     * @Date: 2018/2/9 16:08
     */
    @RequestMapping("/selectCommodity")
    public String selectCommodity(String commodityIds, String commodityCodes, ModelMap map) {
        logger.debug("选择商品页面:{}", commodityIds);
        if (StringUtil.isNotBlank(commodityIds)) {
            map.put("commodityIds", commodityIds + ",");
            map.put("commodityCodes", commodityCodes + ",");
            map.put("selectNums", commodityIds.split(",").length);//选择个数
        }
        return "sp/commodityInfo/commodityInfo-select-list";
    }


    /**
     * @Author: zhouhong
     * @Description: 选择商品
     * @param: deviceIds 已选择商品ID
     * @Date: 2018/2/9 16:08
     */
    @RequestMapping("/selectCommodityByMap")
    public String selectCommodityByMap(String commodityIds, ModelMap map, String deviceId) {
        logger.debug("选择商品页面:{}", commodityIds);
        if (StringUtil.isNotBlank(commodityIds)) {
            map.put("commodityIds", commodityIds);
            map.put("selectNums", commodityIds.split(",").length);//选择个数
            map.put("selectNums", commodityIds.split(",").length);
        }
        map.put("deviceId", deviceId);

        return "sp/commodityInfo/commodityInfo-select-map-list";
    }


    /**
     * @Author: zhouhong
     * @Description: 选择商品
     * @param: deviceIds 已选择商品ID
     * @Date: 2018/2/9 16:08
     */
    @RequestMapping("/selectSingleCommodity")
    public String selectSingleCommodity(Integer types, ModelMap map) {
        if (null != types) {
            map.put("types", types);
        }
        return "sp/commodityInfo/commodityInfo-single-select-list";
    }

    /**
     * @return com.cloud.cang.common.PageListVo<java.util.List<com.cloud.cang.mgr.sb.domain.DeviceInfoDomain>>
     * @Author: zhouhong
     * @Description: 查询设备数据
     * @param: deviceInfoVo
     * @param: paramPage
     * @Date: 2018/2/9 16:21
     */
    @RequestMapping("/queryDeviceData")
    @ResponseBody
    public PageListVo<List<DeviceInfoDomain>> queryDeviceData(ParamPage paramPage, DeviceInfoVo deviceInfoVo, String allIstatus) {
        PageListVo<List<DeviceInfoDomain>> pageListVo;//返回的页面对象
        pageListVo = new PageListVo<List<DeviceInfoDomain>>();
        Page<DeviceInfoDomain> page = new Page<DeviceInfoDomain>();//新建分页对象
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (null == deviceInfoVo) {
            deviceInfoVo = new DeviceInfoVo();
        }
        //生成查询条件 (场景活动使用)
       /* Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator, 50);
        deviceInfoVo.setQueryCondition(queryCondition);*/
        //只能查询自己供应商
        deviceInfoVo.setSmerchantId(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
        deviceInfoVo.setIoperateStatus(10);
        if (StringUtils.isNotBlank(allIstatus) && "10".equals(allIstatus)) {
            deviceInfoVo.setStatusCondition("A.ISTATUS in(20,30,50)");
        } else if (StringUtils.isNotBlank(allIstatus) && "20".equals(allIstatus)) {
            deviceInfoVo.setStatusCondition("A.ISTATUS in(20,30,50)");
            Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
            String queryCondition = operatorService.generatePurviewSql(operator, 50);
            deviceInfoVo.setQueryCondition(queryCondition);
        } else {
            deviceInfoVo.setIstatus(20);
        }
        deviceInfoVo.setIdelFlag(0);/* 是否删除0否1是 */

        //页面对象排序方式 -> 按照ID升序排列
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            deviceInfoVo.setOrderStr(" A." + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        //分页查询
        page = deviceInfoService.selectPageByDomainWhere(page, deviceInfoVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 根商户可以所有商户设备
     *
     * @Author: zengzexiong
     * @param: deviceInfoVo
     * @param: paramPage
     * @param: deviceType 30=所有视觉柜子，40=带大屏的视觉柜子，50=云端识别设备（包含重力，不带大屏）
     * @Date: 2019年1月19日14:22:39
     */
    @RequestMapping("/queryRootDeviceData")
    @ResponseBody
    public PageListVo<List<DeviceInfoDomain>> queryRootDeviceData(ParamPage paramPage, DeviceInfoVo deviceInfoVo, String deviceType) {
        PageListVo<List<DeviceInfoDomain>> pageListVo;//返回的页面对象
        pageListVo = new PageListVo<List<DeviceInfoDomain>>();
        Page<DeviceInfoDomain> page = new Page<DeviceInfoDomain>();//新建分页对象
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (null == deviceInfoVo) {
            deviceInfoVo = new DeviceInfoVo();
        }

        String smerchantId = SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId();
        MerchantInfo merchantInfo = merchantInfoService.selectByPrimaryKey(smerchantId);
        if (null != merchantInfo) {
            if (!"0".equals(merchantInfo.getSrootId())) {
                deviceInfoVo.setSmerchantId(smerchantId); //只能查询自己供应商
            }
        }
        //生成查询条件
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator,50);
        deviceInfoVo.setQueryCondition(queryCondition);
        //生成查询条件
        if (StringUtils.isNotBlank(deviceType)) { // 云端识别柜子 10=传统 20=RFID射频 30=视觉 40=前端视觉+重力 50=云端识别 60=云端识别+重力
            if ("30".equals(deviceType)) {  // 所有视觉柜子
                deviceInfoVo.setStatusCondition(" A.ISTATUS in (20,30) and A.ITYPE in (30,40,50,60)");
            } else if ("40".equals(deviceType)) { // 带大屏的视觉柜子
                deviceInfoVo.setStatusCondition(" A.ISTATUS in (20,30) and A.ITYPE in (30,40)");
            } else if ("50".equals(deviceType)) { // 云端识别设备（包含重力，不带大屏）
                deviceInfoVo.setStatusCondition(" A.ISTATUS in (20,30) and A.ITYPE in (50,60)");
            } else if ("60".equals(deviceType)) { // 云端识别设备（正常）
                deviceInfoVo.setStatusCondition(" A.ISTATUS in (20)");
            }
        } else {
            deviceInfoVo.setStatusCondition(" A.ISTATUS in (20,30) ");
        }
        deviceInfoVo.setIoperateStatus(10);
        deviceInfoVo.setIdelFlag(0);/* 是否删除0否1是 */

        //页面对象排序方式 -> 按照ID升序排列
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            deviceInfoVo.setOrderStr(" A." + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        //分页查询
        page = deviceInfoService.selectPageByDomainWhere(page, deviceInfoVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 设备选择（单选）
     *
     * @param
     * @return
     */
    @RequestMapping("/selectDeviceOne")
    public String selectDeviceOne(String sid, ModelMap map, String selectType) {
        if (null != sid) {
            map.put("sid", sid);
        }
        map.put("selectType", selectType);
        return "sb/deviceInfo-selectOne-list";
    }


    /**
     * @Author: zhouhong
     * @Description: 查询设备数据
     * @param: deviceInfoVo
     * @param: paramPage
     * @Date: 2018/2/9 16:21
     */
    @RequestMapping("/queryAllDevice")
    @ResponseBody
    public PageListVo<List<DeviceInfoDomain>> queryAllDevice(ParamPage paramPage, DeviceInfoVo deviceInfoVo, String selectType) {
        PageListVo<List<DeviceInfoDomain>> pageListVo;//返回的页面对象
        pageListVo = new PageListVo<List<DeviceInfoDomain>>();
        Page<DeviceInfoDomain> page = new Page<DeviceInfoDomain>();//新建分页对象
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (null == deviceInfoVo) {
            deviceInfoVo = new DeviceInfoVo();
        }
        //只能查询自己供应商
        deviceInfoVo.setSmerchantId(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
        deviceInfoVo.setIoperateStatus(10);
        deviceInfoVo.setIdelFlag(0);/* 是否删除0否1是 */
        if (StringUtils.isNotBlank(selectType) && "10".equals(selectType)) {
            Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
            String queryCondition = operatorService.generatePurviewSql(operator,50);
            deviceInfoVo.setQueryCondition(queryCondition);
            deviceInfoVo.setStatusCondition("A.ISTATUS in(20,30,50)");
        } else {
            deviceInfoVo.setIstatus(20);
        }
        //页面对象排序方式 -> 按照ID升序排列
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            deviceInfoVo.setOrderStr(" A." + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        //分页查询
        page = deviceInfoService.selectAllDevice(page, deviceInfoVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }


    /**
     * @return com.cloud.cang.common.PageListVo<java.util.List<com.cloud.cang.mgr.sp.domain.CommodityDomain>>
     * @Author: zhouhong
     * @Description: 商品
     * @param: paramPage
     * @Date: 2018/2/10 12:43
     */
    @RequestMapping("/queryCommodityData")
    @ResponseBody
    public PageListVo<List<CommodityDomain>> queryCommodityData(ParamPage paramPage, CommodityVo commodityVo, String deviceId) {
        PageListVo<List<CommodityDomain>> pageListVo;//返回的页面对象
        pageListVo = new PageListVo<List<CommodityDomain>>();
        Page<CommodityDomain> page = new Page<CommodityDomain>();//新建分页对象
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (null == commodityVo) {
            commodityVo = new CommodityVo();
        }
        /*//生成查询条件
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator,60);
        commodityVo.setQueryCondition(queryCondition);*/
        //只能查询自己供应商
        commodityVo.setSmerchantId(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
        commodityVo.setIstatus(10);
        commodityVo.setIdelFlag(0);/* 是否删除0否1是 */

        //页面对象排序方式 -> 按照ID升序排列
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            commodityVo.setOrderStr(" A." + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        //分页查询
        if (StringUtils.isBlank(deviceId)) {
            page = commodityInfoService.selectPageByDomainWhere(page, commodityVo);
        } else {
            commodityVo.setDeviceId(deviceId);
            page = commodityInfoService.selectOrderCommodity(page, commodityVo);
        }
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }


    /**
     * @Author: zhouhong
     * @Description: 获取设备信息
     * @param: deviceIds 设备ID集合
     * @Date: 2018/2/10 11:00
     */
    @RequestMapping("/getDeviceByIds")
    @ResponseBody
    ResponseVo<List<SelectDeviceDomain>> getDeviceByIds(String[] deviceIds) {
        List<SelectDeviceDomain> sddList = null;
        if (null != deviceIds) {
            sddList = new ArrayList<SelectDeviceDomain>();
            DeviceInfo temp = null;
            SelectDeviceDomain sdd = null;
            for (String deviceId : deviceIds) {//循环设备ID
                temp = deviceInfoService.selectByPrimaryKey(deviceId);
                if (null != temp) {
                    sdd = new SelectDeviceDomain();
                    sdd.setDeviceId(temp.getId());
                    sdd.setDeviceCode(temp.getScode());
                    sdd.setDeviceName(temp.getSname());
                    sdd.setAddress(getAddress(temp));
                    sddList.add(sdd);
                }
            }
        }
        return ResponseVo.getSuccessResponse(sddList);
    }

    /**
     * @Author: zhouhong
     * @Description: 获取设备信息
     * @param: deviceIds 设备ID集合
     * @Date: 2018/2/10 11:00
     */
    @RequestMapping("/getCommodityByIds")
    @ResponseBody
    ResponseVo<List<SelectCommodityDomain>> getCommodityByIds(String[] commodityIds) {
        List<SelectCommodityDomain> sddList = null;
        if (null != commodityIds) {
            sddList = new ArrayList<SelectCommodityDomain>();
            CommodityInfo temp = null;
            SelectCommodityDomain sdd = null;
            for (String commodityId : commodityIds) {//循环设备ID
                temp = commodityInfoService.selectByPrimaryKey(commodityId);
                if (null != temp) {
                    sdd = new SelectCommodityDomain();
                    sdd.setCommodityId(temp.getId());
                    sdd.setCommodityCode(temp.getScode());
                    sdd.setCommodityName(temp.getSname());
                    sdd.setSalePrice(temp.getFsalePrice());
                    sddList.add(sdd);
                }
            }
        }
        return ResponseVo.getSuccessResponse(sddList);
    }

    /**
     * @Author: zhouhong
     * @Description: 获取设备地址信息
     * @param: deviceInfo 设备数据
     * @Date: 2018/2/10 11:00
     */
    private String getAddress(DeviceInfo deviceInfo) {
        StringBuffer sb = new StringBuffer();
        if (StringUtils.isNotBlank(deviceInfo.getSprovinceName())) {
            sb.append(deviceInfo.getSprovinceName());
        }
        if (StringUtils.isNotBlank(deviceInfo.getScityName())) {
            sb.append(deviceInfo.getScityName());
        }
        if (StringUtils.isNotBlank(deviceInfo.getSareaName())) {
            sb.append(deviceInfo.getSareaName());
        }
        if (StringUtils.isNotBlank(deviceInfo.getSputAddress())) {
            sb.append(deviceInfo.getSputAddress());
        }
        return sb.toString();
    }

    /**
     * @Author: yanlingfeng
     * @Description: 选择设备分组
     * @param: deviceIds 已选择设备ID
     * @Date: 2018/2/22 10:08
     */
    @RequestMapping("/selectDeviceGroup")
    public String selectDeviceGroup(String deviceIdGroups, ModelMap map) {
        logger.debug("选择设备分组页面:{}", deviceIdGroups);
        if (StringUtil.isNotBlank(deviceIdGroups)) {
            map.put("deviceIdGroups", deviceIdGroups + ",");
            map.put("selectNums", deviceIdGroups.split(",").length);//选择个数
        }
        return "sb/deviceInfo/deviceInfoGroup-select-list";
    }

    /**
     * @Author: yanlingfeng
     * @Description: 获取设备分组信息
     * @param: deviceIds 设备分组ID集合
     * @Date: 2018/2/27 11:00
     */
    @RequestMapping("/getDeviceGroupByIds")
    @ResponseBody
    ResponseVo<List<GroupManage>> getDeviceGroupByIds(String[] deviceGroupIds) {
        List<GroupManage> sddList = null;
        if (null != deviceGroupIds) {
            sddList = new ArrayList<GroupManage>();
            GroupManage temp = null;
            for (String deviceGroupId : deviceGroupIds) {//循环设备ID
                temp = groupManageService.selectByPrimaryKey(deviceGroupId);
                if (null != temp) {
                    sddList.add(temp);
                }
            }
        }
        return ResponseVo.getSuccessResponse(sddList);
    }

    /**
     * @Author: yanlingfeng
     * @Description: 获取设备信息
     * @param: deviceIds 设备ID集合
     * @Date: 2018/2/10 11:00
     */
    @RequestMapping("/getDeviceAndGroupByIds")
    @ResponseBody
    ResponseVo<List<SelectDeviceDomain>> getDeviceAndGroupByIds(String[] deviceIds) {
        List<SelectDeviceDomain> sddList = null;
        if (null != deviceIds) {
            sddList = new ArrayList<SelectDeviceDomain>();
            DeviceInfoDomain temp = null;
            SelectDeviceDomain sdd = null;
            for (String deviceId : deviceIds) {//循环设备ID
                temp = deviceInfoService.selectDeviceMessageById(deviceId);
                if (null != temp) {
                    sdd = new SelectDeviceDomain();
                    sdd.setDeviceId(temp.getId());
                    sdd.setDeviceCode(temp.getScode());
                    sdd.setDeviceName(temp.getSname());
                    sdd.setAddress(getAddress(temp));
                    sdd.setGroupName(temp.getSgroupName());
                    sddList.add(sdd);
                }
            }
        }
        return ResponseVo.getSuccessResponse(sddList);
    }

    /**
     * 导出配置页面
     *
     * @return
     */
    @RequestMapping("/exportConfig")
    public String exportConfig() {
        return "sys/export_config";
    }

    /**
     * 导出配置页面
     *
     * @return
     */
    @RequestMapping("/exportConfigs")
    public String exportConfigs() {
        return "sys/export_configOne";
    }
}
