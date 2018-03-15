package com.welab.hsj.alldemo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
/**
 * 该列子因为是写死的申请流程里面的人
 * @author hsj
 *
 */
public class ProcessesDefinitionDeployementTest {
	ProcessEngine pe = ProcessEngines.getDefaultProcessEngine();

	/**
	 * springboot会自动部署resources下面的processes文件直接目录下面的流程定义文件【只会第一子集，下面嵌套不会部署】
	 * -这里练习自己部署
	 */
	@Test
	public void deployementProcessDifined(){
		Deployment deployment = pe.getRepositoryService()
		  .createDeployment()
		  .name("helloword入门程序")
		  .addClasspathResource("processes/hsj/helloword.bpmn")
		  .addClasspathResource("processes/hsj/helloword.png")
		  .deploy();
		
		System.out.println("部署id="+deployment.getId());
		System.out.println("部署名字="+deployment.getName());
	}
	
	/**
	 * zip加载
	 * springboot会自动部署resources下面的processes文件直接目录下面的流程定义文件【只会第一子集，下面嵌套不会部署】
	 * -这里练习自己部署
	 */
	@Test
	public void deployementProcessDifinedByZip(){
		ZipInputStream zipInputStream = new ZipInputStream(
				this.getClass().getClassLoader().getResourceAsStream("processes/hsj/helloword.zip")
				);
		Deployment deployment = pe.getRepositoryService()
		  .createDeployment()
		  .name("helloword入门程序")
		  .addZipInputStream(zipInputStream)
		  .deploy();
		
		System.out.println("部署id="+deployment.getId());
		System.out.println("部署名字="+deployment.getName());
	}
	
	/**
	 * 查询流程定义
	 */
	@Test
	public void queryProcessesDefintion(){
		List<ProcessDefinition>  ld = pe.getRepositoryService().createProcessDefinitionQuery()
		  .orderByProcessDefinitionVersion().asc().list();
		
		if(ld != null && ld.size() > 0){
			for(ProcessDefinition pd:ld){
				System.out.println("-------分割线--------");
				System.out.println("流程定义的id="+pd.getId());
				System.out.println("流程定义的名称="+pd.getName());
				System.out.println("流程定义的key="+pd.getKey());
				System.out.println("流程定义的version="+pd.getVersion());
				System.out.println("流程定义的DeployementId="+pd.getDeploymentId());
			}
		}
	}
	
	/**
	 * 删除流程定义和部署
	 */
	@Test
	public void deleteProcessesDefintion(){
		String deploymentId = "5001";
		//默认不带级联删除-只能删除没有启动的流程-如果流程启动就会抛出异常
		pe.getRepositoryService().deleteDeployment(deploymentId);
		System.out.println("删除成功");
	}
	
	/**
	 * bpmn对应的png图片写到磁盘目录下面
	 */
	@Test
	public void viewPng(){
		String deploymentId = "5001";
		//获取图片资源
		List<String> ls = pe.getRepositoryService().getDeploymentResourceNames(deploymentId);
		//图片不是bpmn文件
		String sp = null;
		if(ls!= null && ls.size()>0){
			for(String s:ls){
				if(s.indexOf(".png")>=0){
					sp = s;
					break;
				}
			}
		}
		//图片的输入流
		InputStream is = pe.getRepositoryService().getResourceAsStream(deploymentId, sp);
		//生成图片到某个目录
		try {
			FileUtils.copyInputStreamToFile(is, new File("E:/activiti5_test/"+sp));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 查找最新版本的流程定义
	 */
	@Test
	@SuppressWarnings("unused")
	public void findLastVersionProcessesDefinition(){
		List<ProcessDefinition> lpd = pe.getRepositoryService().createProcessDefinitionQuery()
		     .orderByProcessDefinitionVersion().asc().list();
		//过滤最新版本
		Map<String,ProcessDefinition> map = null;
		if(lpd != null && lpd.size() > 0){
			map =new LinkedHashMap<String,ProcessDefinition>();
			for(ProcessDefinition pd:lpd){
				map.put(pd.getKey(), pd);
			}
		}
		List<ProcessDefinition> lpdlast = null;
		if(map != null && map.size()>0){
			//map转list
			lpdlast = new ArrayList<ProcessDefinition>(map.values());
		}
		//输出最新版本的内容
		if(lpdlast != null && lpdlast.size() > 0){
			for(ProcessDefinition pdlast:lpdlast){
				System.out.println("-------分割线--------");
				System.out.println("流程定义的id="+pdlast.getId());
				System.out.println("流程定义的名称="+pdlast.getName());
				System.out.println("流程定义的key="+pdlast.getKey());
				System.out.println("流程定义的version="+pdlast.getVersion());
				System.out.println("流程定义的DeployementId="+pdlast.getDeploymentId());
			}
		}
	}
	
	/**
	 * 根据流程的key删除相同key的流程定义的不同版本
	 */
	@Test
	public void deleteProcessesDefintionByProcesseskey(){
		List<ProcessDefinition> lpd = pe.getRepositoryService().createProcessDefinitionQuery()
		    .processDefinitionKey(ConstantInfo.ProcessesInfo.proDedfKey).list();
		
		if(lpd!=null && lpd.size()>0){
			for(ProcessDefinition pd:lpd){
				//级联删除，不管没有没实例
				pe.getRepositoryService().deleteDeployment(pd.getDeploymentId(), true);
			}
			System.out.println(String.format("删除%s个流程定义", lpd.size()));
		}
	}
}
