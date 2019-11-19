<#-- @ftlvariable name="jobExecution" type="org.springframework.batch.core.JobExecution" -->

    -------------------------------------------------------------------
      __        _        __
      \ \  ___ | |__    /  _\_   _ _ __ ___  _ __ ___   __ _ _ __ _   _
       \ \/   \|  _ \   \  \| | | |  _   _ \|  _   _ \ / _  |  __| | | |
    /\_/ / (_) | |_) |  _\  \ |_| | | | | | | | | | | | (_| | |  | |_| |
    \___/ \___/|____/   \__/ \__,_|_| |_| |_|_| |_| |_|\__,_|_|   \__  |
                                                                  |___/
    -------------------------------------------------------------------

<#if jobExecution??>
    Job name: ${jobExecution.jobInstance.jobName!""}
    Parameters ${jobExecution.jobParameters!""}
    Status: ${jobExecution.status!""}

</#if>
