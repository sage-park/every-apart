package com.everyapart.batch.scheduler

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class BatchScheduler(
    val jobLauncher: JobLauncher,
    val sendMailJob: Job
) {

    @Scheduled(fixedRate = 1000) // 1초마다 실행
//    @Scheduled(cron = "0 0 0 1 * ?") //매월 1일에 실행
    fun sendMail() {
        val jobParameters = JobParametersBuilder()
            .addLong("time", System.currentTimeMillis())
            .toJobParameters()

        jobLauncher.run(sendMailJob, jobParameters)
    }


}
