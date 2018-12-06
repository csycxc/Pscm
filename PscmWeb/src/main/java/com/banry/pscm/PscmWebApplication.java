package com.banry.pscm;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


/**
 * 总的启动项目 
 *
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class, scanBasePackages = "com.banry.pscm")//(exclude={JpaRepositoriesAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
@MapperScan("com.banry.pscm.persist")
//@ImportResource(locations={"classpath:activiti.cfg.xml","classpath:applicationContext-security.xml"})
//@EnableTransactionManagement
@EnableWebSecurity
public class PscmWebApplication 
{
	
//	@Bean
//    public PlatformTransactionManager txManager(DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
	
    public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(PscmWebApplication.class);		
		springApplication.addListeners(new ApplicationStartup());
        springApplication.run(args);   
	}
}
