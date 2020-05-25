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
import org.springframework.batch.item.*;
import org.springframework.batch.item.support.PassThroughItemProcessor;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {StreamTest.TestConfig.class, CourseUtilBatchTestConfig.class})
class StreamTest {

    private static final Logger LOGGER = LogManager.getLogger(StreamTest.class);

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

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
            taskExecutor.setCorePoolSize(5);
            taskExecutor.setMaxPoolSize(5);
            taskExecutor.afterPropertiesSet();

            SimpleStepBuilder<String, String> chunk = stepBuilderFactory.get("jsonItemReader")
                    .repository(jobRepository)
                    .chunk(2);

            return chunk
                    .reader(customStreamReader())
                    .processor(new PassThroughItemProcessor<>())
                    .writer(getCustomWriter())
                    .taskExecutor(taskExecutor)
                    .build();
        }

        List<String> list = List.of("a", "b", "c", "d", "e", "f", "g", "h", "i", "j");
        AtomicInteger count = new AtomicInteger(0);

        private ItemReader<String> customStreamReader() {
            return () -> {

                Thread.sleep(3000);
                String item = readNextString();
                LOGGER.info("Read {}", item);
                return item;

            };
        }

        private synchronized String readNextString() {
            int index = count.get();
            if (index == list.size()) {
                return null;
            }
            String item = list.get(index);
            count.getAndIncrement();
            return item;
        }

        private ItemStreamWriter<String> getCustomWriter() {
            return new ItemStreamWriter<String>() {
                @Override
                public void open(ExecutionContext executionContext) throws ItemStreamException {

                }

                @Override
                public void update(ExecutionContext executionContext) throws ItemStreamException {

                }

                @Override
                public void close() throws ItemStreamException {

                }

                @Override
                public void write(List<? extends String> list) throws Exception {
                    {
                        Thread.sleep(200);
                        LOGGER.info("Write {}", list);
                    }
                }
            };
        }
    }

}
