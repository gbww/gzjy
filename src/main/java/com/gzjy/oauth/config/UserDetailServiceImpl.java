package com.gzjy.oauth.config;


import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.gzjy.oauth.entity.OauthUser;
import com.gzjy.oauth.user.EpcOauthUserService;
import com.gzjy.oauth.util.SpringContenxtHelper;

public class UserDetailServiceImpl implements UserDetailsService {
  private static Logger logger = LoggerFactory.getLogger(UserDetailServiceImpl.class);
  private EpcOauthUserService oauthUserService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    /*if (oauthUserService == null) {
      oauthUserService =
          (EpcOauthUserService) SpringContenxtHelper.getBean(EpcOauthUserService.class);
      logger.debug("设置UserDetailServiceIMPl中，EPCOauthService值{}", oauthUserService);
    }
    OauthUser user = oauthUserService.getUser(username);

    if (user == null) {
      throw new UsernameNotFoundException("账号信息错误");
    }
    org.springframework.security.core.userdetails.User userDetails =
        new org.springframework.security.core.userdetails.User(user.getUsername(),
            user.getPassword(), Collections.emptyList());
    logger.debug("用户信息是{}", user);*/
      org.springframework.security.core.userdetails.User userDetails =
              new org.springframework.security.core.userdetails.User("epic",
                  "epic1234", Collections.emptyList());
    return userDetails;
  }


}
