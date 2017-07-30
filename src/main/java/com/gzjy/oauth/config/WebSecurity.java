package com.gzjy.oauth.config;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.gzjy.common.util.ReflectUtil;
import com.gzjy.oauth.condition.TokenStoreJdbcCondition;
import com.gzjy.oauth.property.EpcOauthProperties;
import com.gzjy.oauth.security.DefaultPasswordEncoder;
import com.gzjy.oauth.security.Encoder;



@Configuration
@EnableWebSecurity
@EnableAuthorizationServer
@EnableResourceServer
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@EnableConfigurationProperties(EpcOauthProperties.class)
@EnableTransactionManagement
public class WebSecurity extends WebSecurityConfigurerAdapter {
  private static Logger logger = LoggerFactory.getLogger(WebSecurity.class);
  @Autowired
  private EpcOauthProperties oauthProperties;

  @PostConstruct
  public void init() {
    ReflectUtil<Encoder> reflectUtil = new ReflectUtil<>();
    String passwordEncoder = oauthProperties.getEncoder();
    if (passwordEncoder != null) {
      Encoder instance = reflectUtil.getInstance(passwordEncoder);

      OauthInitBeanCollection.add(passwordEncoder, instance);
    }
    String loginDecoder = oauthProperties.getDecoder();
    if (loginDecoder != null) {
      OauthInitBeanCollection.add(loginDecoder, reflectUtil.getInstance(loginDecoder));
    }
    String kaptchaReceiver = oauthProperties.getKaptchaReceiver();
    if (kaptchaReceiver != null) {
      OauthInitBeanCollection.add(kaptchaReceiver, reflectUtil.getInstance(kaptchaReceiver));
    }
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    UserDetailServiceImpl userDetailService = new UserDetailServiceImpl();

    DaoAuthenticationConfigurer<AuthenticationManagerBuilder, UserDetailServiceImpl> configurer =
        auth.userDetailsService(userDetailService);
    if (oauthProperties.getEncoder() != null) {

      DefaultPasswordEncoder passwordEncoder = new DefaultPasswordEncoder();
      passwordEncoder.setPasswordEncoder(
          (Encoder) OauthInitBeanCollection.getBean(oauthProperties.getEncoder()));
      configurer.passwordEncoder(passwordEncoder);

    }
  }

  @Override
  public void configure(org.springframework.security.config.annotation.web.builders.WebSecurity web)
      throws Exception {
    org.springframework.security.config.annotation.web.builders.WebSecurity.IgnoredRequestConfigurer ignoring =
        web.ignoring();
    if (oauthProperties.getIgnorePaths() != null) {
      ignoring.antMatchers(oauthProperties.getIgnorePaths());
    }
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.antMatcher("/**").authorizeRequests()
        .anyRequest().authenticated();
  }



  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  @Conditional(TokenStoreJdbcCondition.class)
  public TokenStore tokenStore(DataSource dataSource) {
    logger.info("加载token数据库存储");
    return new JdbcTokenStore(dataSource);
  }

}
