package com.gzjy.common.util;

import java.util.Random;
/**
 * ����������֤��
 * @author yangxiaoming@cmss.chinamobile.com
 *
 */
public class CheckCodeUtil {
  /**
   * ����4λ�����֤��
   * @return
   */
  public static String generateCheckCode() {
    char[] codeChar = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'a', 'b', 'c', 'd', 'e', 'f',
                          'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
                          'w', 'x', 'y', 'z'};
    int number;
    char code;
    String checkCode = "";

    Random random = new Random();
    for (int i = 0; i < 4; i++)
    {
        number = random.nextInt(36);
        code = codeChar[number];
        checkCode += code;
    }
    return checkCode;
  }
}