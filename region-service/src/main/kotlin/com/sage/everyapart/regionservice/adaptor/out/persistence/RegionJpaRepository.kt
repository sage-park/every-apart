package com.sage.everyapart.regionservice.adaptor.out.persistence;

import com.sage.everyapart.regionservice.domain.City
import org.springframework.data.jpa.repository.JpaRepository

interface RegionJpaRepository : JpaRepository<RegionJpaEntity, String> {

}
