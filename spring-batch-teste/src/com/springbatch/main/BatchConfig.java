package com.springbatch.main;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import com.springbatch.beans.Employee;

@Configuration
public class BatchConfig {

	@Bean
    @Primary
    public DataSource dataSource()
    {
        return new SimpleDriverDataSource();
    }
	
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new ResourcelessTransactionManager();
    }

    @Bean
    public JobRepository jobRepository() throws Exception {
        return new MapJobRepositoryFactoryBean(transactionManager()).getObject();
    }

    @Bean
    public JobLauncher jobLauncher() throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository());
        return jobLauncher;
    }
    
    @Bean
    public JobBuilderFactory jobBuilderFactory() throws Exception {
    	JobBuilderFactory jobBuilderFactory = new JobBuilderFactory(jobRepository());
		return jobBuilderFactory;
    }
    
    @Bean
    public StepBuilderFactory stepBuilderFactory() throws Exception {
    	StepBuilderFactory  stepBuilderFactory  = new StepBuilderFactory(jobRepository(), transactionManager());
		return stepBuilderFactory;
    }

	@Bean
	public ClassReader reader() {
		return new ClassReader();
	}

	@Bean
	public ClassWriter writer() {
		return new ClassWriter();
	}

	@Bean
	public ClassProcessor processor() {
		return new ClassProcessor();
	}
	
	
	@Bean
	public Job processJob() throws Exception {
		return jobBuilderFactory().get("processJob").incrementer(new RunIdIncrementer()).flow(step1()).end().build();
	}

	@Bean
	public Step step1() throws Exception {
		return stepBuilderFactory().get("step1").<Employee, Employee>chunk(1).reader(reader()).processor(processor()).writer(writer()).build();
	}
}
