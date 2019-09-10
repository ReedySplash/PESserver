package edu.pes.laresistencia.service

import edu.pes.laresistencia.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class AppUserService(private val userRepository: UserRepository): UserDetailsService {
    private val logger = Logger.getLogger(AppUserService::class.toString())

    override fun loadUserByUsername(email: String): UserDetails {
        logger.info("loaduserByUsername $email")
        val user = userRepository.findByEmail(email) ?: throw UsernameNotFoundException("$email not found")
        val authorities = user.roles.map { SimpleGrantedAuthority(it.name) }.toMutableList()
        return User(user.email, user.password, authorities)
    }
}