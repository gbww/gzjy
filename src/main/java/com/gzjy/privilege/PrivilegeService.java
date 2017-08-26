package com.gzjy.privilege;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzjy.common.annotation.Privileges;
import com.gzjy.common.exception.BizException;
import com.gzjy.privilege.mapper.PrivilegeMapper;
import com.gzjy.privilege.model.Privilege;
import com.gzjy.role.RolePrivilegeService;
import com.gzjy.role.model.CrabRole;
import com.gzjy.role.model.Role.RoleIdentity;
import com.gzjy.user.UserService;

@Service
@Transactional
public class PrivilegeService {
  @Autowired
  private PrivilegeMapper privilegeMapper;
  @Autowired
  private UserService userService;
  @Autowired
  private RolePrivilegeService rolePrivilegeService;
  
 
  /**
   * 检查权限是否存在
   * 
   * @param id ：权限id
   * @return 权限
   */
  public Privilege check(String id) {
    Privilege p = privilegeMapper.selectById(id);
    if (p == null) {
      throw new BizException("不存在该权限！");
    }
    return p;
  }

  /**
   * 更新权限
   * 
   * @param id ：权限id
   * @param privilege ：权限
   * @return 更新权限
   */
  @Transactional
  public Privilege update(String id, Privilege privilege) {
    Privilege p = check(id);
    int privilegeScope = p.getScope();
    p.setName(null);
    p.setDescription(privilege.getDescription());
    privilegeMapper.updateByIdSelective(p);
    return p;
  }

  /**
   * 获取权限
   * 
   * @param id ：权限id
   * @return
   */
  public Privilege getById(String id) {
    Privilege p = privilegeMapper.selectById(id);
    return p;
  }
  
  public List<Privilege> getViewPrivileges(String category, int scope) {
    return privilegeMapper.selectViewPrivilegesByCategoryAndScope(category, scope);
  }
  
  /**
   * 获取对应类型的管理员权限列表
   * @param category ：权限资源分类
   * @param scope ：管理员类型
   * @return
   */
  public List<Privilege> getPrivilegesByAdmin(String category, int scope) {
    return privilegeMapper.selectByCategoryAndScope(category, scope);
  }
  
  /**
   * 获取当前用户的权限列表
   * @param category ：权限分类
   * @return
   */
  public List<Privilege> getPrivileges(String category) {
    CrabRole role = userService.getCurrentUser().getRole();
    if (!role.isSuperAdmin() && !role.isAnHuiAdmin()) {
      return rolePrivilegeService.getCategoryPrivilegesByRoleId(role.getId(), category);
    }
    return getPrivilegesByAdmin(category, role.getScope());
  }

  /**
   * 获取当前用户的权限列表
   * @param category ：权限分类
   * @param pageNum ：分页页码
   * @param pageSize ：每页大小
   * @return
   */
  public PageInfo<Privilege> getPrivileges(String category, Integer pageNum, Integer pageSize) {
    CrabRole role = userService.getCurrentUser().getRole();
    PageInfo<Privilege> pages = null;
    if (!role.isSuperAdmin() && !role.isAnHuiAdmin()) {
      pages = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(new ISelect() {
        @Override
        public void doSelect() {
            rolePrivilegeService.getCategoryPrivilegesByRoleId(role.getId(), category);
        }
      });
      return pages;
    }
    pages = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(new ISelect() {
      @Override
      public void doSelect() {
        getPrivilegesByAdmin(category, role.getScope());
      }
    });
    return pages;
  }
  
  public Map<String, String> getCategories() {
    Map<String, String> categroys = new HashMap<>();
    for (Privilege.Category cgy : Privilege.Category.values())  {
      categroys.put(cgy.getName(), cgy.getDescription());
    }
    return categroys;
  }
  
  public Map<String, String[]> getCategoriesWithCapitals() {
    Map<String, String[]> categroys = new HashMap<>();
    for (Privilege.CategoryWithCapitals cgy : Privilege.CategoryWithCapitals.values())  {
      categroys.put(cgy.getName(), cgy.getDetails());
    }
    return categroys;
  }
}
