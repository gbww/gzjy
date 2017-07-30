package com.gzjy.oauth;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gzjy.common.exception.SysException;

/**
 * Created by szt on 2017/1/12.
 */
public class LoginDecoder implements EpicLoginDecoder {
  private static final Logger logger = LoggerFactory.getLogger(LoginDecoder.class);
  private Key key;

  @Override
  public String usernameDecode(String username) {
    return decodeStr(username);
  }

  @Override
  public String passwordDecode(String password) {
    return decodeStr(password);
  }

  private String decodeStr(String str) {
    if (key == null) {
      key = readKey();
    }
    
    Cipher cipher = null;
    try {
      cipher = Cipher.getInstance("RSA");
      cipher.init(Cipher.DECRYPT_MODE, key);
      byte[] base64DecryStr = Base64.getDecoder().decode(str);
      byte[] bytes = cipher.doFinal(base64DecryStr);
      return new String(bytes);
    } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e){
      logger.error("解密失败,请确认传输加密方式: " + e.getMessage());
      throw new SysException("解密失败,请确认传输加密方式", e);
    } 
  }
  
  private Key readKey() {
    Key key = null;
    InputStream is = null;
    try {
      is = this.getClass().getResourceAsStream("/rsa/private.rsa");    
      // Java 7 新特性，自动释放资源
      try (ObjectInputStream oisKey = new ObjectInputStream(is)) {
        key = (Key) oisKey.readObject();
        logger.info("read key is : " + is);
      } catch (Exception e) {
        logger.error("私钥读取失败", e);
      }
      if (key == null) {
        throw new SysException("读取私钥失败，项目缺失私钥配置文件");
      }
    } finally {
      if (is != null) {
        try {
          is.close();
        } catch (IOException e) {
          logger.error("私钥文件流关闭错误", e);
        }
      }
    }
    return key;
  }
}
