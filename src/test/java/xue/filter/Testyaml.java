/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年10月24日
 */
package xue.filter;

import java.io.FileInputStream;
import java.net.URL;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年10月24日
 */
public class Testyaml {

    /**
     * @param args
     * Testyaml.java
     */
    public static void main(String[] args) {
        try {
            Yaml yaml = new Yaml();
            URL url = Testyaml.class.getClassLoader().getResource("IPS.yaml");
            if (url != null) {
                //获取test.yaml文件中的配置数据，然后转换为obj，
                Object obj =yaml.load(new FileInputStream(url.getFile()));
                
                //System.out.println(obj);
                //也可以将值转换为Map
                Map map =(Map)yaml.load(new FileInputStream(url.getFile()));
               Map map1=(Map)map.get("oids");
                
                for(Object key : map1.keySet())
                System.out.println("INSERT INTO `alarm_item` VALUES (REPLACE(UUID(),'-',''), '设备SNMP TRAP 告警',"+"'"+key+"'"+", '网络设备');");
                
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
