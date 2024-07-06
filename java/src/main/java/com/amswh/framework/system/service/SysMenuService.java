package com.amswh.framework.system.service;

import com.amswh.framework.system.model.Constants;
import com.amswh.framework.system.model.MetaVo;
import com.amswh.framework.system.model.RouterVo;
import com.amswh.framework.system.model.UserConstants;
//import com.amswh.framework.utils.StringUtils;
import com.amswh.framework.system.model.SysMenu;
import com.amswh.framework.system.mapper.ISysMenu;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;
import com.amswh.framework.system.model.TreeSelect;

public class SysMenuService extends ServiceImpl<ISysMenu, SysMenu> {
    public static final String PREMISSION_STRING = "perms[\"{0}\"]";





    @Resource
    SysRoleService roleService;

//    @Autowired
//    private ISysR roleMenuMapper;

    /**
     * 根据用户查询系统菜单列表
     *
     * @param partyId 用户ID
     * @return 菜单列表
     */

    public List<SysMenu> selectMenuList(String partyId)
    {
        return selectMenuList(new HashMap<>(), partyId);
    }

    /**
     * 查询系统菜单列表
     *
     * @param mp 菜单信息
     * @return 菜单列表
     */

    public List<SysMenu> selectMenuList(Map<String,Object> mp, String partyId)
    {
        List<SysMenu> menuList = null;
        List<String> roles=roleService.getPartyRoles(partyId);
        // 管理员显示所有菜单信息
        if (roles.contains("admin"))
        {
            menuList = this.baseMapper.selectMenuList(mp);
        }
        else
        {
            mp.put("partyId", partyId);
            menuList = this.baseMapper.selectMenuListByUserId(mp);
        }
        return menuList;
    }

    /**
     * 根据用户ID查询权限
     *
     * @param partyId 用户ID
     * @return 权限列表
     */

