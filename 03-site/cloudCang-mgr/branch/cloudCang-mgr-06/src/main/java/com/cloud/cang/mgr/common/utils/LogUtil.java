package com.cloud.cang.mgr.common.utils;

import javax.servlet.http.HttpServletRequest;

import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.mgr.sl.service.OperLogService;
import com.cloud.cang.mgr.sl.service.VistLogService;
import com.cloud.cang.model.sl.OperLog;
import com.cloud.cang.model.sl.VistLog;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.security.vo.AuthorizationVO;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.SpringContext;
import org.apache.commons.lang3.StringUtils;



/**
 *  日志工具类
 * @author zhouhong
 * @version 1.0
 */
public class LogUtil {
	
	//------------------行为----------------
	/**添加*/
	public static final int AC_ADD=1;
	/**修改*/
	public static final int AC_EDIT=2;
	/**删除*/
	public static final int AC_DELETE=3;
	/**查看*/
	public static final int AC_VIEW=4;
    /**其他*/
    public static final int AC_OTHER=5;
    /**登录*/
    public static final int AC_LOGIN=6;
	
 	static VistLogService vistLogService;
    static OperLogService operLogService;

    private static VistLogService getVistLogService() {
        if (vistLogService == null) {
            vistLogService = SpringContext.getBean(VistLogService.class);
        }
        return vistLogService;
    }
    private static OperLogService getOperLogService() {
        if (operLogService == null) {
            operLogService = SpringContext.getBean(OperLogService.class);
        }
        return operLogService;
    }
	
    /**
     * 获取请求路径
     * @param request
     * @return
     */
    public static  String getRequestPath(HttpServletRequest request) {
        String rtnUrl = "";// 返回路径
        String reqUrl = request.getRequestURI();
        if (StringUtils.isNotBlank(reqUrl)) {
            rtnUrl = reqUrl.replace(request.getContextPath(), "").trim();
        }
        return rtnUrl;
    }
    
    /**
     *  访问日志记录
     * @param request
     */
    public static  void addVistLog(HttpServletRequest request)
    {
        AuthorizationVO vo = SessionUserUtils.getSessionAttributeForUserDtl();
        if (vo != null) {
            final VistLog vistLog = new VistLog();
            vistLog.setToperateDate(DateUtils.getCurrentDateTime());
            vistLog.setSrealName(vo.getRealName());
            vistLog.setSuerId(vo.getId());
            vistLog.setSip(SessionUserUtils.getIpAddr(request));
            vistLog.setSusername(vo.getUserName());
            vistLog.setSactionpath(getRequestPath(request));
            vistLog.setSsourceSystem("cloudCang");
            ExecutorManager.getInstance().execute(new Runnable() {
                @Override
                public void run() {
                    getVistLogService().insert(vistLog);
                }
            });
        }
    }

    /**
     * 操作日志
     * @param iaction 操作类型
     * @param scontent 操作内容
     * @param sremark 备注
     */
    public static void addOPLog(int iaction,String scontent,String sremark){
        AuthorizationVO vo = SessionUserUtils.getSessionAttributeForUserDtl();
        if (vo != null) {
            final OperLog operLog = new OperLog();
            operLog.setIaction(iaction);
            operLog.setScontent(scontent);
            operLog.setSremark(sremark);
            operLog.setSip(SessionUserUtils.getRequestIp());
            operLog.setSrealName(vo.getRealName());
            operLog.setSuerId(vo.getId());
            operLog.setSusername(vo.getUserName());
            operLog.setToperateDate(DateUtils.getCurrentDateTime());
            operLog.setSsourceSystem("cloudCang");
            ExecutorManager.getInstance().execute(new Runnable() {
				@Override
				public void run() {
                    getOperLogService().insert(operLog);
				}
			});
        }
    }
    /**
     * 操作日志
     * @param iaction 操作类型
     * @param scontent 操作内容
     * @param sremark 备注
     * @param host IP地址
     */
    public static void addOPLog(int iaction,String scontent,String sremark,String host){
        AuthorizationVO vo = SessionUserUtils.getSessionAttributeForUserDtl();
        if (vo != null) {
            final OperLog operLog = new OperLog();
            operLog.setIaction(iaction);
            operLog.setScontent(scontent);
            operLog.setSremark(sremark);
            operLog.setSip(host);
            operLog.setSrealName(vo.getRealName());
            operLog.setSuerId(vo.getId());
            operLog.setSusername(vo.getUserName());
            operLog.setToperateDate(DateUtils.getCurrentDateTime());
            operLog.setSsourceSystem("cloudCang");
            ExecutorManager.getInstance().execute(new Runnable() {
                @Override
                public void run() {
                    getOperLogService().insert(operLog);
                }
            });
        }
    }
}
