package com.gzjy.oauth;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gzjy.common.Response;
import com.gzjy.common.rest.EpcRestTemplate;
import com.gzjy.oauth.security.EpicKaptchaReceiver;
import com.gzjy.user.model.Kaptchas;

public class KaptchaReceiver implements EpicKaptchaReceiver {
  @Override
  public Kaptchas getKaptchas(String id) {
    EpcRestTemplate template = new EpcRestTemplate();
    Map<String, Object> vars = new HashMap<>();
    vars.put("id", id);
    Type type = new TypeReference<Response<Kaptchas>>() {}.getType();
    Response<Kaptchas> rsp = template.exchangeParams("http://localhost:8001/v1/user/kaptcha/response", HttpMethod.GET, type, vars);
    if (!rsp.isSuccess()) 
      return null;
    return rsp.getEntity();
  }
}
