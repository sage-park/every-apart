package com.sage.everyapart.regionservice.adaptor.out.persistence

import com.querydsl.core.types.Predicate
import com.querydsl.core.types.dsl.StringPath
import com.querydsl.jpa.impl.JPAQueryFactory
import com.sage.everyapart.regionservice.application.port.out.SearchRegionPort
import com.sage.everyapart.regionservice.domain.City
import com.sage.everyapart.regionservice.domain.RegionCode
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import java.lang.RuntimeException

@Repository
class JpaRegionAdaptor(
    private val jpaQueryFactory:JPAQueryFactory,
): SearchRegionPort {
    override fun search(keyword: String?, pageable: Pageable): List<City> {
        val region = QRegionJpaEntity.regionJpaEntity

        val result = jpaQueryFactory
            .select(
                region.regionCode,
                region.metropolitanCityName,
                region.cityName
            )
            .from(region)
            .where(
                region.metropolitanCityName.isNotNull,
                region.cityName.isNotNull,
                region.districtName.isNull,
                region.villageName.isNull,
                likeCityName(region.cityName, keyword)
            )
            .orderBy(region.regionCode.asc())
            .offset(pageable.getOffset())   // (2) 페이지 번호
            .limit(pageable.getPageSize().toLong())  // (3) 페이지 사이즈
            .fetch()

        return result.map {
            City(
                regionCode = RegionCode(it.get(region.regionCode).orEmpty()),
                metropolitanCityName = it.get(region.metropolitanCityName).orEmpty(),
                cityName = it.get(region.cityName).orEmpty()
            )
        }
    }

    override fun getTotal(keyword: String?, pageable: Pageable): Long {
        val region = QRegionJpaEntity.regionJpaEntity

        val count = jpaQueryFactory
            .select(region.count())
            .from(region)
            .where(
                region.metropolitanCityName.isNotNull,
                region.cityName.isNotNull,
                region.districtName.isNull,
                region.villageName.isNull,
                likeCityName(region.cityName, keyword)
            )
            .fetchOne()

        if (count != null) {
            return count
        } else {
            throw RuntimeException("조회된 결과가 없습니다.")
        }
    }

    private fun likeCityName(cityName: StringPath, keyword: String?): Predicate? {
        if (keyword == null) return null;

        return cityName.like("$keyword%")
    }
}
