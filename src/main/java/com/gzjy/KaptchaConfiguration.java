package com.gzjy;

import java.awt.Color;
import java.awt.Font;
import java.util.Properties;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.google.code.kaptcha.BackgroundProducer;
import com.google.code.kaptcha.GimpyEngine;
import com.google.code.kaptcha.NoiseProducer;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.text.TextProducer;
import com.google.code.kaptcha.text.WordRenderer;
import com.google.code.kaptcha.util.Config;
import com.gzjy.common.util.WordGenerator;

@Configuration
public class KaptchaConfiguration extends Config{
  private final static Logger logger = LoggerFactory.getLogger(KaptchaConfiguration.class);
  /**
   * 是否有边框
   */
  @Value("${kaptcha.border}") 
  private String border;
  
  /**
   * 边框颜色
   */
  @Value("${kaptcha.border.color}")
  private String borderColor;
  
  /**
   * 边框粗细度
   */
  @Value("${kaptcha.border.thickness}")
  private String borderThickness;
    
  /**
   * 字体颜色
   */
  @Value("${kaptcha.textproducer.font.color}")  
  private String fontColor;  
  
  /**
   * 字体大小
   */
  @Value("${kaptcha.textproducer.font.size}")  
  private String fontSize;  
    
  /**
   * 图片宽度
   */
  @Value("${kaptcha.image.width}")  
  private String imageWidth;  
    
  /**
   * 图片高度
   */
  @Value("${kaptcha.image.height}")  
  private String imageHeight;  
    
  /**
   * 验证码噪点颜色
   */
  @Value("${kaptcha.noise.color}")  
  private String noiseColor;  
    
  /**
   * 字符距离
   */
  @Value("${kaptcha.textproducer.char.space}")  
  private String charSpace;  
    
  /**
   * 验证码背景颜色渐变开始颜色
   */
  @Value("${kaptcha.background.clear.from}")  
  private String fromColor;  
    
  /**
   * 验证码验证码背景颜色渐变结束颜色
   */
  @Value("${kaptcha.background.clear.to}")  
  private String toColor;  
  
  public KaptchaConfiguration(@Qualifier("systemProperties") Properties properties) {
    super(properties);
  }

  @Override
  public boolean isBorderDrawn() {
    if (null == border)
      return super.isBorderDrawn();
    return border.equals("false") || border.equals("0") ? false : true;
  }

  @Override
  public Color getBorderColor() {
    if (null == borderColor)
      return super.getBorderColor();
    return colorMatcher(borderColor, super.getBorderColor());
  }

  @Override
  public int getBorderThickness() {
    if (null == borderThickness)
      return super.getBorderThickness();
    return getSizeInteger(borderThickness, super.getBorderThickness(), "边框厚度-border.thickness");
  }

  @Override
  public Color getBackgroundColorFrom() {
    if (null == fromColor) 
      return super.getBackgroundColorFrom();
    return colorMatcher(fromColor, super.getBackgroundColorFrom());
  }

  @Override
  public Color getBackgroundColorTo() {
    if (null == toColor) 
      return super.getBackgroundColorTo();
    return colorMatcher(toColor, super.getBackgroundColorTo());
  }

  @Override
  public int getWidth() {
    if (null == imageWidth)
      return super.getWidth();
    return getSizeInteger(imageWidth, super.getWidth(), "图片宽度-image.width");
  }

  @Override
  public int getHeight() {
    if (null == imageHeight)
      return super.getHeight();
    return getSizeInteger(imageHeight, super.getHeight(), "图片高度-image.height");
  }
  @Override
  public int getTextProducerFontSize() {
    if (null == fontSize) 
      return super.getTextProducerFontSize();
    return getSizeInteger(fontSize, super.getTextProducerFontSize(), "字体大小-textproduce.font.size");
  }

  @Override
  public Color getTextProducerFontColor() {
    if (null == fontColor)
      return super.getTextProducerFontColor();
    return colorMatcher(fontColor, super.getTextProducerFontColor());
  }

  @Override
  public int getTextProducerCharSpace() {
    if (null == charSpace) 
      return super.getTextProducerCharSpace();
    return getSizeInteger(charSpace, super.getTextProducerCharSpace(), "字符距离-textproduce.char.space");
  }
  
  @Override
  public Color getNoiseColor() {
    if (null == noiseColor) 
      return super.getNoiseColor();
    return colorMatcher(noiseColor, super.getNoiseColor());
  }
  
  @Override
  public Producer getProducerImpl() {
    // TODO Auto-generated method stub
    return super.getProducerImpl();
  }

