package com.christianoette._A_the_basics._05_steps_in_separate_files.config;

import com.christianoette._A_the_basics._05_steps_in_separate_files.dto.InputData;
import com.christianoette.utils.CourseUtils;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class ReaderConfiguration {

    private final StepBuilderFactory stepBuilderFactory;

    public ReaderConfiguration(StepBuilderFactory stepBuilderFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean("myJsonItemReader")
    @StepScope
    public ItemStreamReader<InputData> reader(@Value("#{jobParameters['inputPath']}") String inputPath) {
        Resource inputResource = CourseUtils.getFileResource(inputPath);

        return new JsonItemReaderBuilder<InputData>()
                .jsonObjectReader(new JacksonJsonObjectReader<>(InputData.class))
                .resource(inputResource)
                .name("jsonItemReader")
                .build();
    }
}
