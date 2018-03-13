package com.welab.hsj.alldemo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
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
	
	//流程定义的key-相当于流程的id
	public final static String proDedfKey = "helloWord";
	
	//张三-申请者
	public final static String zhangsan = "张三";
	//李四-部门经理
	public final static String lisi = "李四";
	//王五-总经理
	public final static String wangwu = "王五";

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
}
