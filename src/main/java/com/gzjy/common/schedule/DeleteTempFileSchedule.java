package com.gzjy.common.schedule;

import java.io.File;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DeleteTempFileSchedule {
	
	@Scheduled(cron="0 0 1 * * *")
//	@Scheduled(cron="*/5 * * * * *")	
    public void DeleteFile(){
		System.out.println("--->定时删除Temp目录下文件");
		String filePath = "var/lib/docs/gzjy/temp";
		File file = new File(filePath);
		if (file.isDirectory()) {
            String[] children = file.list();
            for (int i=0; i<children.length; i++) {
                boolean success = (new File(filePath, children[i])).delete();
                if (success) {
                	System.out.println("删除文件:"+filePath+"/"+children[i]+"OK");
                }else {
                	System.out.println("删除文件:"+filePath+"/"+children[i]+"ERROR");
                }
            }
        }        
    }
}
