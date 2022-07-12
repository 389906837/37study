package com.cloud.cang.mgr.sys.service.impl;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.sys.dao.MenuDao;
import com.cloud.cang.mgr.sys.domain.MenuDomain;
import com.cloud.cang.mgr.sys.service.MenuService;
import com.cloud.cang.model.sys.Menu;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl extends GenericServiceImpl<Menu, String> implements
		MenuService {

	@Autowired
	MenuDao menuDao;

	
	@Override
	public GenericDao<Menu, String> getDao() {
		return menuDao;
	}

	/**
	 * 根据用户ID 商户ID 查询用户权限范围内的菜单
	 * @param param 查询参数 用户ID 商户ID
	 * @return
	 */
	@Override
	public List<Menu> queryByMap(Map<String, Object> param) {
		return this.menuDao.queryByMap(param);
	}
	/**
	 * 获取商户所有菜单
	 * @param merchantId 商户ID
	 * @return
	 */
	@Override
	public List<Menu> selectByMerchantId(String merchantId) {
		return this.menuDao.selectByMerchantId(merchantId);
	}

	/**
	 * 菜单名称是否存在
	 * @return
	 */
	@Override
	public boolean isExistName(Menu param) {
		if (menuDao.isNameExist(param) > 0)
			return true;
		return false;
	}
	/**
	 * 保存
	 * @return
	 */
	@Override
	public void save(Menu menu, String id) {
		if (StringUtil.isBlank(id)) {
			//初始值
			if (StringUtil.isNotBlank(menu.getSparentId()) && menu.getSparentId().equals("0")) {
				//根菜单
				menu.setBisRoot(1);
				menu.setBisLeaf(0);
				menu.setImenuLevel(1);
				menu.setSmenuPath("/");
			} else {
				menu.setImenuLevel(2);
				menu.setBisRoot(0);
				menu.setBisLeaf(1);
				Menu menu1 = menuDao.selectByPrimaryKey(menu.getSparentId());
				if (menu1.getBisRoot() == 0) {
					menu1.setBisLeaf(0);
					menu1.setBisRoot(1);
					menu.setImenuLevel(1);
					menu.setSmenuPath("/");
					menuDao.updateByIdSelective(menu1);
				}
			}
			menu.setBisDisplay(1);
			menu.setSpurId("0");
			menu.setDaddDate(DateUtils.getCurrentDateTime());
			menuDao.insert(menu);
		} else {
			menuDao.updateByIdSelective(menu);
		}
	}
	/**
	 * 删除
	 * @return
	 */
	@Override
	public void delete(String[] checkboxId) {
		for (String id:checkboxId) {
			//删除菜单，菜单权限码，权限码分配
			//删除角色权限码关联表
			menuDao.deleteRolePurviewMenuId(id);
			//删除菜单商户权限码关联表
			menuDao.deletePurviewMerchantByMenuId(id);
			//删除菜单权限码
			menuDao.deletePurviewByMenuId(id);
			//删除菜单
			menuDao.deleteByPrimaryKey(id);
		}
	}
	/**
	 * 分页查询所有菜单记录
	 * @param page 分页参数
	 * @param menu 查询参数
	 * @return
	 */
	@Override
	public Page<Menu> queryAllMenu(Page<Menu> page, MenuDomain menu) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return menuDao.queryAllMenu(menu);
	}
	/**
	 * 获取父菜单
	 * @return
	 */
	@Override
	public List<Menu> selectByParentMenu() {
		return menuDao.selectByParentMenu();
	}
	/**
	 * 获取所有菜单列表
	 * @return
	 */
	@Override
	public List<Menu> queryAllMenu() {
		MenuDomain menu = new MenuDomain();
		menu.setBisDisplay(1);
		return menuDao.queryAllMenu(menu);
	}
}