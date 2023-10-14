package com.everyapart.batch.job

import com.everyapart.batch.application.tasklet.SendMailTasklet
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.context.annotation.Bean
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.stereotype.Component

@Component
class SendMailJobConfig(
    val jobRepository: JobRepository,
    val sendMailTasklet: SendMailTasklet,
    val transactionManager: JpaTransactionManager
) {

    @Bean
    fun sendMailJob(sendMailStep: Step): Job {
        return JobBuilder("sendMailJob", jobRepository)
            .start(sendMailStep)
            .build()
    }

    @Bean
    fun sendMailStep(): Step {
        return StepBuilder("sendMailStep", jobRepository)
            .tasklet(sendMailTasklet, transactionManager)
            .build()
    }


}
