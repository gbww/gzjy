/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年12月4日
 */
package com.gzjy.client.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年12月4日
 */
@Component
public class SchedulerTask {
    
    @Scheduled(cron = "0 0 0/5 * * ?")
    protected void execute() {
        System.out.println("定时任务开始执行");
        //gather();

    }

}
