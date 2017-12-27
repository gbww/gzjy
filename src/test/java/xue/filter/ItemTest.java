/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年12月25日
 */
package xue.filter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gzjy.Application;
import com.gzjy.mail.service.MailService;
import com.gzjy.receive.mapper.ReceiveSampleItemMapper;
import com.gzjy.receive.model.ReceiveSampleItem;

/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年12月25日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Application.class)
public class ItemTest {
    
    @Autowired  
    private MailService mailService;  
    @Autowired  
    private ReceiveSampleItemMapper mapper;
    
    
    @Test  
    public void testquery() {  
        Map<String, Object> filter = new HashMap<String, Object>();
   //     filter.put("receive_sample_id", "F005");
        filter.put("status", 1);
        //filter.put("unit", "g");
        List<ReceiveSampleItem>  resoult= mapper.selectTestDetail(filter,"updated_at desc");
        System.out.println(resoult.size());
    }  
      
   

   

}