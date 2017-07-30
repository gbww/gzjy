
package com.gzjy.oauth.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import com.gzjy.oauth.condition.CorsCondition;

@Component
@Conditional(CorsCondition.class)
public class CorsFilter implements Filter {
  private static final Logger logger = LoggerFactory.getLogger(CorsFilter.class);
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletResponse req = (HttpServletResponse) response;
    req.setHeader("Access-Control-Allow-Origin", "*");
    req.setHeader("Access-Control-Expose-Headers", "id");
    req.setHeader("Access-Control-Allow-Methods", "*");
    //"GET", "HEAD", "POST", "PUT", "PATCH", "DELETE", "TRACE"
    req.setHeader("Access-Control-Max-Age", "3600");
    req.setHeader("Access-Control-Allow-Headers",
        "content-type,access-control-request-headers,access-control-request-method,accept,origin,authorization,x-requested-with");
    chain.doFilter(request, response);
  }

  public void init(FilterConfig filterConfig) {
    logger.info("加载允许跨域调用配置");
  }

  public void destroy() {
    logger.info("销毁允许跨域调用配置");
  }

}

