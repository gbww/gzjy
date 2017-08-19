import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gzjy.Application;

/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年5月1日
 */

/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年5月1日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Application.class)
public class TestActiviti {
    @Autowired
    private ProcessEngine processEngine;
   
    @Test
    public void testWorkFlow() throws Exception {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        IdentityService identityService=processEngine.getIdentityService();
        repositoryService.createDeployment().addClasspathResource("processes/Interview.bpmn").deploy();
        Map<String, Object> variables = new HashMap<String, Object>();
                //variables.put("user", "张三");
                variables.put("user", "xuexuexue");
                variables.put("email", "xue@qq.com");
        String processId = runtimeService.startProcessInstanceByKey("Interview",variables).getId();
     
        TaskService taskService = processEngine.getTaskService();
        //得到笔试的流程
        System.out.println("\n***************笔试流程开始***************");
       

       /* Group group = identityService.newGroup("wode");
        

        group.setName("经理组");

        group.setType("manager");
        identityService.saveGroup(group);
        
        User u=identityService.newUser("张三");
        identityService.saveUser(u);
        identityService.createMembership("张三", "wode");*/
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("人力资源部").list();
        for (Task task : tasks) {
            System.out.println("人力资源部的任务：name:"+task.getName()+",id:"+task.getId());
            String processdefintionId= task.getProcessInstanceId();
            System.out.println("\n***************笔试流程开始***************"+processdefintionId);
            taskService.claim(task.getId(), "张三");
            taskService.addUserIdentityLink(task.getId(), "张三", "hah11");
            
           
        }
       

        System.out.println("张三的任务数量："+taskService.createTaskQuery().taskAssignee("张三").count());
        tasks = taskService.createTaskQuery().taskAssignee("张三").list();
        Map<String, Object> variable11 = new HashMap<String, Object>();
        //variables.put("user", "张三");
        variable11.put("message", "hellllllllll");
        variable11.put("email", "138922222");
        for (Task task : tasks) {
            System.out.println("张三的任务：name:"+task.getName()+",id:"+task.getId());
            taskService.addComment(task.getId(), processId, "薛文龙评价，完成的不错");
            /*TaskEntity entity=(TaskEntity)task;
            entity.addIdentityLink("张三", "wode", "manager");
           */
           
           // taskService.addGroupIdentityLink(task.getId(), "xuwenlong", "gaoji");
           // taskService.addUserIdentityLink(task.getId(), "张三", "hah");
            runtimeService.setVariables(task.getExecutionId(), variable11);
            taskService.complete(task.getId());
           
           
         
        }

        System.out.println("张三的任务数量："+taskService.createTaskQuery().taskAssignee("张三").count());
        System.out.println("***************笔试流程结束***************");

        System.out.println("\n***************一面流程开始***************");
        tasks = taskService.createTaskQuery().taskCandidateGroup("技术部").list();
        for (Task task : tasks) {
            System.out.println("技术部的任务：name:"+task.getName()+",id:"+task.getId());
            taskService.claim(task.getId(), "李四");
        }

        System.out.println("李四的任务数量："+taskService.createTaskQuery().taskAssignee("李四").count());
        for (Task task : tasks) {
            System.out.println("李四的任务：name:"+task.getName()+",id:"+task.getId());
            taskService.complete(task.getId());
        }

        System.out.println("李四的任务数量："+taskService.createTaskQuery().taskAssignee("李四").count());
        System.out.println("***************一面流程结束***************");

        System.out.println("\n***************二面流程开始***************");
        tasks = taskService.createTaskQuery().taskCandidateGroup("技术部").list();
        for (Task task : tasks) {
            System.out.println("技术部的任务：name:"+task.getName()+",id:"+task.getId());
            taskService.claim(task.getId(), "李四");
        }

        System.out.println("李四的任务数量："+taskService.createTaskQuery().taskAssignee("李四").count());
        for (Task task : tasks) {
            System.out.println("李四的任务：name:"+task.getName()+",id:"+task.getId());
            taskService.complete(task.getId());
        }

        System.out.println("李四的任务数量："+taskService.createTaskQuery().taskAssignee("李四").count());
        System.out.println("***************二面流程结束***************");

        System.out.println("***************HR面流程开始***************");
        tasks = taskService.createTaskQuery().taskCandidateGroup("人力资源部").list();
        for (Task task : tasks) {
            System.out.println("技术部的任务：name:"+task.getName()+",id:"+task.getId());
            taskService.claim(task.getId(), "李四");
        }

        System.out.println("李四的任务数量："+taskService.createTaskQuery().taskAssignee("李四").count());
        for (Task task : tasks) {
            System.out.println("李四的任务：name:"+task.getName()+",id:"+task.getId());
            taskService.complete(task.getId());
        }

        System.out.println("李四的任务数量："+taskService.createTaskQuery().taskAssignee("李四").count());
        System.out.println("***************HR面流程结束***************");

        System.out.println("\n***************录用流程开始***************");
        tasks = taskService.createTaskQuery().taskCandidateGroup("人力资源部").list();
        for (Task task : tasks) {
            System.out.println("技术部的任务：name:"+task.getName()+",id:"+task.getId());
            taskService.claim(task.getId(), "李四");
        }

        System.out.println("李四的任务数量："+taskService.createTaskQuery().taskAssignee("李四").count());
        for (Task task : tasks) {
            System.out.println("李四的任务：name:"+task.getName()+",id:"+task.getId());
            taskService.complete(task.getId());
        }

        System.out.println("李四的任务数量："+taskService.createTaskQuery().taskAssignee("李四").count());
        System.out.println("***************录用流程结束***************");

        HistoryService historyService = processEngine.getHistoryService();
        HistoricProcessInstance historicProcessInstance = historyService
                .createHistoricProcessInstanceQuery()
                .processInstanceId(processId).singleResult();
        System.out.println("\n流程结束时间："+historicProcessInstance.getEndTime());
    }
       
    
    @Test  
    public void findHisTaskList(){  
        TaskService taskService = processEngine.getTaskService();
        String processInstanceId = "45004";  
        List<HistoricTaskInstance> list = processEngine.getHistoryService()  
                .createHistoricTaskInstanceQuery()  
                .processInstanceId(processInstanceId).finished().orderByTaskCreateTime().asc()
                .list();  
        if(list!=null && list.size()>0){  
            for(HistoricTaskInstance hti:list){  
                System.out.println(hti.getId()+"    "+hti.getName()+"   "+hti.getClaimTime()); 
                if(taskService.getTaskComments(hti.getId()).size()>0) {
                    String commit=taskService.getTaskComments(hti.getId()).get(0).getFullMessage();
                    System.out.println(commit);
                }
            }  
        }  
        List<Task>tasks=taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        for(Task task :tasks) {
            System.out.println(task.getId()+":"+task.getName());  
            if(taskService.getTaskComments(task.getId()).size()>0) {
                System.out.println(taskService.getTaskComments(task.getId()).get(0).toString());
            }
        }
    }  
  
   

}
