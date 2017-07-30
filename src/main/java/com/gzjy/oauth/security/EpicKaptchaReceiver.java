package com.gzjy.oauth.security;

import com.gzjy.user.model.Kaptchas;

/**
 * Created by yangxiaoming@cmss.chinamobile.com on 2017/1/11.
 *
 */
public interface EpicKaptchaReceiver {
  Kaptchas getKaptchas(String id);
}
