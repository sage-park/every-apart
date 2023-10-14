package com.sage.everyapart.regionservice.application.port.`in`

import com.sage.everyapart.regionservice.domain.RegionCode

interface RegionQuery {
    fun exist(regionCode: RegionCode): Boolean

}
