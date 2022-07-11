package com.cloud.cang.core.utils;


import com.cloud.cang.model.sys.ParameterGroupDetail;
import com.cloud.cang.utils.FtpUser;

import java.util.Iterator;
import java.util.Set;


public class FtpParamUtil {
	
private static FtpUser ftpConfig = null;
    
    public static  FtpUser getFtpUser() {

       ftpConfig = new FtpUser();
        Set<ParameterGroupDetail> ParameterGroupDetailList =  GrpParaUtil.get(GrpParaUtil.FTP_CONFIG);
         Iterator<ParameterGroupDetail> iterator = ParameterGroupDetailList.iterator();
         while(iterator.hasNext())
         {
             ParameterGroupDetail detail = iterator.next();
             if (detail.getSname().equals("username"))
             {
                 ftpConfig.setUserName(detail.getSvalue());
             }else if (detail.getSname().equals("password"))
             {
                 ftpConfig.setPassword(detail.getSvalue());
             } else if (detail.getSname().equals("ip"))
             {
                 ftpConfig.setIp(detail.getSvalue());
             } else if (detail.getSname().equals("port"))
             {
                 ftpConfig.setPort(detail.getSvalue());
             }
         } 
        return ftpConfig;
        
    }

}
