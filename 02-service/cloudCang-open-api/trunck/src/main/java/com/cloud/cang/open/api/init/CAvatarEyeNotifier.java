package com.cloud.cang.open.api.init;

import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.eye.sdk.CAvatarEye;
import com.cloud.cang.eye.sdk.INotifier;
import com.cloud.cang.eye.sdk.bean.ResultBean;
import com.cloud.cang.eye.sdk.bean.UpdateResultBean;
import com.cloud.cang.eye.sdk.socket.Connection;
import com.cloud.cang.model.sb.DeviceUpgrade;
import com.cloud.cang.open.api.common.APIConstant;
import com.cloud.cang.open.api.common.utils.VisualUtils;
import com.cloud.cang.open.api.cr.service.ServerListService;
import com.cloud.cang.open.api.sb.service.DeviceUpgradeDetailsService;
import com.cloud.cang.open.api.sb.service.DeviceUpgradeService;
import com.cloud.cang.utils.StringUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class CAvatarEyeNotifier implements INotifier {

	@Autowired
	private ServerListService serverListService;
	@Autowired
	private ICached iCached;
	@Autowired
	private DeviceUpgradeService deviceUpgradeService;
	@Autowired
	private DeviceUpgradeDetailsService deviceUpgradeDetailsService;

	@Override
	public void exceptionReported(String exceptions) {
		//异常上报回调处理
		CAvatarEye.LOG("CAvatarEyeNotifier exceptionReported" + exceptions,1);
	}

	@Override
	public void updateReported(String updateInfo) {
		try {
			if (StringUtil.isNotBlank(updateInfo)) {
				//视觉服务更新回调处理
				CAvatarEye.LOG("CAvatarEyeNotifier updateReported 更新中------------返回参数：" + updateInfo, 1);
				UpdateResultBean resultBean = new Gson().fromJson(updateInfo, UpdateResultBean.class);
				String progress = resultBean.getProgress();
				int update = StringUtil.toNumber(progress, -1);
				if (update == 100) {//更新成功
					//重启视觉服务
					Connection conn = VisualUtils.getGoodsRecognition().getConnsMap().get(resultBean.getServerCode());
					if (null == conn) {
						CAvatarEye.LOG("CAvatarEyeNotifier updateReported 服务连接信息为空：" + resultBean.getServerCode(), 2);
						return;
					}
					serverListService.restart(conn);
				}
				if (update < 0) {//更新失败
					String updateId = (String) iCached.get(APIConstant.UPDATE_SERVER_MODEL + resultBean.getServerCode());
					//修改更新日志
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("status", 30);
					paramMap.put("errorMsg", "更新失败，下载异常");
					paramMap.put("tendTime", new Date());
					paramMap.put("serverCode", resultBean.getServerCode());
					paramMap.put("smainId", updateId);
					Integer updateFlag = deviceUpgradeDetailsService.updateByMap(paramMap);
					if (null != updateFlag && updateFlag.intValue() > 0) {//更新主表
						DeviceUpgrade deviceUpgrade = deviceUpgradeService.selectSyncByPrimaryKey(updateId);
						if (deviceUpgrade.getIstatus().intValue() != 30) {
							DeviceUpgrade updateObj = new DeviceUpgrade();
							updateObj.setId(deviceUpgrade.getId());
							updateObj.setIupgradeFailNum(deviceUpgrade.getIupgradeFailNum() != null ? (deviceUpgrade.getIupgradeFailNum() + 1) : 1);
							if (null == deviceUpgrade.getIupgradeSuccessNum()) {
								deviceUpgrade.setIupgradeSuccessNum(0);
							}
							int total = deviceUpgrade.getIupgradeSuccessNum() + updateObj.getIupgradeFailNum();
							if (total >= deviceUpgrade.getInoticeNum()) {
								updateObj.setIstatus(30);
							}
							deviceUpgradeService.updateBySelective(updateObj);
						}
					}
				}
			}
		} catch (Exception e) {
			CAvatarEye.LOG("CAvatarEyeNotifier updateReported 更新操作异常：" + e.getMessage(), 2);
		}
	}
}
