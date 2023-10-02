package com.sage.everyapart.membershipservice.adaptor.out.persistence

import com.sage.everyapart.membershipservice.domain.Membership
import com.sage.everyapart.membershipservice.domain.MembershipId
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.mockito.kotlin.mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.security.crypto.password.PasswordEncoder
import kotlin.test.BeforeTest
import kotlin.test.Test
@DataJpaTest
@Import(JpaQuerydslConfig::class)
class MembershipPersistenceAdaptorTest {

    private val passwordEncoder: PasswordEncoder = mock()
    lateinit var membershipPersistenceAdaptor: MembershipPersistenceAdaptor
    @Autowired
    private lateinit var membershipJpaRepository: MembershipJpaRepository

    @BeforeTest
    fun setup() {
        membershipPersistenceAdaptor = MembershipPersistenceAdaptor(
            membershipJpaRepository = membershipJpaRepository
        )
    }

    @DisplayName("exist")
    @Nested
    inner class Exist {

        @DisplayName("만약 membership 이 존재 할 경우 true 를 반환한다.")
        @Test
        fun ifNotExist() {
            //given
            val membershipId = "user01"

            membershipJpaRepository.save(MembershipJpaEntity(
                membershipId = membershipId,
                isValid = true,
                password = "password01"
            ))


            //when
            val result = membershipPersistenceAdaptor.exist(MembershipId(membershipId))

            //then
            assertThat(result).isTrue

        }

        @DisplayName("만약 membership 이 존재하지 않을 경우 false 를 반환한다.")
        @Test
        fun ifExist() {
            //given
            val membershipId = "user02"

            //when
            val result = membershipPersistenceAdaptor.exist(MembershipId(membershipId))

            //then
            assertThat(result).isFalse()

        }

    }


    @DisplayName("save")
    @Nested
    inner class Save {

        @DisplayName("만약 값이 없을 경우 그대로 저장한다.")
        @Test
        fun save() {

            //given
            val membership = Membership(
                membershipId = MembershipId("user01"),
                password = "password01",
                name = "user01",
                address = "address01",
                email = "email01",
                isValid = true,
                refreshToken = "refreshToken01",
            )

            //when
            membershipPersistenceAdaptor.save(membership)

            //when
            val foundMembership = membershipPersistenceAdaptor.findMembership(membership.membershipId.membershipId)

            assertThat(foundMembership)
                .usingRecursiveComparison()
                .ignoringFields("password")
                .isEqualTo(membership)

        }

        @DisplayName("만약 값이 있을 경우 업데이트한다.")
        @Test
        fun update() {

            //given
            val membershipId = MembershipId("user01")

            membershipPersistenceAdaptor.save(
                Membership(
                    membershipId = membershipId,
                    password = "password01",
                    name = "김구",
                    address = "address01",
                    email = "email01",
                    isValid = true,
                    refreshToken = "refreshToken01",
                )

            )

            //when
            val membership = Membership(
                membershipId = membershipId,
                password = "password01_수정",
                name = "김구_수정",
                address = "address01_수정",
                email = "email01_수정",
                isValid = false,
                refreshToken = "refreshToken01_수정",
            )
            membershipPersistenceAdaptor.save(membership)

            //then
            val findMembership = membershipPersistenceAdaptor.findMembership(membershipId.membershipId)


            assertThat(findMembership)
                .usingRecursiveComparison()
                .ignoringFields("password")
                .isEqualTo(membership)

        }

    }


}

