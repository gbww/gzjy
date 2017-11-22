package com.gzjy.log.constant;

public enum LogConstant {
	
	//系统登陆
    SYSTEM_LOGIN("0"), 
    //系统登出
    SYSTEM_LOGIN_OUT("1"),   
    //添加用户
    USER_ADD("2"),  
  //删除用户
    USER_DELETE("3"),    
  //修改用户
    USER_UPDATE("4"),    
  //查询单个用户
    USER_SEARCH("5");   


    private final String value;

    LogConstant(String value) {
        this.value = value;
    }
   
    public String getValue() {
        return this.value;
    }

}
