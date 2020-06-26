package com.christianoette.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.util.ResourceUtils;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class CourseUtilJobSummaryListener implements JobExecutionListener {

    private static final Logger LOGGER = LogManager.getLogger(CourseUtilJobSummaryListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
            cfg.setDirectoryForTemplateLoading(ResourceUtils.getFile("classpath:utils/templates"));
            cfg.setDefaultEncoding("UTF-8");
            Template template = cfg.getTemplate("summary.ftl");
            Map<String, Object> templateData = new HashMap<>();
            templateData.put("jobExecution", jobExecution);
            try (StringWriter out = new StringWriter()) {
                template.process(templateData, out);
                System.out.println(out.getBuffer().toString());
                out.flush();
            }
        } catch (Exception ex) {
            LOGGER.error("Couldn't create summary", ex);
        }
    }
}
