/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年12月4日
 */
package com.gzjy.client.task;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gzjy.client.model.ClientScheduler;
import com.gzjy.client.model.SchedulerRemind;
import com.gzjy.client.model.SchedulerRepetition;
import com.gzjy.client.service.ClientSchedulerService;

/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年12月4日
 */
@Component
public class SchedulerTask {
    @Autowired
    private ClientSchedulerService schedulerService;

  //  @Scheduled(cron = "0 0 0/1 * * ?") // 每隔两小时执行一次
    protected void execute() {
        System.out.println("定时任务开始执行");
        //reminder();

    }

    public void executeNotify() {
        Date nowtime=new Date();
        Map<String,Object>filter=new HashMap<String,Object>();
        filter.put("effective", 1);
        List<ClientScheduler> records=schedulerService.selectAll(filter);
        if(records.size()>0) {
            for(ClientScheduler record:records) {
                
                if(!checkEffective(record)) {   //判断无效
                    schedulerService.setEffective(0, record.getId());
                    continue;
                }
                else {           //有效
                    SchedulerRepetition enumRepetition=getEnum_repetition( record);
                 
                }
                
           
                
        }
        }
    }
    
    
    public Boolean checkEffective(ClientScheduler record) {
        Date nowtime =new Date();
        String repetition=record.getSchedulerRepetition();
        if(repetition.equals(SchedulerRepetition.NONE.getCode())) {    //不重复情况
            if(nowtime.after(record.getSchedulerEndTime()))//当前时间在日志结束时间之后
               
                return false;
            else
                return true;
           
        }
        else
            return true;
    }
    
    public  SchedulerRepetition  getEnum_repetition(ClientScheduler record) {
        SchedulerRepetition type = null;
        for (SchedulerRepetition ticketType : SchedulerRepetition.values()) {
          if (ticketType.getCode() == Integer.parseInt(record.getSchedulerRepetition())) {
            type = ticketType;
            break;
          }
        }
        return type;
        
    }
    
    public  void   setReminder(SchedulerRepetition enumRepetition,ClientScheduler record) {
        Date nowtime=new Date();
        Calendar nowCal=Calendar.getInstance();
        nowCal.setTime(nowtime);
        int nowDayOfMonth=nowCal.get(Calendar.DAY_OF_MONTH);
        int nowDayOfYear=nowCal.get(Calendar.DAY_OF_YEAR);
        int nowHours=nowCal.get(Calendar.HOUR_OF_DAY);
        
        Calendar cal=Calendar.getInstance();
        cal.setTime(record.getSchedulerStartTime());
        int dayOfMonth=cal.get(Calendar.DAY_OF_MONTH);
        int dayOfYear=cal.get(Calendar.DAY_OF_YEAR);
        int hours=cal.get(Calendar.HOUR_OF_DAY);
        switch (enumRepetition) {
        case EVERYDAY:
           
            if(nowHours+1==hours) {
                //发动推送提醒功能；
            }
            break;
        case EVERYWEEK:
           if((nowDayOfYear%7==dayOfYear%7)&&(nowHours+1==hours)) {
             //发动推送提醒功能；
           }
            break;
        case EVERYMONTH:
            if((nowDayOfMonth==dayOfMonth)&&(nowHours+1==hours)) {
                //发动推送提醒功能；
              }
            break;
        

        default:
            break;
        }

        
    }
    
    
   
    
    

}
