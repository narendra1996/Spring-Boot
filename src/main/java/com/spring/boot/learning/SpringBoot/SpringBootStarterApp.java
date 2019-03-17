package com.spring.boot.learning.SpringBoot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.spring.boot.controller.CourseController;
import com.spring.boot.controller.EmployeeController;

@ComponentScan("com.spring.boot.dao")
@ComponentScan("com.spring.boot.controller")
@EnableJpaRepositories("com.spring.boot.repository")
@RestController
@EnableAutoConfiguration
@SpringBootApplication
public class SpringBootStarterApp extends SpringBootServletInitializer{
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	      return application.sources(SpringBootStarterApp.class);
	   }

	private static final Logger logger = LoggerFactory.getLogger(SpringBootStarterApp.class);
	public static void main(String[] args) {
	
		logger.info("Inside Spring-Boot Main Method");	
		SpringApplication.run(SpringBootStarterApp.class, args);
//		SpringApplication.run(EmployeeController.class, args);
//		SpringApplication.run(CourseController.class, args);
		
	}
	/*@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/*").allowedOrigins("http://localhost:4200");
            }
        };
    }*/
	
	@Configuration
	@EnableWebMvc
	public class CorsConfiguration implements WebMvcConfigurer 
	{
	    @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**")
	                .allowedMethods("GET", "POST");
	    }
	}
	
	   @RequestMapping(value = "/hello")
	   public String hello() {
	      return "Hello World from Tomcat";
	   }
	   
	   
}
