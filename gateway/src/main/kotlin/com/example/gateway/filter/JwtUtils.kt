package com.example.gateway.filter

import com.example.gateway.exception.JwtExpiredException
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.time.Instant
import java.util.*

@Component
@Slf4j
class JwtUtils {

    @Value("\${jwt.secret-key}")
    lateinit var jwtSecret: String

    var nowDate: Date = Date()
    private val log = LoggerFactory.getLogger(JwtUtils::class.java)

    private fun key(): Key {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret))
    }

    fun validateJwtToken(authToken: String): Boolean {

        try {
            val decoder = Base64.getUrlDecoder()
            val chunks = authToken.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val header = String(decoder.decode(chunks[0]))
            val payload = String(decoder.decode(chunks[1]))

            val expiredEpochTime =
                payload.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[3].replace("}", "")
            log.info("header= $header |payload=  $payload |expiredEpochTime $expiredEpochTime")

            if (nowDate.after(Date.from(Instant.ofEpochSecond(expiredEpochTime.toLong())))) {
                throw JwtExpiredException("Token Expired")
            } else{
                Jwts.parser().setSigningKey(key()).build().parse(authToken)
                return true
            }
        } catch (e: MalformedJwtException) {
            throw MalformedJwtException("Invalid JWT token: ${e.message}")
            log.error("Invalid JWT token: {}", e.message)
        } catch (e: ExpiredJwtException) {
            log.error("JWT token is expired: {}", e.message)
            throw JwtExpiredException("JWT token is expired: ${e.message}")
        } catch (e: UnsupportedJwtException) {
            throw UnsupportedJwtException("JWT token is unsupported: ${e.message}")
            log.error("JWT token is unsupported: {}", e.message)
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("JWT claims string is empty: ${e.message}")
            log.error("JWT claims string is empty: {}", e.message)
        } catch (e: RuntimeException) {
            throw JwtExpiredException("Token Expired")
        } catch (e: Exception) {
            throw JwtExpiredException("Invalid Token")
        }

        return false
    }
}