package com.sage.everyapart.membershipservice.adaptor.out.jwt

import com.sage.everyapart.membershipservice.application.port.out.TokenPort
import com.sage.everyapart.membershipservice.domain.MembershipId
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.UnsupportedJwtException
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.lang.IllegalArgumentException
import java.util.Date

@Component
class JwtTokenProvider(
    @Value("\${everyapart.token.secret}")
    val jwtSecret:String,
    @Value("\${everyapart.token.jwt.expiration}")
    val jwtTokenExpirationInMs:Long,
    @Value("\${everyapart.token.refresh.expiration}")
    val refreshTokenExpirationInMs:Long
): TokenPort{

    override fun generateJwtToken(membershipId: MembershipId): String {

        val now = Date()
        val expiryDate = Date(now.time + jwtTokenExpirationInMs)

        return Jwts.builder()
            .setSubject(membershipId.membershipId)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact()

    }

    override fun generateRefreshToken(membershipId: MembershipId): String {

        val now = Date()
        val expiryDate = Date(now.time + refreshTokenExpirationInMs)

        return Jwts.builder()
            .setSubject(membershipId.membershipId)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact()

    }

    override fun validateJwtToken(jwtToken: String): Boolean {

        try {
            Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(jwtToken)
            return true;
        } catch (ex: MalformedJwtException) {
            // Invalid JWT token: 유효하지 않은 JWT 토큰일 때 발생하는 예외
            ex.printStackTrace()
        } catch (ex: UnsupportedJwtException) {
            // Expired JWT token: 토큰의 유효기간이 만료된 경우 발생하는 예외
            ex.printStackTrace()
        } catch (ex: IllegalArgumentException) {
            // JWT claims string is empty: JWT 토큰이 비어있을 때 발생하는 예외
            ex.printStackTrace()
        }

        return false
    }

    override fun parseMembershipIdFromToken(jwtToken: String): MembershipId {
        val claims = Jwts.parserBuilder()
            .setSigningKey(jwtSecret)
            .build()
            .parseClaimsJws(jwtToken)
            .body

        return MembershipId(claims.subject)
    }

}
