package com.christianoette._A_the_basics._05_steps_in_separate_files.config;

import com.christianoette._A_the_basics._05_steps_in_separate_files.dto.InputData;
import com.christianoette._A_the_basics._05_steps_in_separate_files.dto.OutputData;
import com.christianoette._A_the_basics._05_steps_in_separate_files.processors.UpperCaseJsonProcessor;
import com.christianoette.utils.CourseUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final JobRepository jobRepository;
    private final UpperCaseJsonProcessor upperCaseJsonProcessor;
    private final StepBuilderFactory stepBuilderFactory;

    public BatchConfig(JobBuilderFactory jobBuilderFactory,
                       JobRepository jobRepository,
                       UpperCaseJsonProcessor upperCaseJsonProcessor,
                       StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.jobRepository = jobRepository;
        this.upperCaseJsonProcessor = upperCaseJsonProcessor;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job job() {
        return jobBuilderFactory.get("myJob")
                .start(step())
                .build();
    }

    @Bean
    public Step step() {
        SimpleStepBuilder<InputData, OutputData> chunk =
                stepBuilderFactory.get("jsonItemReader")
                .repository(jobRepository)
                .chunk(1);

        return chunk.reader(reader())
                .processor(upperCaseJsonProcessor)
                .writer(writer())
                .build();
    }

    @Bean
    public JsonItemReader<InputData> reader() {
        Resource inputResource = CourseUtils.getFileResource("classpath:files/_A/input.json");

        return new JsonItemReaderBuilder<InputData>()
                .jsonObjectReader(new JacksonJsonObjectReader<>(InputData.class))
                .resource(inputResource)
                .name("tradeJsonItemReader")
                .build();
    }

    @Bean
    public JsonFileItemWriter<OutputData> writer() {
        Resource outputResource = new FileSystemResource("output/output.json");

        return new JsonFileItemWriterBuilder<OutputData>()
                .jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
                .resource(outputResource)
                .name("tradeJsonItemReader")
                .build();
    }
}
