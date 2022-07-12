package com.cloud.cang.mgr.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cloud.cang.core.sys.service.ParameterGroupDetailService;
import com.cloud.cang.mgr.sys.vo.MenuVo;
import com.cloud.cang.model.sys.Menu;
import com.cloud.cang.model.sys.Operatorrole;
import com.cloud.cang.model.sys.ParameterGroupDetail;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.mgr.sys.dao.FastMenuDao;
import com.cloud.cang.model.sys.FastMenu;
import com.cloud.cang.mgr.sys.service.FastMenuService;

@Service
public class FastMenuServiceImpl extends GenericServiceImpl<FastMenu, String> implements
        FastMenuService {

    @Autowired
    FastMenuDao fastMenuDao;
    @Autowired
    private ParameterGroupDetailService parameterGroupDetailService;

    @Override
    public GenericDao<FastMenu, String> getDao() {
        return fastMenuDao;
    }


    /**
     * 查看用户可添加的菜单权限
     *
     * @param
     */
    public List<MenuVo> selectUserMenu(String id) {
        return fastMenuDao.selectUserMenu(id);
    }

    /**
     * 保存用户选择的菜单
     *
     * @param
     */
    @Override
    public void saveFastMenu(String[] RoleIds, String operatorId) {
        // 删除已经拥有快捷菜单
        fastMenuDao.deleteByOperatorId(operatorId);
        if (StringUtils.isNotBlank(operatorId)) {
            for (String roleId : RoleIds) {
                ParameterGroupDetail parameterGroupDetail = parameterGroupDetailService.selectByPrimaryKey(roleId);
                FastMenu fastMenu = new FastMenu();
                fastMenu.setIdelFlag(0);
                fastMenu.setSoperatorId(operatorId);
                String[] fileNameSplit = parameterGroupDetail.getSname().split("#");
                String name = fileNameSplit[0];
                fastMenu.setSmenuName(name);
                fastMenu.setSmenuIcon(parameterGroupDetail.getSremark());
                fastMenu.setSmenuPath(fileNameSplit[1]);
                fastMenu.setTaddTime(DateUtils.getCurrentDateTime());
                fastMenu.setTupdateTime(DateUtils.getCurrentDateTime());
                fastMenuDao.insert(fastMenu);
            }
        }
    }

    /**
     * 根据Id查询菜单路径
     *
     * @param id 快捷菜单数字字典明细Id
     */
    @Override
    public String selectMenuPathById(String id) {
        return fastMenuDao.selectMenuPathById(id);
    }

    @Override
    public void updateByName(FastMenu fastMenu) {
        fastMenuDao.updateByName(fastMenu);
    }

    @Override
    public void deleteByName(String name) {
        fastMenuDao.deleteByName(name);
    }

    /**
     * 是否已选择该快捷菜单
     *
     * @param name: 快捷菜单名
     */
    public boolean isSelect(String name) {
        Map<String, String> map = new HashMap<>();
        map.put("sname", name);
        map.put("operatorId", SessionUserUtils.getSessionAttributeForUserDtl().getId());
        if (fastMenuDao.isSelect(map) > 0) {
            return true;
        }
        return false;
    }

    /**
     * 查询快捷菜单数据
     *
     * @param operId 系统用户ID
     * @return
     */
    @Override
    public List<FastMenu> selectByOperId(String operId) {
        return fastMenuDao.selectByOperId(operId);
    }

    /**
     * 商户是否有该权限
     *
     * @param spurCode: 权限码
     */
    @Override
    public boolean isMerchantPurview(String spurCode) {
        Map<String, String> map = new HashMap<>();
        map.put("spurCode", spurCode);
        map.put("smerchantId", SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
        if (fastMenuDao.isMerchantPurview(map) > 0) {
            return true;
        }
        return false;
    }
}