    public Set<String> selectMenuPermsByUserId(String partyId)
    {
        List<String> perms = this.baseMapper.getPartyMenuPermissions(partyId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms)
        {
            if (perm!=null && !Objects.equals(perm.trim(), ""))
            {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 根据用户ID查询菜单
     *
     * @param partyId 用户名称
     * @return 菜单列表
     */

    public List<SysMenu> selectMenuTreeByUserId(String partyId)
    {
        List<SysMenu> menus = null;
        List<String> roles=roleService.getPartyRoles(partyId);
        if (roles.contains("admin"))
        {
            menus = this.baseMapper.selectMenuTreeAll();
        }
        else
        {
            menus = this.baseMapper.selectMenuTreeByPartyId(partyId);
        }
        return getChildPerms(menus, 0);
    }

    /**
     * 根据角色ID查询菜单树信息
     *
     * @param roleId 角色ID
     * @return 选中菜单列表
     */

    public List<Integer> selectMenuListByRoleId(Long roleId)
    {
       // SysRole role = roleMapper.selectRoleById(roleId);
       // SysRole role=roleService.getById(roleId);
       // return this.baseMapper.selectMenuListByRoleId(roleId, role.isMenuCheckStrictly());
        return this.baseMapper.selectMenuListByRoleId(roleId);
    }

    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */

    public List<RouterVo> buildMenus(List<SysMenu> menus)
    {
        List<RouterVo> routers = new LinkedList<RouterVo>();
        for (SysMenu menu : menus)
        {
            RouterVo router = new RouterVo();
            router.setHidden(menu.isVisible());
            router.setName(getRouteName(menu));
            router.setPath(getRouterPath(menu));
            router.setComponent(getComponent(menu));
           // router.setQuery(menu.getQuery());
            router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), true, menu.getPath()));
            List<SysMenu> cMenus = menu.getChildren();
            if ( cMenus.size() > 0 && "directory".equals(menu.getMenuType()))
            {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildMenus(cMenus));
            }
            else if (isMenuFrame(menu))
            {
                router.setMeta(null);
                List<RouterVo> childrenList = new ArrayList<RouterVo>();
                RouterVo children = new RouterVo();
                children.setPath(menu.getPath());
               // children.setComponent(menu.getComponent());
                children.setName(StringUtils.capitalize(menu.getPath()));
                children.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), menu.isCached(), menu.getPath()));
               // children.setQuery(menu.getQuery());
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            else if (menu.getParentId() == 0 && isInnerLink(menu))
            {
                router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon()));
                router.setPath("/inner");
                List<RouterVo> childrenList = new ArrayList<RouterVo>();
                RouterVo children = new RouterVo();
                String routerPath = StringUtils.replaceEach(menu.getPath(), new String[] { Constants.HTTP, Constants.HTTPS }, new String[] { "", "" });
                children.setPath(routerPath);
                children.setComponent(UserConstants.INNER_LINK);
                children.setName(StringUtils.capitalize(routerPath));
                children.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), menu.getPath()));
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            routers.add(router);
        }
        return routers;
    }

    /**
     * 构建前端所需要树结构
     *
     * @param menus 菜单列表
     * @return 树结构列表
     */

    public List<SysMenu> buildMenuTree(List<SysMenu> menus)
    {
        List<SysMenu> returnList = new ArrayList<SysMenu>();
        List<Long> tempList = new ArrayList<Long>();
        for (SysMenu dept : menus)
        {
            tempList.add(dept.getId());
        }
        for (Iterator<SysMenu> iterator = menus.iterator(); iterator.hasNext();)
        {
            SysMenu menu = (SysMenu) iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(menu.getParentId()))
            {
                recursionFn(menus, menu);
                returnList.add(menu);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = menus;
        }
        return returnList;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param menus 菜单列表
     * @return 下拉树结构列表
     */
      public List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menus)
    {
        List<SysMenu> menuTrees = buildMenuTree(menus);
        return menuTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 根据菜单ID查询信息
     *
     * @param menuId 菜单ID
     * @return 菜单信息
     */

    public SysMenu selectMenuById(Long menuId)
    {
        return this.baseMapper.selectById(menuId);
    }

    /**
     * 是否存在菜单子节点
     *
     * @param menuId 菜单ID
     * @return 结果
     */

    public boolean hasChildByMenuId(Long menuId)
    {
        int result = this.baseMapper.hasChildByMenuId(menuId);
        return result > 0 ? true : false;
    }

    /**
     * 查询菜单使用数量
     *
     * @param menuId 菜单ID
     * @return 结果
     */

    public boolean checkMenuExistRole(Long menuId)
    {
        int result = baseMapper.checkMenuExistRole(menuId);
        return result > 0 ? true : false;
    }

    /**
     * 新增保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */

    public int insertMenu(SysMenu menu)
    {
        return this.baseMapper.insert(menu);
    }

    /**
     * 修改保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */

    public int updateMenu(SysMenu menu)
    {

      //  return menuMapper.updateMenu(menu);
        return this.baseMapper.updateById(menu);
    }

    /**
     * 删除菜单管理信息
     *
     * @param menuId 菜单ID
     * @return 结果
     */

    public int deleteMenuById(Long menuId)
    {
        return baseMapper.deleteById(menuId);
    }

    /**
     * 校验菜单名称是否唯一
     *
     * @param menu 菜单信息
     * @return 结果
     */

    public String checkMenuNameUnique(SysMenu menu)
    {
        Long menuId = -1L;
        if(menu!=null && menu.getId()!=null) menuId=menu.getId();
        SysMenu info = baseMapper.checkMenuNameUnique(menu.getMenuName(), menu.getParentId());
        if (info !=null && !Objects.equals(info.getId(), menuId))
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 获取路由名称
     *
     * @param menu 菜单信息
     * @return 路由名称
     */
    public String getRouteName(SysMenu menu)
    {
        String routerName = StringUtils.capitalize(menu.getPath());
        // 非外链并且是一级目录（类型为目录）
        if (isMenuFrame(menu))
        {
            routerName = StringUtils.EMPTY;
        }
        return routerName;
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRouterPath(SysMenu menu)
    {
        String routerPath = menu.getPath();
        // 内链打开外网方式
        if (menu.getParentId().intValue() != 0 && isInnerLink(menu))
        {
            routerPath = StringUtils.replaceEach(routerPath, new String[] { Constants.HTTP, Constants.HTTPS }, new String[] { "", "" });
        }
        // 非外链并且是一级目录（类型为目录）
        if (0 == menu.getParentId().intValue() && UserConstants.TYPE_DIR.equals(menu.getMenuType())
                && menu.isFrame())
        {
            routerPath = "/" + menu.getPath();
        }
        // 非外链并且是一级目录（类型为菜单）
        else if (isMenuFrame(menu))
        {
            routerPath = "/";
        }
        return routerPath;
    }

    /**
     * 获取组件信息
     *
     * @param menu 菜单信息
     * @return 组件信息
     */
    public String getComponent(SysMenu menu)
    {
        String component = UserConstants.LAYOUT;
        if (StringUtils.isNotEmpty(menu.getComponent()) && !isMenuFrame(menu))
        {
            component = menu.getComponent();
        }
        else if (StringUtils.isEmpty(menu.getComponent()) && menu.getParentId().intValue() != 0 && isInnerLink(menu))
        {
            component = UserConstants.INNER_LINK;
        }
        else if (StringUtils.isEmpty(menu.getComponent()) && isParentView(menu))
        {
            component = UserConstants.PARENT_VIEW;
        }
        return component;
    }

    /**
     * 是否为菜单内部跳转
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isMenuFrame(SysMenu menu)
    {
        return menu.getParentId().intValue() == 0 && UserConstants.TYPE_MENU.equals(menu.getMenuType())
                && !menu.isFrame();
    }

    /**
     * 是否为parent_view组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isParentView(SysMenu menu)
    {
        return menu.getParentId().intValue() != 0 && UserConstants.TYPE_DIR.equals(menu.getMenuType());
    }

    /**
     * 是否为内链组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isInnerLink(SysMenu menu)
    {
        return !menu.isFrame() && menu.getPath().contains("http");
    }

    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list 分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    public List<SysMenu> getChildPerms(List<SysMenu> list, int parentId)
    {
        List<SysMenu> returnList = new ArrayList<SysMenu>();
        for (Iterator<SysMenu> iterator = list.iterator(); iterator.hasNext();)
        {
            SysMenu t = (SysMenu) iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId() == parentId)
            {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private void recursionFn(List<SysMenu> list, SysMenu t)
    {
        // 得到子节点列表
        List<SysMenu> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysMenu tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysMenu> getChildList(List<SysMenu> list, SysMenu t)
    {
        List<SysMenu> tlist = new ArrayList<SysMenu>();
        Iterator<SysMenu> it = list.iterator();
        while (it.hasNext())
        {
            SysMenu n = (SysMenu) it.next();
            if (n.getParentId() == t.getId())
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysMenu> list, SysMenu t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }

    /*******************************************************************************************************************/
    public List<String> getPartyMenuPermissions(String partyId){
        return this.baseMapper.getPartyMenuPermissions(partyId);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
