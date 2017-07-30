package com.gzjy.privilege;

import java.lang.reflect.Method;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gzjy.common.annotation.Privileges;
import com.gzjy.common.exception.BizException;
import com.gzjy.privilege.model.Privilege;
import com.gzjy.privilege.model.Privilege.PrivilegeScope;
import com.gzjy.role.RoleService;
import com.gzjy.role.model.Role;
import com.gzjy.role.model.Role.RoleIdentity;
import com.gzjy.user.UserService;


@Aspect
@Component
public class PrivilegeProcessor {
  @Autowired
  private RoleService roleClient;

  @Autowired
  private UserService userClient;

  @Pointcut("@annotation(com.gzjy.common.annotation.Privileges)")
  public void privilegeAnotation() {}

  @Around(value = "privilegeAnotation()")
  public Object process(ProceedingJoinPoint joinPoint) throws Throwable {
    Object result = null;
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();

    Privileges privileges = method.getAnnotation(Privileges.class);
    if (!privileges.name().equals("NULL") && PrivilegeScope.getPrivilegeScope(privileges.scope()[0]) == null) {
      throw new BizException("权限名称存在的情况下必须指定准确权限类型");
    }
    Role role = userClient.getCurrentUser().getRole();
    for (RoleIdentity identity : privileges.identity()) {
      if (identity == role.getRoleIdentity()) {
        result = joinPoint.proceed();
        return result;
      }
    }
    List<Privilege> list = roleClient.getPrivileges(role.getId(), null);
    Privilege p = null;
    int[] scopes = privileges.scope();
    for (int scope : scopes) {
      p = new Privilege();
      p.setName(privileges.name() + "-" + scope);
      p.setScope(scope);
      if (list.contains(p)) {
        result = joinPoint.proceed();
        return result;
      }
    }
    throw new BizException("当前用户没有权限，请联系管理员");
  }
}
