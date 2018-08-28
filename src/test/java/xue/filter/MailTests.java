/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年12月5日
 */
package xue.filter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gzjy.Application;
import com.gzjy.mail.service.MailService;

/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年12月5日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Application.class)
public class MailTests {
    
    @Autowired  
    private MailService mailService;  
      
    private String to = "xuewenlong@cmss.chinamobile.com";  
      
    @Test  
    public void sendSimpleMail() {  
        mailService.sendSimpleMail(to, "主题：简单邮件", "测试邮件内容");  
    }  

   

}
