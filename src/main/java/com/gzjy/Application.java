package com.gzjy;

import java.util.Arrays;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.gzjy.interceptor.FrUrlInterceptor;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@EnableAsync
@EnableScheduling
@EnableTransactionManagement
@ComponentScan
@ServletComponentScan
public class Application  {
    private static final String TYPE_ALIASES_PACKAGE = "com.gzjy.*.model";
    private static final String MAPPER_LOCATION = "classpath:/mybatis/*.xml";
 
    @Bean
    @Autowired 
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception   { 
     final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean(); 
    sqlSessionFactoryBean.setDataSource(dataSource); 
    //mybatis.typeAliasesPackage：指定domain类的基包，即指定其在*Mapper.xml文件中可以使用简名来代替全类名（看后边的UserMapper.xml介绍） 
    sqlSessionFactoryBean.setTypeAliasesPackage(TYPE_ALIASES_PACKAGE); 
    /* 
     * mybatis.mapperLocations：指定*Mapper.xml的位置
     *  如果不加会报org.apache.ibatis.binding.BindingException: Invalid bound statement (not found): com.blog.mapper.MessageMapper.findMessageInfo异常
     *   因为找不到*Mapper.xml，也就无法映射mapper中的接口方法。 
    */ 
    
    sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION)); 
    return sqlSessionFactoryBean.getObject();
    }
    
    @Configuration
    static class WebConfig extends WebMvcConfigurerAdapter {
    @Autowired
    FrUrlInterceptor frUrlInterceptor;

        /**
         * 配置拦截器
         * 
         * @author lance
         * @param registry
         */
        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(frUrlInterceptor).addPathPatterns("/v1/ahgz/**");
        }
    }
    

	public static void main(String[] args) throws Exception {
		ApplicationContext ctx = SpringApplication.run(Application.class, args);
		
		//仅在测试环境中使用
        System.out.println("通过Spring Boot启动了Http Server，以下是Spring Boot扫描的Bean列表：");
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
             System.out.println("found bean -> " + beanName);
        }
		
	}


	
   /* @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:mysql://10.133.0.242:3306/workflow2?characterEncoding=UTF-8")
                .username("admin")
                .password("1qaz2wsx")
                .driverClassName("com.mysql.jdbc.Driver")
                .build();
    }*/
}
