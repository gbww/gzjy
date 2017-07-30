package com.gzjy.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class WordGenerator {
  private static final int TOTAL = 970;
  private static final Logger logger = LoggerFactory.getLogger(WordGenerator.class);
  
  public static final String[] words = init();
  
  private static String[] init() {
    PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
    InputStream is = null;
    try {
      Resource resource = patternResolver.getResource("/dictionary/words.txt");
      is = resource.getInputStream();
    } catch (IOException e) {
      logger.error("无法获取到验证码词库！！！");
    }
    InputStreamReader isr = new InputStreamReader(is);
    BufferedReader br = new BufferedReader(isr);
    String str = null;
    String[] words = new String[TOTAL];
    int i = 0;
    try {
      while ((str = br.readLine()) != null) {
        words[i++] = str;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (br != null) {
      try {
        br.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    if (isr != null) {
      try {
        isr.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    if (is != null) {
      try {
        is.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return words;
  }
  
  public static String getRandomWord() {
    Random rand = new Random();
    int index = rand.nextInt(TOTAL);
    String word = words[index];
    return word;
  }
}
