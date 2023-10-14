package com.everyapart.batch.application.tasklet

import com.everyapart.batch.application.port.out.ApartmentTransactionPort
import com.everyapart.batch.application.port.out.MessageBrokerPort
import com.everyapart.batch.application.port.out.SubscriptionPort
import com.everyapart.batch.domain.Subscription
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component

@Component
class SendMailTasklet(
    val subscriptionPort: SubscriptionPort,
    val apartmentTransactionPort: ApartmentTransactionPort,
    val messageBrokerPort: MessageBrokerPort
): Tasklet {
    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {

        // 1. 메일보낼사람 확인
         val subscriptions:List<Subscription>  = subscriptionPort.getAll()

        // 2. 아파트 실거래가 조회
        for (subscription in subscriptions) {

            val apartmentTransactions = apartmentTransactionPort.getCurrentMonthTransactions(
                regionCode = subscription.regionCode,
            )

            // 3. 메일 전송이벤트 발행
            messageBrokerPort.send(apartmentTransactions)

            // 4. 배치 결과 저장
        }

        return RepeatStatus.FINISHED
    }

}
