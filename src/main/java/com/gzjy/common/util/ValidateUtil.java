package com.gzjy.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 
 * @author yangxiaoming@cmss.chinamobile.com
 *
 */
public class ValidateUtil {
  /**
   * verify if the email is legal
   * @param email ��email
   * @return true if is legal false otherwise
   */
  public static boolean isLegalEmail(String email){
    if(email == null) return false;
    Pattern pattern = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
    Matcher matcher = pattern.matcher(email);
    if(matcher.find())
      return true;
    return false;
  }
  
  /**
   * verify if the phone number is legal
   * @param phone ��phone number
   * @return true if is legal false otherwise
   */
  public static boolean isLegalPhone(String phone){
    if(phone == null) return false;
    Pattern pattern = Pattern.compile("^(1[3-8])\\d{9}$");
    Matcher matcher = pattern.matcher(phone);
    if(matcher.find())
      return true;
    return false;
  }
  
  /**
   * verify if the object is null
   * @param obj
   * @return true if is null false otherwise
   */
  public static boolean isNull(Object obj){
    if(obj == null) return true;
    return false;
  }
  
  /**
   * verify the string
   * @param str : the string to be verified
   * @param pass : if set true allow null value false otherwise
   * @return true if pass the verification false otherwise
   */
  public static boolean isLegal(String str, boolean pass){
    if(str == null) return pass;
    Pattern pattern = Pattern.compile("^[\\S\u4e00-\u9fa5]+$");
    Matcher matcher = pattern.matcher(str);
    if(matcher.find())
      return true;
    return false;
  }
}
