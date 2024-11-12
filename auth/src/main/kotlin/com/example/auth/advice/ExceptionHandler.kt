package com.example.auth.advice

import com.example.auth.dto.GlobalResponse
import com.example.auth.exception.BadRequestException
import com.example.auth.exception.NotFoundException
import com.example.auth.exception.UnauthorizedException
import com.example.auth.exception.DataExistException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.util.stream.Collectors

@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(ex: BadRequestException): ResponseEntity<GlobalResponse<Any>> {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.message)
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException): ResponseEntity<GlobalResponse<Any>> {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.message)
    }

    @ExceptionHandler(UnauthorizedException::class)
    fun handleUnauthorizedException(ex: UnauthorizedException): ResponseEntity<GlobalResponse<Any>> {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, ex.message)
    }

    @ExceptionHandler(DataExistException::class)
    fun handleDataExistException(ex: DataExistException): ResponseEntity<GlobalResponse<Any>> {
        return buildErrorResponse(HttpStatus.CONFLICT, ex.message)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationErrors(ex: MethodArgumentNotValidException): ResponseEntity<GlobalResponse<Any>> {
        val errorList = ex.bindingResult.fieldErrors
            .stream()
            .map(FieldError::getDefaultMessage)
            .collect(Collectors.toList())

        return ResponseEntity(
            GlobalResponse(
                status = HttpStatus.BAD_REQUEST.value(),
                message = HttpStatus.BAD_REQUEST.reasonPhrase,
                errorList = errorList
            ),
            HttpHeaders(),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeExceptions(ex: RuntimeException): ResponseEntity<GlobalResponse<Any>> {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.message)
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneralExceptions(ex: Exception): ResponseEntity<GlobalResponse<Any>> {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.message)
    }

    private fun buildErrorResponse(
        status: HttpStatus,
        errorMessage: String?
    ): ResponseEntity<GlobalResponse<Any>> {
        val errors = listOfNotNull(errorMessage)
        return ResponseEntity(
            GlobalResponse(
                status = status.value(),
                message = status.reasonPhrase,
                errorList = errors
            ),
            HttpHeaders(),
            status
        )
    }
}
