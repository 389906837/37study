package com.cloud.cang.mgr.ac.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.mgr.ac.domain.CouponBatchDomain;
import com.cloud.cang.mgr.ac.domain.CouponBatchSelectUserDomain;
import com.cloud.cang.mgr.ac.vo.CouponBatchParam;
import com.cloud.cang.mgr.ac.vo.CouponBatchVo;
import com.cloud.cang.model.ac.CouponBatch;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CouponBatchService extends GenericService<CouponBatch, String> {

    /**
     * 优惠券批量下发
     * @param page
     * @param couponBatchVo
     * @return
     */
    Page<CouponBatchDomain> queryDataCoupon(Page<CouponBatchDomain> page, CouponBatchVo couponBatchVo);

    /**
     * 逻辑删除优惠券下发用户ID集合
     * @param checkboxId
     */
    void delete(String[] checkboxId) throws Exception;

    /**
     * 保存
     * @param couponBatch
     * @param param
     * @return
     */
    ResponseVo<CouponBatch> saveOrUpdate(HttpServletRequest request, CouponBatch couponBatch, CouponBatchParam param) throws ServiceException;

    /**
     * 从Excel中读取用户信息
     *
     * @param excelFile
     * @return
     */
    ResponseVo<List<CouponBatchSelectUserDomain>> readUserInfo(MultipartFile excelFile);


}