package wang;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.Execution;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gzjy.Application;


@RunWith(SpringJUnit4ClassRunner.class)  
@SpringBootTest(classes=Application.class)
public class TestContractProcess {

	@Autowired
	private ProcessEngine processEngine;
	
	@Test
    public void testWorkFlow() throws Exception {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        IdentityService identityService=processEngine.getIdentityService();
        repositoryService.createDeployment().addClasspathResource("processes/ContractProcess.bpmn").deploy();
        Map<String, Object> variables = new HashMap<String, Object>();                
                variables.put("user", "bobowang");
                variables.put("email", "xue@qq.com");
        String processId = runtimeService.startProcessInstanceByKey("Interview",variables).getId();
     
//        TaskService taskService = processEngine.getTaskService();
        //得到笔试的流程
        System.out.println("\n***************合同流程开始***************");
        
        List<Execution> exes = runtimeService.createExecutionQuery().variableValueEquals("user", "bobowang").list();  
        System.out.println(exes);
	}
}
