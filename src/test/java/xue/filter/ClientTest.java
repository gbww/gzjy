/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年8月24日
 */
package xue.filter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ClassUtils;

import com.gzjy.Application;
import com.gzjy.client.model.ClientLinkman;
import com.gzjy.client.model.GzClient;
import com.gzjy.client.service.ClientLinkmanService;
import com.gzjy.client.service.ClientService;
import com.gzjy.common.util.fs.EpicNFSClient;
import com.gzjy.common.util.fs.EpicNFSService;

/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年8月24日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Application.class)
public class ClientTest {
@Autowired
private ClientService clientService;
@Autowired
private ClientLinkmanService  clientLinkmanService;


@Autowired
private EpicNFSService epicNFSService;
    @Test
    public void testAddClient() throws Exception {
        GzClient client=new GzClient();
        client.setClientName("薛文龙");
        clientService.add(client);
     
    }
    
    @Test
    public void testAddClientLinkman() throws Exception {
        ClientLinkman man=new ClientLinkman();
        man.setClientNum(1);
        man.setLinkmanName("xxx");
        clientLinkmanService.add(man);
     
    }
    
    @Test
    public void testpath() throws Exception {
    
        String name=ClassUtils.getDefaultClassLoader().getResource("sign/xue.png").getPath();
        System.out.println(name);
    }
    
    @Test
    public void testcreateFile() throws Exception {
    
        EpicNFSClient epicNFSClient = epicNFSService.getClient("gzjy");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar cal=Calendar.getInstance();
        
        
        String curDate = simpleDateFormat.format(cal.getTime());  
        
        String parentPath = "receive"+"/"+curDate ;
        if (!epicNFSClient.hasRemoteDir(parentPath)) {
            epicNFSClient.createRemoteDir(parentPath);
        }
    }
      
    
    
    
}
