package com.save.everyapart.subscription.application.port.out

import com.save.everyapart.subscription.domain.RegionCode

interface RegionPort {
    fun exist(regionCode: RegionCode): Boolean

}
