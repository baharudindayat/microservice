package com.example.auth.dto

import jakarta.validation.constraints.Email
import lombok.Data

@Data
data class RequestRegisterDto (
     @Email(
          regexp = "^[A-Za-z0-9+_.-]+@bankbsi\\.co\\.id\$",
          message = "Email must be a valid @bankbsi.co.id address"
     )
     val email: String,

     val username: String,
     val fullName: String,
     val password: String,
     val phoneNumber: String,
     val jabatan: String,
     val nip: String,
     val tingkatJabatan: String
)
