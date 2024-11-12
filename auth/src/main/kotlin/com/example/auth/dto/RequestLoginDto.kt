package com.example.auth.dto

import jakarta.validation.constraints.NotNull


data class RequestLoginDto (
    @NotNull(message = "email is required for login, please fill it")
     val email: String,

    @NotNull(message = "password is required for login, please fill it")
     val password: String,
)