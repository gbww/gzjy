package com.gzjy.oauth.user;

import java.security.Principal;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.endpoint.CheckTokenEndpoint;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.gzjy.common.Response;
import com.gzjy.common.exception.BizException;
import com.gzjy.common.rest.EpcSyncRestTemplate;
import com.gzjy.oauth.config.OauthInitBeanCollection;
import com.gzjy.oauth.entity.OauthUser;
import com.gzjy.oauth.property.EpcOauthProperties;
import com.gzjy.oauth.security.Decoder;
import com.gzjy.oauth.security.KaptchaReceiver;

@RestController
public class OauthUserController {
  private Logger logger = LoggerFactory.getLogger(OauthUserController.class);
  @Autowired(required = true)
  private EpcOauthUserService userService;
  @Autowired(required = false)
  private EpcOauthProperties oauthProperties;
  @Autowired(required = false)
  private DefaultTokenServices defaultTokenServices;
  // 根据principal获取用户信息，这里只返回用户名等
  @Autowired
  private TokenEndpoint tokenEndpoint;
  @Autowired
  private CheckTokenEndpoint checkTokenEndpoint;

  @Value("${user.oauth_token}")
  private String oauthTokenUrl;
  @Value("${kaptcha.enable}")
  private String kaptchaEnable;
  @Value("${kaptcha.receive.url}")
  private String kaptchaReceiveUrl;

  private boolean isKaptchaEnabled() {
    return kaptchaEnable.equals("0") ? false : true;
  }



  @RequestMapping(method = RequestMethod.GET, value = "oauth/user")
  public Response<?> queryUser(Principal principal) {
    logger.debug("返回Principal的queryUser方法被调用");
    OauthUser user = userService.getUser(principal.getName());
    user.setPassword(null);
    return Response.success(user);
  }

  @RequestMapping(method = RequestMethod.POST, value = "user/login")
  public Response<?> login(@RequestParam Map<String, String> parameters) {
    logger.info("用户登录");
    String id = parameters.get("id");
    if (id == null) {
      throw new BizException("非法请求！");
    }
    if (isKaptchaEnabled()) {
      String kaptchaTxt = parameters.get("kaptcha");
      if (kaptchaTxt == null) return Response.fail("请输入有效验证码");
      try {
        KaptchaReceiver<?> kaptchaReceiver = null;
        if (oauthProperties.getKaptchaReceiver() != null) {
          kaptchaReceiver = getKapthcaReceiver();
        }

        // kaptchaReceiver.getKaptcha(id, kaptchaReceiveUrl);
        kaptchaReceiver.checkKaptcha(id, kaptchaReceiveUrl, kaptchaTxt);
      } catch (Exception e) {
        throw e;
      }
    }
    // return getToken(principal, parameters);
    Map<String, Object> params = new HashMap<>();
    params.put("username", parameters.get("username"));
    params.put("password", parameters.get("password"));
    params.put("grant_type", "password");
    params.put("scope", parameters.get("id"));
    EpcSyncRestTemplate restTemplate = new EpcSyncRestTemplate();
    String client = Base64.getEncoder().encodeToString("CLIENT:SECRET".getBytes());
    Map<String, String> header = Maps.newHashMap();
    ParameterizedTypeReference<Response<OAuth2AccessToken>> type =
        new ParameterizedTypeReference<Response<OAuth2AccessToken>>() {};
    header.put("Authorization", "Basic " + client);
    Response<OAuth2AccessToken> tokenResult =
        restTemplate.post(oauthTokenUrl, type, params, header);
    return tokenResult;
  }

  @RequestMapping(method = RequestMethod.POST, value = "oauth/token")
  public Response<?> getToken(Principal principal, @RequestParam Map<String, String> parameters) {
    logger.debug("获取用户token");
    ResponseEntity<OAuth2AccessToken> accessToken = null;
    try {
      if (oauthProperties.getDecoder() != null) {
        preproccessLoginInfo(parameters);
      }
      accessToken = tokenEndpoint.postAccessToken(principal, parameters);
    } catch (HttpRequestMethodNotSupportedException e) {
      logger.error(e.getMessage(), e);
      return Response.fail(e.getMessage());
    } catch (Exception e) {
      if (e.getMessage().equals("Bad credentials")) return Response.fail("账号信息错误");
      throw e;
    }

    if (accessToken.getStatusCode().equals(HttpStatus.OK)) {
      OAuth2AccessToken body = accessToken.getBody();
      return Response.success(body);
    } else if (accessToken.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
      return Response.fail("账号信息错误");
    } else {
      return Response.fail("账号信息错误");
    }
  }

  @RequestMapping(value = "oauth/check_token")
  public Map<String, ?> checkToken(@RequestParam("token") String value, HttpServletRequest rsq,
      HttpServletResponse rsp) {
    logger.debug("check oauth token");
    Map<String, ?> checkToken = null;
    try {
      checkToken = checkTokenEndpoint.checkToken(value);
    } catch (Exception e) {
      // throw new BizException("error token fail");
//      ((Map<String, Object>)checkToken).put("client_id", "CLIENT");
//      Map<String, Object> map = Maps.newLinkedHashMap();
//      map.put("client_id", "CLIENT");
//      map.put("message", "Invalid Token!");
//      logger.debug(e.getMessage());
//      return map;
      throw e;
    }
    return checkToken;
  }

//  @ExceptionHandler(InvalidTokenException.class)
  public ResponseEntity<OAuth2Exception> handleException(Exception e) throws Exception {
    WebResponseExceptionTranslator exceptionTranslator =
        new DefaultWebResponseExceptionTranslator();
    logger.debug("Handling error: " + e.getClass().getSimpleName() + ", " + e.getMessage());
    // This isn't an oauth resource, so we don't want to send an
    // unauthorized code here. The client has already authenticated
    // successfully with basic auth and should just
    // get back the invalid token error.
    @SuppressWarnings("serial")
    InvalidTokenException e400 = new InvalidTokenException(e.getMessage()) {
      
      @Override
      public int getHttpErrorCode() {
        return 200;
      }
    };
    return exceptionTranslator.translate(e400);
  }

  @RequestMapping(method = RequestMethod.PUT, value = "oauth/user/logout")
  public boolean logout(HttpServletRequest request) {
    logger.debug("用户注销登陆");
    boolean b = defaultTokenServices
        .revokeToken(request.getHeader("Authorization").replaceAll("^bearer \\s*?", ""));
    return b;
  }

  private void preproccessLoginInfo(Map<String, String> map) {
    String username = map.get("username");
    String password = map.get("password");
    String scope = map.get("scope");
    if (scope == null) map.put("scope", "cmss");
    Decoder decoder = getDecoder();
    map.put("username", decoder.decode(username));
    map.put("password", decoder.decode(password));
  }

  private Decoder loginDecoder;

  private KaptchaReceiver<?> kaptchaReceiver;

  private KaptchaReceiver<?> getKapthcaReceiver() {
    if (kaptchaReceiver != null) {
      return kaptchaReceiver;
    }
    return (KaptchaReceiver<?>) OauthInitBeanCollection
        .getBean(oauthProperties.getKaptchaReceiver());
  }

  private Decoder getDecoder() {
    if (loginDecoder != null) {
      return loginDecoder;
    }
    return (Decoder) OauthInitBeanCollection.getBean(oauthProperties.getDecoder());
  }
}
