package com.gzjy.user.controller;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gzjy.common.Response;
import com.gzjy.user.UserService;
import com.gzjy.user.UserSignService;
import com.gzjy.user.model.UserSign;


@RestController
@RequestMapping(value = "v1")
public class SignController {
 /* @Autowired
  private OauthUserController oauthUserController;*/
  @Autowired
  private UserService userService;
  @Autowired
  private UserSignService signService;
  private final ResourceLoader resourceLoader; 
  @Autowired
  public SignController(ResourceLoader resourceLoader) { 
  this.resourceLoader = resourceLoader; 
  }
  
/*  @RequestMapping(value = "/user/sign/show",method = RequestMethod.GET) 
  @ResponseBody
  public ResponseEntity<?> showSign() { 
      String path=signService.getPathByCurrentUser();
      try { 
       return ResponseEntity.ok(resourceLoader.getResource(path).toString()); 
      } catch (Exception e) { 
       return ResponseEntity.notFound().build(); 
      } 
      } */
  
  

//获取用户电子签名的记录
 /* @RequestMapping(value = "/user/sign", method = RequestMethod.GET)
  public Response get(@RequestParam(name="id") String id) {
   
    UserSign sign = signService.getUserSign(id);
    return Response.success(sign);
  }*/
  
  /**
   * 上传电子签名
   * @param file
   * @return
   */
  @RequestMapping(value="/user/sign/upload", method = RequestMethod.POST)
  public Response uploadFile(@RequestParam("file") MultipartFile file) {
      //保存上传文件
      UserSign signFile=new UserSign();
      try {
          signFile = signService.upload(file);
      } catch (IOException e) {
        return Response.fail("文件上传失败");
      }
      return Response.success(signFile);
  }
  
  /**
   * 查看电子签名
   * @param id
   * @return
   */
  @RequestMapping(value="/user/sign/download", method=RequestMethod.GET)
  public ResponseEntity<byte[]> download(@RequestParam(name="id") String id) {
      try {
        return  signService.download(id);
      } catch (IOException e) {
        return null;
      }
  }


}

