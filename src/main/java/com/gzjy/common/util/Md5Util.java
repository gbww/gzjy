package com.gzjy.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * MD5���ܹ�����
 * @author yangxiaoming@cmss.chinamobile.com
 *
 */
public class Md5Util {
  /***
   * MD5���� ����32λmd5��
   * 
   * @param str ���������ַ���
   */
  public static String string2MD5(String str) {
    StringBuffer sb = new StringBuffer();
    // ��ȡMD5�㷨ʵ�� �õ�һ��md5����ϢժҪ
    MessageDigest md = null;
    try {
      md = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    // ���Ҫ���м���ժҪ����Ϣ
    md.update(str.getBytes());
    // �õ���ժҪ
    byte b[] = md.digest();
    
    //���ֽ�����ת�����ַ���
    for (byte aByte : b) {
      String s = Integer.toHexString(0xff & aByte);
      if (s.length() == 1) {
        sb.append("0" + s);
      } else {
        sb.append(s);
      }
    }
    return sb.toString();
  }
}
