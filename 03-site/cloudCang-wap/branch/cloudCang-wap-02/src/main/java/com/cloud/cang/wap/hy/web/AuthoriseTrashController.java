package com.cloud.cang.wap.hy.web;

import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.security.vo.AuthorizationVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yan
 *         智能垃圾箱授权 控制层
 */
@Controller
@RequestMapping("/personal")
public class AuthoriseTrashController {
    private static Logger logger = LoggerFactory.getLogger(AuthoriseTrashController.class);

    @RequestMapping("/authTrash")
    public String authTrash(ModelMap modelMap) {
        try {
            AuthorizationVO authVo = SessionUserUtils.getSessionAttributeForUserDtl();//获取用户数据
            modelMap.put("mobile", authVo.getMobile());
            modelMap.put("memId", authVo.getId());
            return "index/authTrash";
        } catch (Exception e) {
            logger.error("智能垃圾箱授权异常：{}", e);
        }
        return "error/error";
    }
}
