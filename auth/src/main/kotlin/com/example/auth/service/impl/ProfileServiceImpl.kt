package com.example.auth.service.impl

import com.example.auth.dto.GlobalResponse
import com.example.auth.dto.ProfileResponseDto
import com.example.auth.exception.NotFoundException
import com.example.auth.model.entity.User
import com.example.auth.repository.UserRepository
import com.example.auth.service.JwtService
import com.example.auth.service.ProfileService
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProfileServiceImpl(
    var userRepository: UserRepository,
    var jwtService:JwtService
): ProfileService {
    override fun getProfile(username: String): GlobalResponse<Any> {

        try {
            val user: String? =  jwtService.extractUsernameFromAccessToken(username)
            val userDetail : Optional<User> = userRepository.findByUserId(user)
            if (userDetail.isEmpty){
                throw NotFoundException("User not found")
            }
            val profileResponseDto = ProfileResponseDto(
                id = userDetail.get().id,
                userId = userDetail.get().userId,
                nama = userDetail.get().nama,
                unitKerja = userDetail.get().unitKerja,
                level = userDetail.get().level,
                jabatan = userDetail.get().jabatan,
                wilayah = userDetail.get().wilayah,
                unitKerjaInputer = userDetail.get().unitKerjaInputer,
                status = userDetail.get().status
            )
            return GlobalResponse(
                data = profileResponseDto,
                message = "Success",
                status = 200
            )

        }catch (e: NotFoundException){
            throw NotFoundException("User not found")
        }

    }


}