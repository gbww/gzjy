package xue.twodem;
import java.io.IOException;

import com.google.zxing.WriterException;

public class Qrest {

    public static void main(String[] args) {

        String content="大家好，我是薛文龙，很高兴认识大家";
     String logUri="/var/xue/xue.jpg";
     String outFileUri="/var/test.jpg";
     int[]  size=new int[]{430,430};
     String format = "jpg";  
     
       try {
        new QRCodeFactory().CreatQrImage(content, format, outFileUri, logUri,size);
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } catch (WriterException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }   
  }

}
