package com.welab.hsj.getAllActNodes;

import java.util.Collection;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;
/**
 * Activiti工作流之获取流程定义中所有的节点
 * see:https://blog.csdn.net/hiyohu/article/details/51682005
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
	* @Description: TODO(Activiti工作流之获取流程定义中所有的节点-流程部署至服务器上之后可使用-只获取UserTask节点)  
	* @author hsj 
	* @date 2018年10月23日  
	* @version V1.0
	 */
	@Test
	public void getDeploymentNodes(){
		BpmnModel model = pe.getRepositoryService().getBpmnModel("sequenceFlow:1:2504");
		
		if(model != null) {
		    Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();
		    
		    for(FlowElement e : flowElements) {
		    	
		        if(e.getName() != null && !"".equals(e.getName()) && "class org.activiti.bpmn.model.UserTask".equals(e.getClass().toString())){
		        	System.out.println("=============><==============="+e.toString());
		        	System.out.println("flowelement id:" + e.getId() + "  name:" + e.getName() + "   class:" + e.getClass().toString());
		        }
		    }
		}

	}
}
