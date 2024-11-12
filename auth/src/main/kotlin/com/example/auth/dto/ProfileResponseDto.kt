package com.example.auth.dto

data class ProfileResponseDto(

    val id: Long,

    val userId: String,

    val nama: String,

    val unitKerja: String? = null,

    val level: String,

    val jabatan: String,

    val wilayah: String? = null,

    val unitKerjaInputer: String? = null,

    val status: String? = null,
)