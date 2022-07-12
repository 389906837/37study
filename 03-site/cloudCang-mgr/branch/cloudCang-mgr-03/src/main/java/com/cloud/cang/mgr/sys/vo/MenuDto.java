package com.cloud.cang.mgr.sys.vo;

import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
import com.cloud.cang.model.sys.Menu;
import com.cloud.cang.utils.StringUtil;

/**
 * Created by yan on 2018/3/7.
 */
public class MenuDto extends Menu {

    private String smenuName; //转义菜单名称

    public String getSmenuName() {
        if (StringUtil.isNotBlank(this.getSname())) {
            String tempName = MessageSourceUtils.getMessageByKey(this.getSname(),null);
            if (StringUtil.isNotBlank(tempName)) {
                return tempName;
            } else {
                return this.getSname();
            }
        }
        return this.getSname();
    }

    public void setSmenuName(String smenuName) {
        this.smenuName = smenuName;
    }

}
