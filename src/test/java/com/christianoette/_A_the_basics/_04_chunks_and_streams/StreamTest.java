package com.christianoette._A_the_basics._04_chunks_and_streams;

import com.christianoette.testutils.CourseUtilBatchTestConfig;
import com.christianoette.utils.CourseUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.PassThroughItemProcessor;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {StreamTest.TestConfig.class, CourseUtilBatchTestConfig.class})
class StreamTest {

    private static final Logger LOGGER = LogManager.getLogger(StreamTest.class);

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    private volatile static Deque<String> items = new LinkedList<>(
            List.of("a", "b", "c", "d", "e", "f", "g", "h", "i", "j"));

    private static synchronized String readNextItem() {
        return items.pollFirst();
    }

    @Test
    void runJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .toJobParameters();

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
    }

    @SuppressWarnings("WeakerAccess")
    @Configuration
    static class TestConfig {

        @Autowired
        private JobBuilderFactory jobBuilderFactory;

        @Autowired
        private JobRepository jobRepository;

        @Autowired
        private StepBuilderFactory stepBuilderFactory;


        @Bean
        public Job job() {
            return jobBuilderFactory.get("myJob")
                    .start(step())
                    .build();
        }

        @Bean
        public Step step() {
            ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
            taskExecutor.setCorePoolSize(4);
            taskExecutor.setMaxPoolSize(4);
            taskExecutor.afterPropertiesSet();
            SimpleStepBuilder<String, String> chunk = stepBuilderFactory.get("jsonItemReader")
                    .repository(jobRepository)
                    .chunk(2);

            return chunk
                    .reader(createItemReader())
                    .processor(new PassThroughItemProcessor<>())
                    .writer(createItemWriter())
                    .taskExecutor(taskExecutor)
                    .build();
        }

        private ItemWriter<? super String> createItemWriter() {
            return (ItemWriter<String>) items -> {
                LOGGER.info("Write {}", items);
                CourseUtils.sleep(200);
            };
        }

        private ItemReader<? extends String> createItemReader() {
            return (ItemReader<String>) () -> {
                String item = readNextItem();
                LOGGER.info("Read {}", item);
                CourseUtils.sleep(1000);
                return item;
            };
        }

    }

}
