package com.gzjy.oauth.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.gzjy.oauth.condition.CorsCondition;

/**
 * 必须在启动类中加入注解@ServletComponentScan
 *
 * @author shizhongtao
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
@Conditional(CorsCondition.class)
public class AuthenticationFilter implements Filter {
  private static final Logger logger =
      org.slf4j.LoggerFactory.getLogger(AuthenticationFilter.class);

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    HttpServletResponse response = (HttpServletResponse) res;
    HttpServletRequest request = (HttpServletRequest) req;
    if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
      response.setHeader("Access-Control-Allow-Origin", "*");
      response.setHeader("Access-Control-Expose-Headers", "id");
      // POST,GET,PUT,DELETE
      response.setHeader("Access-Control-Allow-Methods", "GET,HEAD,POST,PUT,PATCH,DELETE,TRACE");
      response.setHeader("Access-Control-Max-Age", "3600");
      response.setHeader("Access-Control-Allow-Headers",
          "content-type,access-control-request-headers,access-control-request-method,accept,origin,Authorization,x-requested-with");
      response.setStatus(HttpServletResponse.SC_OK);
    } else {
      chain.doFilter(req, res);
    }
  }

  @Override
  public void init(FilterConfig arg0) throws ServletException {
    logger.info("加载允许Oauth2跨域调用配置");
  }

  @Override
  public void destroy() {}

}
