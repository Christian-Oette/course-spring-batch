package com.christianoette._F_controlling_flow._03_decider;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

public class YesNoDecider implements JobExecutionDecider {

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution,
                                      StepExecution stepExecution) {
        Boolean succeeded = (Boolean) stepExecution.getExecutionContext().get("succeeded");
        if (succeeded) {
            return new FlowExecutionStatus("YES");
        } else {
            return new FlowExecutionStatus("NO");
        }
    }
}
