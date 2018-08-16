/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2018年8月15日
 */
package com.gzjy.requestlimit;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.gzjy.common.annotation.Privileges;
import com.gzjy.common.annotation.RequestLimit;
import com.gzjy.common.exception.BizException;

/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2018年8月15日
 */
@Aspect
@Component
public class RequestLimitProcessor {
    private static Logger logger = LoggerFactory.getLogger(RequestLimitProcessor.class);
    private static Map<String,Integer> ipCountTemplate=new HashMap<>();
    @Pointcut("@annotation(com.gzjy.common.annotation.RequestLimit)")
    public void limitAnotation() {}
    @Around(value="limitAnotation()")
    public Object process(final JoinPoint joinPoint)throws BizException{
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RequestLimit limit =method.getAnnotation(RequestLimit.class);
        Object[] args=joinPoint.getArgs();
        HttpServletRequest request = null;
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof HttpServletRequest) {
                request = (HttpServletRequest) args[i];
                break;
            }
        }
        if (request == null) {
            throw new BizException("方法中缺失HttpServletRequest参数");
        }
        String ip = request.getLocalAddr();
        String url = request.getRequestURL().toString();
        String key = "req_limit_".concat(url).concat(ip);
       
        if (ipCountTemplate.get(key) == null || ipCountTemplate.get(key) == 0) {
            ipCountTemplate.put(key, 1);
        } else {
            ipCountTemplate.put(key, ipCountTemplate.get(key) + 1);
        }
        int count = ipCountTemplate.get(key);
        if (count > 0) {
            //创建一个定时器
            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    ipCountTemplate.remove(key);
                }
            };
            //这个定时器设定在time规定的时间之后会执行上面的remove方法，也就是说在这个时间后它可以重新访问
            timer.schedule(timerTask, limit.time());
        }
        logger.info("用户IP[" + ip + "]访问地址[" + url + "]访问次数[" +count + "]");
        if (count > limit.count()) {
            logger.info("用户IP[" + ip + "]访问地址[" + url + "]超过了限定的次数[" + limit.count() + "]");
            throw new BizException("too many count");
        }
        
        return null;
    }
    
    
    

}
