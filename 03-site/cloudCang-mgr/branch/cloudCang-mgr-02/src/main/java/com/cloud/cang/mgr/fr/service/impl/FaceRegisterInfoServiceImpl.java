package com.cloud.cang.mgr.fr.service.impl;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.BaiduAiFaceErrorCodeEnum;
import com.cloud.cang.core.common.aiFace.ResponseAip;
import com.cloud.cang.core.common.utils.AiFaceUtils;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.fr.dao.FaceOperLogDao;
import com.cloud.cang.mgr.fr.dao.FaceRegisterInfoDao;
import com.cloud.cang.mgr.fr.domain.FaceRegisterInfoDomain;
import com.cloud.cang.mgr.fr.service.FaceRegisterInfoService;
import com.cloud.cang.mgr.fr.vo.FaceRegisterInfoVo;
import com.cloud.cang.mgr.hy.dao.MemberInfoDao;
import com.cloud.cang.model.fr.FaceOperLog;
import com.cloud.cang.model.fr.FaceRegisterInfo;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.utils.DateUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FaceRegisterInfoServiceImpl extends GenericServiceImpl<FaceRegisterInfo, String> implements
		FaceRegisterInfoService {

	private static final Logger logger = LoggerFactory.getLogger(FaceRegisterInfoServiceImpl.class);

	@Autowired
	FaceRegisterInfoDao faceRegisterInfoDao;

	@Autowired
	MemberInfoDao memberInfoDao;

	@Autowired
	FaceOperLogDao faceOperLogDao;

	
	@Override
	public GenericDao<FaceRegisterInfo, String> getDao() {
		return faceRegisterInfoDao;
	}


	/**
	 * 分页查询
	 *
	 * @param page               分页对象
	 * @param faceRegisterInfoVo 查询条件
	 * @return
	 */
	@Override
	public Page<FaceRegisterInfoDomain> selectPageByDomainWhere(Page<FaceRegisterInfoDomain> page, FaceRegisterInfoVo faceRegisterInfoVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return (Page<FaceRegisterInfoDomain>) faceRegisterInfoDao.selectByDomainWhere(faceRegisterInfoVo);
	}


	/**
	 * 删除人脸注册信息
	 *
	 * @param checkboxId 人脸信息ID
	 * @return 人脸编号
	 */
	@Override
	public ResponseVo<String> deleteFaceInfo(String[] checkboxId) {
		StringBuffer stringBuffer = new StringBuffer();
		for (String id : checkboxId) {
			FaceRegisterInfo faceRegisterInfo = faceRegisterInfoDao.selectByPrimaryKey(id);
			if (null == faceRegisterInfo) {
				logger.error("删除人脸注册信息，人脸注册信息ID：{}未查询到对应用户人脸注册信息", id);
				throw new ServiceException("人脸信息不存在");
			}

			String userId = faceRegisterInfo.getSmemberId();
			String faceToken = faceRegisterInfo.getSfaceToken();

			// 1.去百度人脸库删除该人脸信息，如果存在删除人脸信息返回code=0，不存根据错误信息修改人脸信息表
			if (StringUtils.isBlank(userId) && StringUtils.isBlank(faceToken)) {
				logger.error("删除人脸注册信息，人脸注册信息ID：{}，未查询到对应用户ID与用户图片facetoken", id);
				throw new ServiceException("用户" + faceRegisterInfo.getSmemberName() + "信息与人脸信息不匹配");
			}

			ResponseAip responseAip = AiFaceUtils.delete(userId, faceToken);
			if (null == responseAip) {
				logger.error("调用百度API删除人脸信息出错，用户ID：{}", userId);
				throw new ServiceException("删除人脸信息出现错误");
			}
			Integer code = responseAip.getError_code(); //返回码 等于0表示成功
			if (null == code) {
				logger.error("调用百度API删除人脸信息出错，用户ID：{}", userId);
				throw new ServiceException("调用百度API删除人脸信息出错");
			}
			int codeInt = code;
			if (0 != codeInt && BaiduAiFaceErrorCodeEnum.MATCH_USER_IS_NOT_FOUND.getCode() != codeInt && BaiduAiFaceErrorCodeEnum.FACE_TOKEN_NOT_EXIST.getCode() != codeInt
					&& BaiduAiFaceErrorCodeEnum.GROUP_IS_NOT_EXIST.getCode() != codeInt && BaiduAiFaceErrorCodeEnum.USER_IS_NOT_EXIST.getCode() != codeInt &&
					BaiduAiFaceErrorCodeEnum.FACE_IS_NOT_EXIST.getCode() != codeInt) {
				logger.error("调用百度API出现未知类型错误，错误码：{}", codeInt);
				throw new ServiceException("删除人脸信息出");
			}

			// 2.记录人脸删除操作信息
			FaceOperLog faceOperLogTemp = new FaceOperLog();
			faceOperLogTemp.setType(50);
			faceOperLogTemp.setToperTime(DateUtils.getCurrentDateTime());
			faceOperLogTemp.setTaddTime(DateUtils.getCurrentDateTime());
			faceOperLogTemp.setSoperDesc("操作员手动删除用户人脸注册信息");
			faceOperLogTemp.setIoperResult(1);
			faceOperLogTemp.setSmemberId(userId);
			faceOperLogTemp.setSmemberName(faceRegisterInfo.getSmemberName());
			faceOperLogTemp.setSmemberCode(faceRegisterInfo.getSmemberCode());
			faceOperLogDao.insert(faceOperLogTemp);


			// 3.修改人脸注册表
			FaceRegisterInfo faceRegisterInfoTemp = new FaceRegisterInfo();
			faceRegisterInfoTemp.setId(id);
			faceRegisterInfoTemp.setIdelFlag(1);
			faceRegisterInfoTemp.setItype(40);
			faceRegisterInfoDao.updateByIdSelective(faceRegisterInfoTemp);

			// 4.修改会员表
			MemberInfo memberInfoTemp = new MemberInfo();
			memberInfoTemp.setId(userId);
			memberInfoTemp.setIisRegFace(0);
			memberInfoDao.updateByIdSelective(memberInfoTemp);
			stringBuffer.append(faceRegisterInfo.getSfaceCode());

		}
		return ResponseVo.getSuccessResponse(stringBuffer.toString());
	}

}