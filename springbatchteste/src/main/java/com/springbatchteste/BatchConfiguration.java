package com.springbatchteste;

import javax.annotation.PostConstruct;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	
	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Autowired
    public JobLauncher jobLauncher;
 
    @Autowired
    public Job job;
        
    @Bean
    ItemWriter<Employee> writer() {
    	return new ClassWriter();
    }
    
    @Bean 
    ItemReader<Employee> reader() {
    	return new ClassReader();
    }
    
    @Bean 
    ItemProcessor<Employee, Employee>  processor() {
    	return new ClassProcessor();
    }
    
    @Bean
    public Job job1() throws Exception {
		return jobBuilderFactory.get("job1").incrementer(new RunIdIncrementer()).flow(step1()).end().build();
    }
    
    @Bean
    public Step step1() throws Exception {
    	return stepBuilderFactory.get("step1").<Employee, Employee>chunk(10).reader(reader()).processor(processor()).writer(writer()).build();
    }
    
    @PostConstruct
    public void init() {
    	try {
        	JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(job, jobParameters);
        } catch (Exception e) {
           System.out.println(e.getMessage());
        }
    }
}
