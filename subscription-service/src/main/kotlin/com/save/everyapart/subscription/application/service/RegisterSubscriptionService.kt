package com.save.everyapart.subscription.application.service

import com.save.everyapart.subscription.application.port.`in`.RegisterSubscriptionCommand
import com.save.everyapart.subscription.application.port.`in`.RegisterSubscriptionUsecase
import com.save.everyapart.subscription.application.port.out.MembershipPort
import com.save.everyapart.subscription.application.port.out.RegionPort
import com.save.everyapart.subscription.application.port.out.SubscriptionPort
import com.save.everyapart.subscription.domain.Subscription
import com.save.everyapart.subscription.domain.SubscriptionId
import com.save.everyapart.subscription.exception.InvalidTokenException
import com.save.everyapart.subscription.exception.RegionNotExistException
import org.springframework.stereotype.Service
import java.util.*

@Service
class RegisterSubscriptionService(
    val membershipPort: MembershipPort,
    val regionPort : RegionPort,
    val subscriptionPort : SubscriptionPort
) : RegisterSubscriptionUsecase {

    override fun register(command: RegisterSubscriptionCommand): SubscriptionId {

        // 1. membership 이 유효한지 확인하고, 멤버쉽 정보를 가져온다.
        val isValid = membershipPort.isValid(command.token)
        if (!isValid) throw InvalidTokenException("token이 유효하지 않습니다.")

        val membership = membershipPort.getMembershipInfo(command.token)

        // 2. regionCode 가 실제 있는지 확인한다.
        val regionCode = command.regionCode
        val isRegionCodeExist = regionPort.exist(regionCode)
        if (!isRegionCodeExist) throw RegionNotExistException("regionCode(${regionCode.regionCode})가 존재하지 않습니다.")

        // 3. subscription 을 추가한다.
        val subscription = Subscription(
            membershipId = membership.membershipId,
            regionCode = regionCode,
            active = true,
            subscriptionId = SubscriptionId(UUID.randomUUID().toString()),
            emailAddress = command.emailAddress
        )

        subscriptionPort.save(subscription)

        return subscription.subscriptionId

    }

}
