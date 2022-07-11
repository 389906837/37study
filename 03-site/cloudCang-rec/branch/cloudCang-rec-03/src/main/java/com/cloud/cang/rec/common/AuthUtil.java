
package com.cloud.cang.rec.common;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.rec.sys.service.PurviewService;
import com.cloud.cang.model.sys.Purview;
import com.cloud.cang.security.utils.SessionUserUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 权限判断工具类
 * PS:判断当前Session里是否有该权限码，没有抛出没有权限异常
 *
 * @author zhouhong
 * @version 1.0
 */

public class AuthUtil {

    @Autowired
    PurviewService purviewService;

    public void check(String scode) {
        //不存在权限
        if (!SessionUserUtils.hasRole(scode)) {
            Purview t = new Purview();
            t.setSpurCode(scode);
            List<Purview> purviews = purviewService.selectByEntityWhere(t);
            if (null == purviews || purviews.isEmpty()) {
                throw new ServiceException("不存在这个权限");
            }
            Purview purview = purviews.get(0);
            throw new ServiceException("没有" + purview.getSpurName() + "的权限");
        }
    }

}
