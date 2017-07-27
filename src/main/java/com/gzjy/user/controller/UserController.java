package com.gzjy.user.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gzjy.user.mapper.UserMapper;
import com.gzjy.user.model.User;

@RestController
@RequestMapping({"/home"})
public class UserController {
@Autowired
UserMapper userMapper;
@RequestMapping(value = "/user")
@ResponseBody
public String user(){
User user = userMapper.selectByPrimaryKey("xue");
return user.getUsername()+"-----";
}
}