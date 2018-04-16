/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2018年4月12日
 */
package xue.model;

import java.util.ArrayList;

/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2018年4月12日
 */
public class StudentBeanFactory {
    public static ArrayList<StudentScore> getBeanCollection(){
        ArrayList<StudentScore> dataList=new ArrayList<>();
         dataList.add(new StudentScore("王小白", "95279527", "3", "102", "113", "108", "80", "82", "83", "6"));
         dataList.add(new StudentScore("王小红", "95289528", "6", "103", "101", "134", "81", "76", "68", "10"));
     dataList.add(new StudentScore("王小黑", "95299529", "11", "99", "123", "133", "79", "88", "92", "12"));
     return dataList;
 }

}
