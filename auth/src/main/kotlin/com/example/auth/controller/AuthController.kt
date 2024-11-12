package com.example.auth.controller

import com.example.auth.dto.RequestLoginDto
import com.example.auth.dto.RequestRegisterDto
import com.example.auth.dto.ResponseLoginDto
import com.example.auth.service.AuthService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/auth")
class AuthController {


    @Autowired
    lateinit var authService: AuthService


    @PostMapping("/login")
    fun login(@RequestBody requestLoginDto: RequestLoginDto): ResponseEntity<Any?> {
        return ResponseEntity.ok(authService.login(requestLoginDto.email,requestLoginDto.password))
    }


    @PostMapping("/register")
    fun register(@RequestBody userRequest: @Valid RequestRegisterDto): ResponseEntity<Any?>? {
        return ResponseEntity.ok(authService.register(userRequest))
    }


}