  @Override
  public TextProducer getTextProducerImpl() {
    return new TextProducer() {
      private Random rand = new Random();
      
      @Override
      public String getText() {
//        int choose = rand.nextInt(3);
        int choose = 0;
        String text = null;
        switch (choose) {
          case 0 :
            text = getWord();
            break;
          case 1:
            text = getChinese();
            break;
          case 2:
            text = getMath();
            break;
        }
        return text;
      }
      
      /**
       * 获取英文单词
       * @return
       */
      private String getWord() {
        String word = "undefined";
        try {
          word = WordGenerator.getRandomWord();
        } catch (Exception e) {
          logger.error("获取随机英文单词验证码出错！！！");
        }
        return word;
      }
      
      private String getChinese() {
        int length = 4;
        String[] s = new String[]{"验","证","码","测","试"};

        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < length; i++){
            int ind = rand.nextInt(s.length);
            sb.append(s[ind]);
        }
        return sb.toString();
        
      }
      /**
       * 中午实例
       * @return
       */
      private String getMath() {
//          int length = getConfig().getTextProducerCharLength();
        int length = 4;
          String finalWord = "", firstWord = "";
          int tempInt = 0;
          String[] array = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                  "a", "b", "c", "d", "e", "f","g","h","i","j","k","l","m","n",
                  "o","p","q","r","s","t","u","v","w","x","y","z" };

          Random rand = new Random();

          for (int i = 0; i < length; i++) {
              switch (rand.nextInt(array.length)) {
              case 1:
                  tempInt = rand.nextInt(26) + 65;
                  firstWord = String.valueOf((char) tempInt);
                  break;
              case 2:
                  int r1,
                  r2,
                  r3,
                  r4;
                  String strH,
                  strL;// high&low
                  r1 = rand.nextInt(3) + 11; // 前闭后开[11,14)
                  if (r1 == 13) {
                      r2 = rand.nextInt(7);
                  } else {
                      r2 = rand.nextInt(16);
                  }

                  r3 = rand.nextInt(6) + 10;
                  if (r3 == 10) {
                      r4 = rand.nextInt(15) + 1;
                  } else if (r3 == 15) {
                      r4 = rand.nextInt(15);
                  } else {
                      r4 = rand.nextInt(16);
                  }

                  strH = array[r1] + array[r2];
                  strL = array[r3] + array[r4];

                  byte[] bytes = new byte[2];
                  bytes[0] = (byte) (Integer.parseInt(strH, 16));
                  bytes[1] = (byte) (Integer.parseInt(strL, 16));

                  firstWord = new String(bytes);
                  break;
              default:
                  tempInt = rand.nextInt(10) + 48;
                  firstWord = String.valueOf((char) tempInt);
                  break;
              }
              finalWord += firstWord;
          }
          return finalWord;
      }
    };
  }

  /**
   * 验证码文本字符内容范围
   */
  @Override
  public char[] getTextProducerCharString() {
    // TODO Auto-generated method stub
    return super.getTextProducerCharString();
  }

  @Override
  public int getTextProducerCharLength() {
    // TODO Auto-generated method stub
    return super.getTextProducerCharLength();
  }

  @Override
  public Font[] getTextProducerFonts(int fontSize) {
    // TODO Auto-generated method stub
    return super.getTextProducerFonts(fontSize);
  }

  @Override
  public NoiseProducer getNoiseImpl() {
    // TODO Auto-generated method stub
    return super.getNoiseImpl();
  }

  @Override
  public GimpyEngine getObscurificatorImpl() {
    // TODO Auto-generated method stub
    return super.getObscurificatorImpl();
  }

  @Override
  public WordRenderer getWordRendererImpl() {
    // TODO Auto-generated method stub
    return super.getWordRendererImpl();
  }

  @Override
  public BackgroundProducer getBackgroundImpl() {
    // TODO Auto-generated method stub
    return super.getBackgroundImpl();
  }

  @Override
  public String getSessionKey() {
    // TODO Auto-generated method stub
    return super.getSessionKey();
  }

  @Override
  public String getSessionDate() {
    // TODO Auto-generated method stub
    return super.getSessionDate();
  }

  @Override
  public Properties getProperties() {
    // TODO Auto-generated method stub
    return super.getProperties();
  }
  
  private int getSizeInteger(String size, int superSize, String propertity) {
    int iSize = superSize;
    try {
      iSize = Integer.valueOf(size);
      if (iSize < 0 || iSize == 0) throw new Exception();
    } catch (Exception e) {
      logger.error("Kaptcha配置: " + propertity + " 信息有误！！！");
      iSize = superSize;
    }
    return iSize;
  }
  
  private Color colorMatcher(String color, Color superColor) {
    color = color.toUpperCase();
    if (color.equals("BLACK"))
      return Color.BLACK;
    else if (color.equals("BLUE"))
      return Color.BLUE;
    else if (color.equals("CYAN"))
      return Color.CYAN;
    else if (color.equals("DARK_GRAY"))
      return Color.DARK_GRAY;
    else if (color.equals("GRAY"))
      return Color.GRAY;
    else if (color.equals("GREEN"))
      return Color.GREEN;
    else if (color.equals("LIGHT_GRAY"))
      return Color.LIGHT_GRAY;
    else if (color.equals("MAGENTA"))
      return Color.MAGENTA;
    else if (color.equals("ORANGE"))
      return Color.ORANGE;
    else if (color.equals("PINK"))
      return Color.PINK;
    else if (color.equals("RED"))
      return Color.RED;
    else if (color.equals("WHITE"))
      return Color.WHITE;
    else if (color.equals("YELLOW"))
      return Color.YELLOW;
    else
      return superColor;
  }
}
