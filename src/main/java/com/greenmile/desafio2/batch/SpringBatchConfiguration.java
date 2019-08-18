package com.greenmile.desafio2.batch;

import org.springframework.batch.core.Step;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.greenmile.desafio2.entities.CSV;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfiguration {
	
	@Bean
	public Job job(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
			ItemReader<CSV> itemReader, ItemProcessor<CSV, CSV> itemProcessor, ItemWriter<CSV> itemWriter) {

		Step step = stepBuilderFactory.get("ETL-file-load").<CSV, CSV>chunk(100).reader(itemReader)
				.processor(itemProcessor).writer(itemWriter).build();

		return jobBuilderFactory.get("ETL-load").incrementer(new RunIdIncrementer()).start(step).build();
	}

	@Bean
	public FlatFileItemReader<CSV> fileItemReader() {
		FlatFileItemReader<CSV> flatFileItemReader = new FlatFileItemReader<CSV>();
		flatFileItemReader.setResource(new ClassPathResource("data/gm-challenge.csv"));
		flatFileItemReader.setName("CSV Reade");
		flatFileItemReader.setLinesToSkip(1);
		flatFileItemReader.setLineMapper(lineMapper());
		return flatFileItemReader;
	}

	private LineMapper<CSV> lineMapper() {
		DefaultLineMapper<CSV> dLineMapper = new DefaultLineMapper<CSV>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames(new String[] { "# ProjectName", "PlannedStart", "PlannedEnd", "PM", "PMEmail",
				"PMSkills", "EmployeeName", "EmployeeEmail", "EmployeeTeam", "EmployeeSkills" });
		
		BeanWrapperFieldSetMapper<CSV> fieldSetMapper = new BeanWrapperFieldSetMapper<CSV>();
		fieldSetMapper.setTargetType(CSV.class);
			
		dLineMapper.setLineTokenizer(lineTokenizer);
		dLineMapper.setFieldSetMapper(fieldSetMapper);

		return dLineMapper;
	}
	
}
