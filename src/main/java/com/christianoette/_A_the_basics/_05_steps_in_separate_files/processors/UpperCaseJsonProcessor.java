package com.christianoette._A_the_basics._05_steps_in_separate_files.processors;

import com.christianoette._A_the_basics._05_steps_in_separate_files.dto.InputData;
import com.christianoette._A_the_basics._05_steps_in_separate_files.dto.OutputData;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class UpperCaseJsonProcessor implements ItemProcessor<InputData, OutputData> {

    @Override
    public OutputData process(InputData inputData) throws Exception {
        OutputData outputData = new OutputData();
        outputData.value = inputData.value.toUpperCase();
        return outputData;
    }
}
