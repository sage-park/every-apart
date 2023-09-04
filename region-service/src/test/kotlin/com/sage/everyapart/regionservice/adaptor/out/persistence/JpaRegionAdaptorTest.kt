package com.sage.everyapart.regionservice.adaptor.out.persistence

import com.querydsl.jpa.impl.JPAQueryFactory
import com.sage.everyapart.regionservice.adaptor.out.persistence.config.JpaConfig
import com.sage.everyapart.regionservice.application.port.out.SearchRegionPort
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.data.domain.PageRequest
import kotlin.test.BeforeTest
import kotlin.test.Test

@DataJpaTest
@Import(JpaConfig::class)
class JpaRegionAdaptorTest {

    lateinit var searchRegionPort: SearchRegionPort
    @Autowired
    lateinit var jpaQueryFactory: JPAQueryFactory

    @Autowired
    lateinit var regionJpaRepository :RegionJpaRepository

    @BeforeTest
    fun setup() {
        searchRegionPort = JpaRegionAdaptor(jpaQueryFactory = jpaQueryFactory)
    }

    /**
     * 요구사항
     * - keyword 가 없을 경우 전체를 대상으로 검색한다.
     * - keyword 가 있을 경우 앞자리부터 똑같은 경우만 검색한다.
     * - pageable 을 적용한다.
     * - regioncode순으로  출력한다.
     */

    @Nested
    inner class `만약 데이터가 주어졌을때`{

        val entities = listOf(
            RegionJpaEntity(
                regionCode = "0000000010",
                metropolitanCityName = "Metropolitan City",
                cityName = "City",
                createdDate = "2022-01-01",
                districtName = null,
                legalRegionCode = null,
                villageName = null,
                deletedDate = null
            ),
            RegionJpaEntity(
                regionCode = "0000000012",
                metropolitanCityName = "Metropolitan City02",
                cityName = "City02",
                createdDate = "2022-01-01",
                districtName = null,
                legalRegionCode = null,
                villageName = null,
                deletedDate = null
            ),
            RegionJpaEntity(
                regionCode = "0000000001",
                metropolitanCityName = "Other city",
                cityName = "Other City02",
                createdDate = "2022-01-01",
                districtName = null,
                legalRegionCode = null,
                villageName = null,
                deletedDate = null
            )
        )

        @BeforeTest
        fun setup() {
            regionJpaRepository.saveAll(entities)
        }

        @DisplayName("search Test")
        @Nested
        inner class SearchCity {
            @Test
            fun `keyword가 없을 경우 전체를 대상으로 검색한다`() {
                val result = searchRegionPort.search(null, PageRequest.of(0, 1000))

                assertThat(result).hasSize(entities.size)


            }

            @Test
            fun `size 가 1 일경우 1개만 검색한다`(){
                val result = searchRegionPort.search(null, PageRequest.of(0, 1))

                assertThat(result).hasSize(1)
            }

            @Test
            fun `keyword 가 있을 경우 앞자리부터 똑같은 경우만 검색한다`() {

                val result = searchRegionPort.search("Other", PageRequest.of(0, 1000))

                assertThat(result.size).isEqualTo(1)
                assertThat(result.get(0).cityName).isEqualTo("Other City02")

            }

            @Test
            fun `검색결과는 resionCode 순으로 출력한다`() {

                val result = searchRegionPort.search(null, PageRequest.of(0, 3))

                assertThat(result.get(0).regionCode.value).isEqualTo(entities[2].regionCode)
                assertThat(result.get(1).regionCode.value).isEqualTo(entities[0].regionCode)
                assertThat(result.get(2).regionCode.value).isEqualTo(entities[1].regionCode)

            }
        }

        @DisplayName("getTotal")
        @Nested
        inner class GetTotal {

            @Test
            fun `total 가 3일경우 3을 리턴한다`(){
                val result = searchRegionPort.getTotal(null, PageRequest.of(0, 3))
                assertThat(result).isEqualTo(3)
            }


        }

    }




}
