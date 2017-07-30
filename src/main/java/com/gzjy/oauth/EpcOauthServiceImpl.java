package com.gzjy.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gzjy.common.exception.BizException;
import com.gzjy.oauth.entity.OauthUser;
import com.gzjy.oauth.user.EpcOauthUserService;
import com.gzjy.user.mapper.UserMapper;
import com.gzjy.user.model.User;


@Service
public class EpcOauthServiceImpl implements EpcOauthUserService {
  @Autowired
  private UserMapper userMapper;
  @Override
  public OauthUser getUser(String userName) {
    User user = userMapper.selectByUsername(userName);
    if (user == null) {
      throw new BizException(String.format("账号信息错误", userName));
    }
    if (user.getState() == User.UserStatus.SUSPEND.getCode()){
      throw new BizException(String.format("User: %s 已经被冻结，请联系管理员", userName));
    }
    OauthUser oauthUser = new OauthUser(user.getName(), user.getUsername());
    oauthUser.setPassword(user.getPassword());
    return oauthUser;
  }
}

