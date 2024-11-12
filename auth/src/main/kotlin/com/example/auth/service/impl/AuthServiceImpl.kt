package com.example.auth.service.impl

import com.example.auth.constant.Constant
import com.example.auth.dto.GlobalResponse
import com.example.auth.dto.RequestRegisterDto
import com.example.auth.dto.ResponseLoginDto
import com.example.auth.exception.BadRequestException
import com.example.auth.exception.NotFoundException
import com.example.auth.model.entity.User
import com.example.auth.model.enums.Role
import com.example.auth.repository.UserRepository
import com.example.auth.service.AuthService
import com.example.auth.service.JwtService
import org.apache.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.util.*


@Service

class AuthServiceImpl(
     var passwordEncoder: PasswordEncoder,
     var userRepository: UserRepository,
     var jwtService: JwtService,
) : AuthService {

    var nowDate: Timestamp = Timestamp(System.currentTimeMillis())

    override fun login(email: String, password: String): GlobalResponse<Any> {
        val user: Optional<User> = userRepository.findByUserId(email)
        if (email.isEmpty() || password.isEmpty()){
            throw BadRequestException(Constant.Message.INVALID_LOGIN_MESSAGE)
        }
        if (user.isPresent){

//            val authentication = authenticationManager.authenticate(
//                UsernamePasswordAuthenticationToken(email,password)
//            )

//            val jwt = jwtUtils.generateJwtToken(authentication)

//            val userDetails = authentication.principal as UserDetailsImpl

            if (!passwordEncoder.matches(password, user.get().passwords)){
                throw BadRequestException(Constant.Message.INVALID_LOGIN_MESSAGE)
            }

            var accessToken = jwtService.generateAccessToken(user.get().username)
            var refreshToken = jwtService.generateRefreshToken(user.get().username)
            


            return GlobalResponse(
                status = HttpStatus.SC_OK,
                message = Constant.Response.SUCCESS_MESSAGE,
                data = ResponseLoginDto(
                    user.get().id.toString(),
                    user.get().userId,
                    accessToken.toString(),
                    refreshToken.toString(),
                    "Bearer",
                    90000000,
                    user.get().level
                )
            )

        }else{
            throw NotFoundException(Constant.Message.NOT_FOUND_DATA_MESSAGE)
        }
    }

    override fun register(requestRegisterDto: RequestRegisterDto): GlobalResponse<Any> {
        TODO("Not yet implemented")

//        val user: Optional<User> = userRepository.findByUsernames(requestRegisterDto.username)
//        if (user.isPresent) {
//            throw BadRequestException("Username Already exist!")
//        }
//        val userEmail: Optional<User> =  userRepository.findByEmail(requestRegisterDto.email)
//        if (userEmail.isPresent){
//            throw BadRequestException("Email Already exist!")
//        }
//
//        val newUser = User(
//            idUser = UUID.randomUUID(),
//            usernames = requestRegisterDto.username,
//            userPassword = passwordEncoder.encode(requestRegisterDto.password),
//            email = requestRegisterDto.email,
//            role = Role.STAFF,
//            createdAt = nowDate,
//            updatedAt = nowDate,
//            name = requestRegisterDto.fullName,
//            phoneNumber = requestRegisterDto.phoneNumber,
//            jabatan = requestRegisterDto.jabatan,
//            nip = requestRegisterDto.nip,
//            tingkatJabatan = requestRegisterDto.tingkatJabatan
//
//        )
//        userRepository.save(newUser)
//
//        return GlobalResponse(HttpStatus.SC_CREATED, Constant.Response.SUCCESS_MESSAGE)
    }

    override fun logout(refreshToken: String): GlobalResponse<Any> {
        TODO("Not yet implemented")
    }

    override fun refreshToken(refreshToken: String): GlobalResponse<Any> {
        TODO("Not yet implemented")
    }


}