/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年8月24日
 */
package wang;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gzjy.Application;
import com.gzjy.receive.model.ReceiveSample;
import com.gzjy.receive.model.ReceiveSampleItem;
import com.gzjy.receive.service.ReceiveSampleService;

/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年8月24日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Application.class)
public class TestReceiveSample {
    @Autowired
  private ReceiveSampleService service;
   
    @Test
    public void testAddResiveSample() throws Exception {
        ReceiveSample record =new ReceiveSample();
        record.setReceiveSampleId("F003");
        record.setCreatedAt(new Date());
        record.setEntrustedUnit("安徽食药局");
        record.setSampleType("委托检验");
        record.setCheckType("抽检12");
        record.setExecuteStandard("GB123-22");
        service.addReceiveSample(record);
    }
    @Test
    public void addReceiveSampleItems() throws Exception {
        List<ReceiveSampleItem> items=new LinkedList<ReceiveSampleItem>();
        ReceiveSampleItem record1 =new ReceiveSampleItem();
        record1.setReceiveSampleId("F001");
        record1.setName("氨基酸");
        record1.setMethod("GB123");
        ReceiveSampleItem record2 =new ReceiveSampleItem();
        record2.setReceiveSampleId("F001");
        record2.setName("氨基酸");
        record2.setMethod("GB123");
        items.add(record1);
        items.add(record2);
        service.addReceiveSampleItems(items);
    }
    
    @Test
    public void selectAll() throws Exception {
        Map<String, Object> filter=new HashMap<String, Object>();
        //filter.put("check_type", "抽检");
        service.select(1, 10, null,5, filter);
    }
    
    @Test
    public void delete() throws Exception {
  
        service.delete("F001");
    }
      
    
    
    
}
