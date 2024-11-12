package com.example.auth.service.impl

import com.example.auth.service.JwtService
import com.example.auth.util.JwtUtils
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey

@Service
class JwtServiceImpl: JwtService {

    @Value("\${jwt.secret-key}")
    private val accessTokenSecret: String? = null

    @Value("\${jwt.secret-refresh-key}")
    private val refreshTokenSecret: String? = null

    @Value("\${jwt.expiration}")
    private val accessTokenExp: Long? = null

    @Value("\${jwt.refresh-expiration}")
    private val refreshTokenExp: Long? = null

    private fun getKey(secret: String?): SecretKey {
        val keyBytes = Decoders.BASE64.decode(secret)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    private fun getExp(exp: Long?): Date {
        return Date(System.currentTimeMillis() + exp!! * 1000)
    }

    override fun generateAccessToken(username: String?): String {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date())
            .setExpiration(getExp(accessTokenExp))
            .signWith(getKey(accessTokenSecret))
            .compact()
    }

    override fun generateRefreshToken(username: String?): String {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date())
            .setExpiration(getExp(refreshTokenExp))
            .signWith(getKey(refreshTokenSecret))
            .compact()
    }

    override fun extractUsernameFromAccessToken(accessToken: String?): String {
        return Jwts.parser()
            .verifyWith(getKey(accessTokenSecret))
            .build()
            .parseClaimsJws(accessToken)
            .body
            .subject
    }

    override fun extractUsernameFromRefreshToken(refreshToken: String?): String {
        return Jwts.parser()
            .verifyWith(getKey(refreshTokenSecret))
            .build()
            .parseClaimsJws(refreshToken)
            .body
            .subject
    }

    override fun validateAccessToken(accessToken: String?, userDetails: UserDetails?): Boolean {
        val username = extractUsernameFromAccessToken(accessToken)
        return (username == userDetails?.username) && !isTokenExpired(accessToken, accessTokenSecret)
    }

    override fun validateRefreshToken(refreshToken: String?): Boolean {
        return !isTokenExpired(refreshToken, refreshTokenSecret)
    }

    override fun isTokenExpired(token: String?, secret: String?): Boolean {
        val tokenExp = Jwts.parser()
            .verifyWith(getKey(secret))
            .build()
            .parseClaimsJws(token)
            .body
            .expiration

        return tokenExp.before(Date())
    }

    private val log = LoggerFactory.getLogger(JwtServiceImpl::class.java)

    fun validateJwtToken(authToken: String?): Boolean {
        try {
            Jwts.parser().setSigningKey(accessTokenSecret).build().parseClaimsJws(authToken)
            return true
        } catch (e: MalformedJwtException) {
            log.error("Invalid JWT token: {}", e.message)
        } catch (e: ExpiredJwtException) {
            log.error("JWT token is expired: {}", e.message)
        } catch (e: UnsupportedJwtException) {
            log.error("JWT token is unsupported: {}", e.message)
        } catch (e: IllegalArgumentException) {
            log.error("JWT claims string is empty: {}", e.message)
        }
        return false
    }
}
