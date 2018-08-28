package com.gzjy.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.gzjy.role.model.Role.RoleIdentity;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
/**
 * 权限注解
 * 
 *
 */
public @interface Privileges {
  /**
   * 权限名称
   * @return 权限
   */
  String name() default "NULL";
  
  int[] scope() default {0};
  
  RoleIdentity[] identity() default {RoleIdentity.SUPER_ADMIN};
}
