package com.example.auth.model.entity

import com.example.auth.model.enums.Role
import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.sql.Timestamp
import java.util.*

@Data
@AllArgsConstructor
@Builder
@Table(name = "user_login")
@Entity(name = "user_login")
data class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long,

    @Column(name = "userid", length = 50, nullable = false, unique = true)
    val userId: String,

    @Column(name = "password", nullable = false)
    val passwords: String,

    @Column(name = "nama", length = 100, nullable = false)
    val nama: String,

    @Column(name = "unitkerja", length = 50)
    val unitKerja: String? = null,

    @Column(name = "levels", length = 20, nullable = false)
    val level: String,

    @Column(name = "jabatan", length = 80, nullable = false)
    val jabatan: String,

    @Column(name = "otorisator", length = 2, nullable = false)
    val otorisator: String,

    @Column(name = "wilayah", length = 10)
    val wilayah: String? = null,

    @Column(name = "unitkerja_inputer", length = 15)
    val unitKerjaInputer: String? = null,

    @Column(name = "status", length = 50)
    val status: String? = null,

    @Column(name = "inputer", length = 100)
    val inputer: String? = null,

    @Column(name = "approval", length = 100)
    val approval: String? = null,
): UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf()
    }

    override fun getPassword(): String {
        return this.passwords
    }

    override fun getUsername(): String {
        return this.userId
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }


}
