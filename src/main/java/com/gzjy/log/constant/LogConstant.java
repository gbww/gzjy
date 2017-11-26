package com.gzjy.log.constant;

public enum LogConstant {
	
	//系统登陆
    SYSTEM_LOGIN("00"), 
    //系统登出
    SYSTEM_LOGIN_OUT("01"),   
    //添加用户
    USER_ADD("11"),  
    //删除用户
    USER_DELETE("12"),    
    //修改用户
    USER_UPDATE("13"),    
    //查询单个用户
    USER_SEARCH("14"),
    //合同录入
	CONTRACT_INPUT("21"),
	//合同评审
	CONTRACT_REVIEW("22"),
	
	CONTRACT_DELETE("23"),
	
	CONTRACT_UPDATE("24"),
	//合同审批
	CONTRACT_APPROVE("25");
	

    private final String value;

    LogConstant(String value) {
        this.value = value;
    }
   
    public String getValue() {
        return this.value;
    }

}
