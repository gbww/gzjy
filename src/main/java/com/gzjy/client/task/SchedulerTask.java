/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年12月4日
 */
package com.gzjy.client.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gzjy.client.model.ClientScheduler;
import com.gzjy.client.service.ClientSchedulerService;

/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年12月4日
 */
@Component
public class SchedulerTask {
    @Autowired
    private ClientSchedulerService schedulerService;

    @Scheduled(cron = "0 0 0/2 * * ?") // 每隔两小时执行一次
    protected void execute() {
        System.out.println("定时任务开始执行");
        //reminder();

    }

    public void reminder() {
        Date nowtime=new Date();
        Map<String,Object>filter=new HashMap<String,Object>();
        filter.put("effective", 1);
        List<ClientScheduler> records=schedulerService.selectAll(filter);
        if(records.size()>0) {
            for(ClientScheduler record:records) {
                record.getSchedulerRepetition();
           
        }
        }
    }

}
