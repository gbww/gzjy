package xue.filter;
/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2016年9月6日
 */


import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2016年9月6日
 */
public class SendMessage implements JavaDelegate{

    /* (non-Javadoc)
     * @see org.activiti.engine.delegate.JavaDelegate#execute(org.activiti.engine.delegate.DelegateExecution)
     */
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("in : " + execution.getVariables()+"aaaaa:"+execution.getEventName()); 
        System.out.println("Send 薛文龙。。。。。。。。");
    }

}
