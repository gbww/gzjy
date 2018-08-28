package com.gzjy.log.constant;

import java.util.HashMap;
import java.util.List;

import com.gzjy.log.model.LogTag;
import com.gzjy.log.service.LogTagService;
import com.gzjy.log.service.impl.LogTagServiceImpl;

public class LogTagConstant {	
	
	private LogTagConstant() {}
	
    private static HashMap<String, String> logTagConstant=null;  
    
    public static HashMap<String, String> getInstance() {  
         if (logTagConstant == null) { 
        	 LogTagService logTagService = new LogTagServiceImpl();
        	 List<LogTag> datas = logTagService.selectAll();
        	 for(LogTag data:datas) {
        		 logTagConstant.put(data.getOperation(), data.getTag());
        	 }             
         }    
        return logTagConstant;  
    }  
}
