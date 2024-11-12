package com.example.gateway.filter

import com.example.gateway.dto.Response
import com.example.gateway.exception.JwtExpiredException
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler


@ControllerAdvice
class ErrorHandler {

    @ExceptionHandler(Exception::class)
    fun handleGeneralExceptions(ex: Exception): ResponseEntity<Response<Any>> {
        val errorList = listOf(ex.message)
        return ResponseEntity<Response<Any>>(
            mappingError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase, errorList
            ),
            HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeExceptions(ex: RuntimeException): ResponseEntity<Response<Any>> {
        val errorList = listOf(ex.message)
        return ResponseEntity<Response<Any>>(
            mappingError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase, errorList
            ),
            HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    @ExceptionHandler(MalformedJwtException::class)
    fun handleRuntimeExceptions(ex: MalformedJwtException): ResponseEntity<Response<Any>> {
        val errorList = listOf(ex.message)
        return ResponseEntity<Response<Any>>(
            mappingError(
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.reasonPhrase, errorList
            ),
            HttpHeaders(), HttpStatus.UNAUTHORIZED
        )
    }

    @ExceptionHandler(ExpiredJwtException::class)
    fun handleRuntimeExceptions(ex: ExpiredJwtException): ResponseEntity<Response<Any>> {
        val errorList = listOf(ex.message)
        return ResponseEntity<Response<Any>>(
            mappingError(
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.reasonPhrase, errorList
            ),
            HttpHeaders(), HttpStatus.UNAUTHORIZED
        )
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleRuntimeExceptions(ex: IllegalArgumentException): ResponseEntity<Response<Any>> {
        val errorList = listOf(ex.message)
        return ResponseEntity<Response<Any>>(
            mappingError(
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.reasonPhrase, errorList
            ),
            HttpHeaders(), HttpStatus.UNAUTHORIZED
        )
    }

    @ExceptionHandler(UnsupportedJwtException::class)
    fun handleRuntimeExceptions(ex: UnsupportedJwtException): ResponseEntity<Response<Any>> {
        val errorList = listOf(ex.message)
        return ResponseEntity<Response<Any>>(
            mappingError(
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.reasonPhrase, errorList
            ),
            HttpHeaders(), HttpStatus.UNAUTHORIZED
        )
    }

    @ExceptionHandler(JwtExpiredException::class)
    fun handleRuntimeExceptions(ex: JwtExpiredException): ResponseEntity<Response<Any>> {
        val errorList = listOf(ex.message)
        return ResponseEntity<Response<Any>>(
            mappingError(
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.reasonPhrase, errorList
            ),
            HttpHeaders(), HttpStatus.UNAUTHORIZED
        )
    }



    private fun mappingError(status: Int, message: String, errorList: List<String?>): Response<Any> {
        return Response(
            status = status,
            message = message,
            errorList = errorList
        )
    }

}