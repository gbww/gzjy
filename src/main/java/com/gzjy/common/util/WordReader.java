package com.gzjy.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class WordReader {
  private static final int TOTAL = 970;
  private static final int MARGIN = 8;
  private static final Logger logger = LoggerFactory.getLogger(WordReader.class);

  /**
   * 获取随机英文单词
   * 
   * @return
   * @throws IOException
   */
  public String getRandomWord() {
    Random rand = new Random();
    File file = getDictionary();
    RandomAccessFile raf = null;
    String word = null;
    try {
      raf = new RandomAccessFile(file, "r");
      int line = rand.nextInt(TOTAL);
      raf.seek(line * MARGIN);
      word = raf.readLine();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (raf != null) {
      try {
        raf.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return word;
  }

  private File getDictionary() {
    File file = null;
    PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
    try {
      Resource resources = patternResolver.getResource("classpath:dictionary/words.txt");
      file = resources.getFile();
    } catch (IOException e) {
      logger.error("无法获取到验证码词库！！！");
    }
    return file;
  }
}
