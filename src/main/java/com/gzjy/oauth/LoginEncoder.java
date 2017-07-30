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

public class LoginEncoder {
  private static final Logger logger = LoggerFactory.getLogger(LoginEncoder.class);
  private Key key;
  
  public String encodeUsername(String username) {
    return encodeStr(username);
  }
  
  public String encodePassword(String password) {
    return encodeStr(password);
  }
  
  private String encodeStr(String str) {
    if (key == null) {
      key = readKey();
    }
    Cipher cipher = null;

    try {
      cipher = Cipher.getInstance("RSA");
      cipher.init(Cipher.ENCRYPT_MODE, key);
      
      byte[] rsaChs = cipher.doFinal(str.getBytes());
      byte[] encryptStr = Base64.getEncoder().encode(rsaChs);
      return new String(encryptStr);
    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
      throw new SysException("加密失败,请确认传输加密方式", e);
    }
  }
  
  private Key readKey() {
    Key key = null;
    InputStream is = null;
    try {
      is = this.getClass().getResourceAsStream("/rsa/public.rsa");
      // Java7 新特性  ， 自动释放资源
      try (ObjectInputStream oisKey = new ObjectInputStream(is)) {
        key = (Key) oisKey.readObject();

      } catch (Exception e) {
        logger.error("公钥读取失败", e);
      }
      if (key == null) {
        throw new SysException("读取公钥失败，项目缺失公钥配置文件");
      }
    } finally {
      if (is != null) {
        try {
          is.close();
        } catch (IOException e) {
          logger.error("公钥文件流关闭错误", e);
        }
      }
    }
    return key;
  }
}
