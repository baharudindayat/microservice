package com.example.gateway.filter

import com.example.gateway.constant.Constant
import org.springframework.stereotype.Component
import org.springframework.http.server.reactive.ServerHttpRequest
import java.util.function.Predicate

@Component
class RouteValidator {

    val isSecure: Predicate<ServerHttpRequest> = Predicate { request ->
        Constant.AUTH_WHITELIST.none { uri -> request.uri.path.contains(uri) }
    }
}
