/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年8月24日
 */
package wang;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ClassUtils;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gzjy.Application;
import com.gzjy.organization.mapper.OrganizationMapper;
import com.gzjy.organization.model.Organization;
import com.gzjy.organization.model.OrganizationExample;
import com.gzjy.organization.model.OrganizationExample.Criteria;
import com.gzjy.user.mapper.UserMapper;
import com.gzjy.user.model.User;

/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年8月24日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Application.class)
public class TestUsers {
    @Autowired
    private UserMapper mapper;
    @Autowired
    private OrganizationMapper organizationmapper;
    @Test
    public void testUser() throws Exception {
        
        List<User> records = new ArrayList<User>();
        PageInfo<User> pages = null;
        /*if (StringUtils.isBlank(orderBy)) {
            orderBy = "alarm_level asc, alarm_time desc";
        }*/
      List<Organization> organizations=new ArrayList<Organization>();
      OrganizationExample example=new OrganizationExample();
      Criteria criteria =example.createCriteria();
      criteria.andNameLike("sh");
        List<User> re=  mapper.seletTest();
        organizations=organizationmapper.selectByExample(example);
    
        pages = PageHelper.startPage(1, 10).doSelectPageInfo(new ISelect() {
                    @Override
                    public void doSelect() {
                       // List<User> records_local = new ArrayList<User>();
                        //mapper.selectAll();
                        //mapper.selectStatusUsersBasedOrganizationsAndRole(organizations, null, null, null);
                     //   records_local =  mapper.seletTest();
                        organizationmapper.selectByExample(example);
                      //  System.out.println(records_local.size());
                    }
                });
        
      
        System.out.print(pages.getSize());
    }
    
    @Test
    public void testpath() throws Exception {
    
        String name=ClassUtils.getDefaultClassLoader().getResource("sign/xue.png").getPath();
        System.out.println(name);
    }
      
    
    
    
}
