package com.gzjy.oauth.user;

import com.gzjy.oauth.entity.OauthUser;

public interface EpcOauthUserService {
  OauthUser getUser(String userName);
}
