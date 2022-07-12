package com.cloud.cang.mgr.sys.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.sys.domain.UserPurviewDomain;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.mgr.sys.vo.UserPurviewVo;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**用户权限展示
 * Created by yan on 2018/1/25.
 */
@Controller
@RequestMapping("/userPurview")
public class UserPurviewController {

    private static final Logger log = LoggerFactory.getLogger(UserPurviewController.class);


    @Autowired
    OperatorService operatorService;

    @RequestMapping("/list")
    public String list(ModelMap map) {

        return "sys/userPurview/userPurview-list";
    }

    /**
     * 查询用户权限列表
     *
     * @param userPurviewDomain
     * @return PageListVo<List<UserPurviewVo>>
     */
    @RequestMapping("/queryData")
    public @ResponseBody
    PageListVo<List<UserPurviewVo>> queryDataByCondition(UserPurviewDomain userPurviewDomain,
                                                         ParamPage paramPage) {
        PageListVo<List<UserPurviewVo>> pageListVo = new PageListVo<List<UserPurviewVo>>();
        Page<UserPurviewVo> page = new Page<UserPurviewVo>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            userPurviewDomain.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        userPurviewDomain.setBisDelete(0);
        userPurviewDomain.setSmerchantId(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
        page = operatorService.selectUserPurview(page, userPurviewDomain);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }


}
