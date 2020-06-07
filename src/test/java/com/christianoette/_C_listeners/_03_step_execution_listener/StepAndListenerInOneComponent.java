package com.christianoette._C_listeners._03_step_execution_listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class StepAndListenerInOneComponent implements Tasklet, StepExecutionListener {

    private int intermediateResult;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        stepExecution.getExecutionContext();
    }

    @Override
    public RepeatStatus execute(StepContribution contribution,
                                ChunkContext chunkContext) {
        intermediateResult = 42;
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        stepExecution.getJobExecution().getExecutionContext()
                .putInt("intermediateResult", intermediateResult);
        return stepExecution.getExitStatus();
    }
}
