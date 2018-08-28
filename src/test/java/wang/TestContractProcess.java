package wang;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.task.Task;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.Execution;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gzjy.Application;
import com.gzjy.contract.service.impl.TaskComplete;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Application.class)
public class TestContractProcess {

	@Autowired
	private ProcessEngine processEngine;
	
	@Test
    public void testDeploy() throws Exception {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        IdentityService identityService=processEngine.getIdentityService();
        TaskService taskService = processEngine.getTaskService();
        repositoryService.createDeployment().addClasspathResource("processes/ContractProcess.bpmn").deploy();
        List<String> approve_users = Arrays.asList("user1", "user2", "user3"); 
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("approve_users", approve_users);
        variables.put("resultCount", 0);
        variables.put("taskComplete", new TaskComplete());
        String processId = runtimeService.startProcessInstanceByKey("TestProcess",variables).getId();
        //回填processID到合同表中
        System.out.println("\n***************合同流程开始***************");   
        
	}
	
	@Test
    public void testCompleteTask() throws Exception {
		TaskService taskService = processEngine.getTaskService();
		RuntimeService runtimeService = processEngine.getRuntimeService();
		HistoryService historyService = processEngine.getHistoryService();
		Task task = taskService.createTaskQuery().taskAssignee("user1").list().get(0);
//		并制定任务结果，如果是否定，设置为0，同意忽略，默认是1
//		runtimeService.setVariable(task.getExecutionId(), "result", 0);
//		完成任务
		taskService.complete(task.getId());
		System.out.println("user1的任务执行完成");
//		task = taskService.createTaskQuery().taskAssignee("user2").singleResult();
//		taskService.complete(task.getId());
//		System.out.println("user2的任务执行完成");
////		此时流程已经结束，因为结束条件已经满足
//		long count = historyService.createHistoricProcessInstanceQuery().finished().count();
//		System.out.println("count="+ count);
	}	
	
	
}
