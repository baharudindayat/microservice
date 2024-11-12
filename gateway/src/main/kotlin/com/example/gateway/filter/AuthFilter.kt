package com.example.gateway.filter

import com.example.gateway.constant.Constant
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import lombok.extern.slf4j.Slf4j
import org.apache.http.HttpHeaders
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import java.security.Key
import java.util.*
import java.util.function.Function


@Component
@Slf4j
class AuthFilter : AbstractGatewayFilterFactory<AuthFilter.Config>(Config::class.java) {
    @Autowired
    private val routeValidator: RouteValidator? = null

    @Autowired
    private val jwtUtils: JwtUtils? = null

    @Value("\${jwt.secret-key}")
    lateinit var jwtSecret: String


    class Config

    override fun apply(config: Config?): GatewayFilter {
        return (GatewayFilter { exchange: ServerWebExchange, chain: GatewayFilterChain ->
            if (routeValidator!!.isSecure.test(exchange.request)) {
                if (!exchange.request.headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw RuntimeException(Constant.Message.FORBIDDEN_MESSAGE)
                }

                var authHeader = exchange.request.headers[HttpHeaders.AUTHORIZATION]!![0]
                if (Objects.nonNull(authHeader) && authHeader.startsWith("Bearer")) {
                    authHeader = authHeader.split("\\s".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
                    try {
                        val isValid = jwtUtils!!.validateJwtToken(authHeader)
                        if (!isValid) {
                            throw RuntimeException(Constant.Message.INVALID_TOKEN_MESSAGE)
                        }

                        // Extract decoded payload
                        val decoder = Base64.getUrlDecoder()
                        val chunks = authHeader.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        val payload = String(decoder.decode(chunks[1]))

                        val username = extractUserName(authHeader)



                        // Create a modified exchange with the decoded payload
                        val mutatedRequest = exchange.request.mutate()
                            .header("X-JWT-username", username)
                            .build()

                        val mutatedExchange = exchange.mutate()
                            .request(mutatedRequest)
                            .build()

                        return@GatewayFilter chain.filter(mutatedExchange)

                    } catch (e: Exception) {
                        throw RuntimeException(Constant.Message.INVALID_TOKEN_MESSAGE)
                    }
                } else {
                    throw RuntimeException(Constant.Message.INVALID_TOKEN_MESSAGE)
                }
            }
            chain.filter(exchange)
        })
    }

    fun extractUserName(token: String): String {
        return extractClaim<String>(token, Function<Claims, String> { obj: Claims -> obj.subject })
    }

    private fun <T> extractClaim(token: String, claimsResolvers: Function<Claims, T>): T {
        val claims = extractAllClaims(token)
        return claimsResolvers.apply(claims)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser().setSigningKey(getLoginKey()).build().parseClaimsJws(token)
            .getBody()
    }

    private fun getLoginKey(): Key {
        val keyBytes = Decoders.BASE64.decode(jwtSecret)
        return Keys.hmacShaKeyFor(keyBytes)
    }

}