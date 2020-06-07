<#-- @ftlvariable name="jobExecution" type="org.springframework.batch.core.JobExecution" -->
<#-- @ftlvariable name="stepExecution" type="org.springframework.batch.core.StepExecution" -->

<#macro fg color>${'\x1B'}[${30 + color}m</#macro>

<#--Blue color macro-->
<@fg 4 />

    -------------------------------------------------------------------
      __        _        __
      \ \  ___ | |__    /  _\_   _ _ __ ___  _ __ ___   __ _ _ __ _   _
       \ \/   \|  _ \   \  \| | | |  _   _ \|  _   _ \ / _  |  __| | | |
    /\_/ / (_) | |_) |  _\  \ |_| | | | | | | | | | | | (_| | |  | |_| |
    \___/ \___/|____/   \__/ \__,_|_| |_| |_|_| |_| |_|\__,_|_|   \__  |
                                                                  |___/
    -------------------------------------------------------------------


<#if jobExecution??>
    <#if jobExecution.jobInstance??>
        Job name: ${jobExecution.jobInstance.jobName!""}
        Job InstanceId: ${jobExecution.jobInstance.instanceId!""}
    <#else>
        JobInstance not found
    </#if>
        Parameters ${jobExecution.jobParameters!""}
        Running: ${jobExecution.running?string('yes', 'no')!""}
        Status: ${jobExecution.status!""}
        ExitStatus: ${jobExecution.exitStatus?truncate(100)!""}
        StartTime: ${jobExecution.startTime?string('HH:mm:ss')!""}
        EndTime: ${jobExecution.endTime?string('HH:mm:ss')!""}
        Steps:
        <#list jobExecution.stepExecutions as stepExecution>
             ${stepExecution.stepName!""}
                - Id: ${stepExecution.id!""}
                - Status: ${stepExecution.status!""}
                - ExitStatus: ${stepExecution.exitStatus?truncate(100)!""}
                - ReadCount | WriteCount | FilterCount: ${stepExecution.readCount!""} | ${stepExecution.writeCount!""} | ${stepExecution.filterCount!""}
                - ReadSkipCount | WriteSkipCount | ProcessSkipCount: ${stepExecution.readSkipCount!""} | ${stepExecution.writeSkipCount!""} | ${stepExecution.processSkipCount!""}
                - CommitCount: ${stepExecution.commitCount!""}
                - RollbackCount: ${stepExecution.rollbackCount!""}
        </#list> <#--Red Color macro--> <@fg 1 />
        Errors:
        <#list jobExecution.allFailureExceptions as failureException>
            - ${failureException}
        </#list>

</#if>
