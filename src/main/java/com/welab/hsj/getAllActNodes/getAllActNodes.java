package com.welab.hsj.getAllActNodes;

import java.util.Collection;
import java.util.List;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.task.Task;
import org.junit.Test;
/**
 * Activiti工作流之获取流程定义中节点相关操作
 * see:https://blog.csdn.net/hiyohu/article/details/51682005
 *     https://blog.csdn.net/chenfengdejuanlian/article/details/77063814
 * @author hsj
 *
 */
public class getAllActNodes {
	ProcessEngine pe = ProcessEngines.getDefaultProcessEngine();

	/**
	 * springboot会自动部署resources下面的processes文件直接目录下面的流程定义文件【只会第一子集，下面嵌套不会部署】
	 * -这里练习自己部署
	 */
	@Test
	public void deployementProcessDifined(){
		Deployment deployment = pe.getRepositoryService()
		  .createDeployment()
		  .name("sequenceFlow")
		  .addClasspathResource("processes/hsj/sequence.bpmn")
		  .addClasspathResource("processes/hsj/sequence.png")
		  .deploy();
		
		System.out.println("部署id="+deployment.getId());
		System.out.println("部署名字="+deployment.getName());
	}
	
	/**
	 * 
	* @Title: getAllActNodes.java  
	* @Package com.welab.hsj.getAllActNodes  
	* @Description: TODO(Activiti工作流之根据processDefinitionId获取流程定义中所有的节点-流程部署至服务器上之后可使用-只获取UserTask节点)  
	* @author hsj 
	* @date 2018年10月23日  
	* @version V1.0
	 */
	@Test
	public void getDeploymentNodes(){
		BpmnModel model = pe.getRepositoryService().getBpmnModel("exclusiveGateWay:1:7504");
		
		if(model != null) {
		    Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();
		    
		    for(FlowElement e : flowElements) {
		    	
		        if(e.getName() != null && !"".equals(e.getName()) && "class org.activiti.bpmn.model.UserTask".equals(e.getClass().toString())){
		        	System.out.println("=============><==============="+e.toString());
		        	System.out.println("flowelement id:" + e.getId() + "  name:" + e.getName() + "  documentation:" + e.getDocumentation()+ "   class:" + e.getClass().toString());
		        }
		    }
		}

	}
	
	
	/**
     * 根据procInstanceId获取当前流程的下一个节点信息
     * @param procInstanceId
     * @return
     */
	@Test
    public void  getNextNode(){
		String procInstanceId = "10001";
        // 1、首先是根据流程ID获取当前任务：
        List<Task> tasks = pe.getTaskService().createTaskQuery().processInstanceId(procInstanceId).list();
        for (Task task : tasks) {
            RepositoryService rs = pe.getRepositoryService();
            // 2、然后根据当前任务获取当前流程的流程定义，然后根据流程定义获得所有的节点：
            ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) rs)
                    .getDeployedProcessDefinition(task.getProcessDefinitionId());
            List<ActivityImpl> activitiList = def.getActivities(); // rs是指RepositoryService的实例
            // 3、根据任务获取当前流程执行ID，执行实例以及当前流程节点的ID：
            String excId = task.getExecutionId();
            RuntimeService runtimeService = pe.getRuntimeService();
            ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(excId)
                    .singleResult();
            String activitiId = execution.getActivityId();
            // 4、然后循环activitiList
            // 并判断出当前流程所处节点，然后得到当前节点实例，根据节点实例获取所有从当前节点出发的路径，然后根据路径获得下一个节点实例：
            for (ActivityImpl activityImpl : activitiList) {
                String id = activityImpl.getId();
                if (activitiId.equals(id)) {
                    System.out.println("当前任务-name：" + activityImpl.getProperty("name")); // 输出某个节点的某种属性
                    System.out.println("当前任务-id：" + activityImpl.getId()); // 输出某个节点的某种属性
                    System.out.println("当前任务-info：" + activityImpl); // 输出某个节点的某种属性
                    System.out.println("当前任务-流程定义：" + activityImpl.getProcessDefinition()); // 输出某个节点的某种属性
                    System.out.println("当前任务-是不是排他网关：" + activityImpl.isExclusive()); // 输出某个节点的某种属性
                    System.out.println("当前任务-Variables：" + activityImpl.getVariables()); // 输出某个节点的某种属性
                    System.out.println("当前任务-目前路线executor：" + activityImpl.getIncomingTransitions()); // 输出某个节点的某种属性
                    System.out.println("当前任务-之后路线executor：" + activityImpl.getOutgoingTransitions()); // 输出某个节点的某种属性
                    List<PvmTransition> outTransitions = activityImpl.getOutgoingTransitions();// 获取从某个节点出来的所有线路
                    for (PvmTransition tr : outTransitions) {
                        PvmActivity ac = tr.getDestination(); // 获取线路的终点节点
                        System.out.println("===========================================");
                        System.out.println("下一步任务任务-name：" + ac.getProperty("name"));
                        System.out.println("下一步任务任务-id：" + ac.getId()); // 输出某个节点的某种属性
                        System.out.println("下一步任务任务-info：" + ac.toString());
                        System.out.println("下一步任务任务-流程定义：" + ac.getProcessDefinition()); // 输出某个节点的某种属性
                        System.out.println("下一步任务任务-是不是排他网关：" + ac.isExclusive()); // 输出某个节点的某种属性
                    }
                    break;
                }
            }
        }
    }
	
	/**
	 * 
	* @Title: getAllActNodes.java  
	* @Package com.welab.hsj.getAllActNodes  
	* @Description: TODO(根据节点flowElementId和processDefinitionId获取OutgoingFlows连线信息获取)  
	* @author hsj 
	* @date 2018年10月24日  
	* @version V1.0
	 */
	@SuppressWarnings("unused")
	@Test
	public void getLineInfo() {
		String flowElementId = "exclusivegateway1";
		
		RepositoryService repositoryService2 = pe.getRepositoryService();
		// processDefinitionId 对应表ACT_RE_PROCDEF主键信息
		String processDefinitionId = "exclusiveGateWay:1:7504";//sequenceFlow:1:2504
		// 获取bpmnModel对象
		BpmnModel bpmnModel = repositoryService2.getBpmnModel(processDefinitionId);
		// 因为我们这里只定义了一个Process 所以获取集合中的第一个即可
		List<org.activiti.bpmn.model.Process> listprocess = bpmnModel.getProcesses();

	    for(org.activiti.bpmn.model.Process a:listprocess){
	    	// 获取所有的FlowElement信息
			Collection<FlowElement> flowElements = a.getFlowElements();
			for (FlowElement flowElement : flowElements) {
				
				if (flowElementId.equals(flowElement.getId())) {
					// 获取入线信息
					List<SequenceFlow> outGoingFlows = ((FlowNode) flowElement).getOutgoingFlows();
					for (SequenceFlow sequenceFlow : outGoingFlows) {
						System.out.println(sequenceFlow.getId() + "-" + sequenceFlow.getConditionExpression() + "--"
								+ sequenceFlow.getDocumentation() + "-" + sequenceFlow.getSourceRef() + "--"
								+ sequenceFlow.getTargetRef() + "-");
					}
					
				}
			}
	    }
	}

}
