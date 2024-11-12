package com.example.auth.security

import com.example.auth.constant.Constant
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.extern.slf4j.Slf4j
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.IOException
import kotlin.math.log

@Component
@Slf4j
class AuthEntryPoint : AuthenticationEntryPoint {
    @Throws(IOException::class, ServletException::class)
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        val bodyMap: MutableMap<String, Any> = HashMap()
        bodyMap["responseCode"] = HttpServletResponse.SC_UNAUTHORIZED
        bodyMap["ResponseMessage"] = "Unauthorized"
        val stringList: MutableList<String?> = ArrayList()
        val error: String?
        error = if ("Bad credentials" == authException.message) {
            Constant.Message.INVALID_LOGIN_MESSAGE
        } else {
            authException.message
        }
        stringList.add(error)
        bodyMap["errorList"] = stringList
        bodyMap["path"] = request.servletPath
        val mapper = ObjectMapper()
        mapper.writeValue(response.outputStream, bodyMap)
    }
}