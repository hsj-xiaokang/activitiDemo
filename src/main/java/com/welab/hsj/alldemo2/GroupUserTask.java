package com.welab.hsj.alldemo2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ActivitiTaskAlreadyClaimedException;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import com.welab.hsj.alldemo.ConstantInfo;

/**
 * 角色组相关
 * 可以看成组任务的特殊
 * !!!很少用实际当中，比如：用户表字段很少啊
 * @author hsj
 *
 */
public class GroupUserTask {
	
	 private String proKey = "groupUserTask";
		
		ProcessEngine pe = ProcessEngines.getDefaultProcessEngine();
	/**
	 * 部署
	 */
	@Test
	public void deployementProcessDifined(){
		Deployment deployment = pe.getRepositoryService()
		  .createDeployment()
		  .name("角色")
		  .addClasspathResource("processes/hsj/GroupUserTask.bpmn")
		  .addClasspathResource("processes/hsj/GroupUserTask.png")
		  .deploy();
		
		System.out.println("部署id="+deployment.getId());
		System.out.println("部署名字="+deployment.getName());
		
		identify();
		System.out.println("添加角色组成功！");
	}
	
	/**
	 * 添加用户角色组
	 */
	@Test
	public void identify(){
		IdentityService is = pe.getIdentityService();
		//角色
		is.saveGroup(new GroupEntity(ConstantInfo.User.zongjingli));
		is.saveGroup(new GroupEntity(ConstantInfo.User.jingli));
		//用户
		is.saveUser(new UserEntity(ConstantInfo.User.zhangsan));
		is.saveUser(new UserEntity(ConstantInfo.User.lisi));
		is.saveUser(new UserEntity(ConstantInfo.User.wangwu));
		//建立中间表的关联关系
		is.createMembership(ConstantInfo.User.zhangsan, ConstantInfo.User.jingli);
		is.createMembership(ConstantInfo.User.lisi, ConstantInfo.User.jingli);
		is.createMembership(ConstantInfo.User.wangwu, ConstantInfo.User.zongjingli);
	}
	
	
	/**
	 * 流程实例
	 */
	@Test
	public void startProInstance(){
		ProcessInstance processInstance = pe.getRuntimeService()
				.startProcessInstanceByKey(proKey);
		
		System.out.println("流程实例id="+processInstance.getId());
		System.out.println("流程定义id="+processInstance.getProcessDefinitionId());
	}
	
	/**
	 * 查找当前人的组任务
	 *
	 */
	@Test
	public void findMyGroupTask(){
		List<Task> lt = pe.getTaskService()
		   .createTaskQuery()
		   .taskAssignee(ConstantInfo.User.zhangsan)
//		   .taskCandidateUser(ConstantInfo.User.lisi)
		   .list();
		if(lt != null && lt.size() > 0){
			for(Task at : lt){
				System.out.println("*********************************");
				System.out.println("任务id="+at.getId());
				System.out.println("任务名称="+at.getName());
				System.out.println("任务创建时间="+at.getCreateTime());
				System.out.println("任务办理人="+at.getAssignee());
				System.out.println("任务创建时间="+at.getCreateTime());
				System.out.println("流程实例id="+at.getProcessInstanceId());
				System.out.println("执行对象id="+at.getExecutionId());
				//会使用最新的一次的流程定义——流程定义一样的话就进行升级version
				System.out.println("流程定义id="+at.getProcessDefinitionId());
			}
		}
	}
	
	/**拾取任务，将组任务分配给个人任务，指定任务的办理人字段**/
    @Test
	public void testClaim(){
    	try{
    		//将组任务分配给个人任务，分配个人任务可以是组内里面的成员，也可以是非组内的成员
        	pe.getTaskService().claim("242504", ConstantInfo.User.zhangsan);	
    	}catch(ActivitiTaskAlreadyClaimedException e){
    		System.out.println("该组该任务节点已经指定的执行人，不能重复指定！");
    	}
     }
    
    /**
	 * 完成任务
	 */
	@Test
	public void completeCurrentTask(){
		Map<String,Object> variables = new HashMap<String,Object>();
		variables.put("pass", "pass ok!");
		pe.getTaskService().complete("242504",variables);
		
		System.out.println("完成任务id="+242504);
	}
	
	
}
