package com.gzjy.log.constant;

public enum LogConstant {
	
	//系统登陆
    SYSTEM_LOGIN("00", "系统登陆"), 
    //系统登出
    SYSTEM_LOGIN_OUT("01", "系统登出"),   
    //添加用户
    USER_ADD("11", "添加用户"),  
    //删除用户
    USER_DELETE("12", "删除用户"),    
    //修改用户
    USER_UPDATE("13", "修改用户"),    
    //查询单个用户
    USER_SEARCH("14","查询单个用户"),
    //合同录入
	CONTRACT_INPUT("21","合同录入"),
	//合同评审
	CONTRACT_REVIEW("22","合同评审"),
	//合同删除
	CONTRACT_DELETE("23","合同删除"),
	//合同修改
	CONTRACT_UPDATE("24","合同修改"),
	//合同审批
	CONTRACT_APPROVE("25","合同审批");
	

    private String code;
    
    private String name;
    LogConstant(String code,String name) {
        this.code = code;
        this.name = name;
    }
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    

}
