package com.banry.pscm.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.CollectionUtils;

import com.banry.pscm.workflow.ProcessCompletedListener;
import com.banry.pscm.workflow.TaskCompletedListener;
import com.banry.pscm.workflow.TaskCreateListener;
import com.banry.pscm.workflow.util.ActivitiProperties;

@Configuration
//@EnableConfigurationProperties(ActivitiProperties.class)
public class ActivitiConfig {

    @Autowired
    private TaskCompletedListener taskCompletedListener;
    @Autowired
    private ProcessCompletedListener processCompletedListener;
    @Autowired
    private TaskCreateListener taskCreateListener;
    @Autowired
    @Qualifier("dynamicDataSource")
    private DataSource dataSource;
    @Autowired
    private PlatformTransactionManager transactionManager;
    @Autowired
    private ActivitiProperties activitiProperties;
    @Autowired
    private ResourcePatternResolver resourceLoader;

    @Bean
    public SpringProcessEngineConfiguration processEngineConfiguration() throws IOException {
    	SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration();
        configuration.setDataSource(dataSource);
        configuration.setTransactionManager(transactionManager);
        
        configuration.setDatabaseSchemaUpdate(activitiProperties.getDatabaseSchemaUpdate());
        configuration.setDbIdentityUsed(activitiProperties.isDbIdentityUsed());
        configuration.setAsyncExecutorActivate(activitiProperties.isAsyncExecutorActivate());
        configuration.setHistory(activitiProperties.getHistoryLevel().getKey());
        configuration.setCreateDiagramOnDeploy(activitiProperties.isCreateDiagramOnDeploy());
        configuration.setActivityFontName(activitiProperties.getActivityFontName());
        configuration.setLabelFontName(activitiProperties.getLabelFontName());
        //todo 修改自动部署，当前自动部署直接搬自[activit-spring-boot]
        //如果checkProcessDefinitions为true，则发布新版流程定义，后续可能根据流程定义文件MD5等判断是否真正变化而进行发布
        List<Resource> procDefResources = discoverProcessDefinitionResources(activitiProperties.getProcessDefinitionLocationPrefix(),
        		activitiProperties.getProcessDefinitionLocationSuffixes(),this.activitiProperties.isCheckProcessDefinitions());
        configuration.setDeploymentResources(procDefResources.toArray(new Resource[procDefResources.size()]));
        
    	Map<String, List<ActivitiEventListener>> typedListeners = new HashMap<String, List<ActivitiEventListener>>();
        List<ActivitiEventListener> taskCompletedList = new ArrayList<ActivitiEventListener>();
        taskCompletedList.add(taskCompletedListener);
        List<ActivitiEventListener> taskCreateList = new ArrayList<ActivitiEventListener>();
        taskCreateList.add(taskCreateListener);
        List<ActivitiEventListener> processCompletedList = new ArrayList<ActivitiEventListener>();
        processCompletedList.add(processCompletedListener);
        //配置全局监听器
        typedListeners.put("TASK_COMPLETED", taskCompletedList);
        typedListeners.put("TASK_CREATED", taskCreateList);
        typedListeners.put("PROCESS_COMPLETED", processCompletedList);
        
//        taskCompletedList.add(taskCreateListener);
//        taskCompletedList.add(processCompletedListener);

        configuration.setTypedEventListeners(typedListeners);
		return configuration;
    }
    
    private List<Resource> discoverProcessDefinitionResources(String prefix, List<String> suffixes, boolean checkPDs) throws IOException {
        if (checkPDs) {
            List<Resource> result = new ArrayList<>();
            for (String suffix : suffixes) {
                String path = prefix + suffix;
                Resource[] resources = resourceLoader.getResources(path);
                if (resources != null && resources.length > 0) {
                    CollectionUtils.mergeArrayIntoCollection(resources, result);
                }
            }
            return result;
        }
        return new ArrayList<>();
    }
    
    @Bean
    public ProcessEngineFactoryBean processEngine() throws IOException {
        ProcessEngineFactoryBean factoryBean = new ProcessEngineFactoryBean();
        factoryBean.setProcessEngineConfiguration(processEngineConfiguration());
        return factoryBean;
    }
    @Bean
    public RuntimeService runtimeService(ProcessEngine processEngine) {
        return processEngine.getRuntimeService();
    }
    @Bean
    public RepositoryService repositoryService(ProcessEngine processEngine) {
        return processEngine.getRepositoryService();
    }
    @Bean
    public TaskService taskService(ProcessEngine processEngine) {
        return processEngine.getTaskService();
    }
    @Bean
    public HistoryService historyService(ProcessEngine processEngine) {
        return processEngine.getHistoryService();
    }
    @Bean
    public ManagementService managementService(ProcessEngine processEngine) {
        return processEngine.getManagementService();
    }
    @Bean
    public IdentityService identityService(ProcessEngine processEngine) {
        return processEngine.getIdentityService();
    }

}
