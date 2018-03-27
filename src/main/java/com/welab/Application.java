package com.welab;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.activiti.spring.SpringProcessEngineConfiguration;

import com.welab.hsj.activiti.JsonpCallbackFilter;


/**
 * Spring Boot 应用启动类
 *
 */
// Spring Boot 应用的标识
@SpringBootApplication
@ComponentScan({"org.activiti.rest.diagram", "com.welab"})
//排除activiti使用的spring-security模块参见下面Stack Overflow链接
@EnableAutoConfiguration(exclude = {//https://stackoverflow.com/questions/32171554/how-to-disable-activiti-rest-http
  org.activiti.spring.boot.RestApiAutoConfiguration.class,
  org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class,
  org.activiti.spring.boot.SecurityAutoConfiguration.class,
  org.springframework.boot.actuate.autoconfigure.ManagementWebSecurityAutoConfiguration.class
}) 
public class Application extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public JsonpCallbackFilter filter(){
		return new JsonpCallbackFilter();
	}
}

