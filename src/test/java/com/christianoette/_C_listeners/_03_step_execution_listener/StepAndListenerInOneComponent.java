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
<<<<<<< HEAD

    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
=======
        stepExecution.getExecutionContext();
    }

    @Override
    public RepeatStatus execute(StepContribution contribution,
                                ChunkContext chunkContext) {
>>>>>>> parent of 7410ca8... StepAndListenerInOneComponent prepared
        intermediateResult = 42;
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        stepExecution.getJobExecution().getExecutionContext()
<<<<<<< HEAD
                .putInt("intermediateResult",intermediateResult);
=======
                .putInt("intermediateResult", intermediateResult);
>>>>>>> parent of 7410ca8... StepAndListenerInOneComponent prepared
        return stepExecution.getExitStatus();
    }
}
