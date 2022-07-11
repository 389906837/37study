package com.cloud.cang.core.utils;


import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.SpringContext;
import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



/**
 * ZooKeeper 工具类，用于通知各节点
 * @author zhouhong
 * @version 1.0
 */
@Component
public class ZookpUtil {
	
    private  CuratorFramework curatorFramework=null;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private  CuratorFramework getCuratorFramework() {
		if (curatorFramework == null) {
			curatorFramework = SpringContext.getBean(CuratorFramework.class);
		}
		return curatorFramework;
	}
	
	/**
     *   创建或更新一个节点
     * @param 
     * @return 是否成功
     */
    public Boolean  createrOrUpdate(String spath){
        try {
            //检查是否存在
            if(getCuratorFramework().checkExists().forPath(spath)==null){
            	getCuratorFramework().create().forPath(spath, DateUtils.getCurrentSTimeNumber().getBytes());
            }else{
            	getCuratorFramework().setData().forPath(spath,DateUtils.getCurrentSTimeNumber().getBytes());
            }
            logger.info("Zookeeper 创建或更新节点:"+spath);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } 
    }
   
}