package com.example.auth.repository

import com.example.auth.model.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository : JpaRepository<User?, String?> {

    fun findByUserId(Userid: String?): Optional<User>

//    fun findByEmail(email: String?): Optional<User>
}
