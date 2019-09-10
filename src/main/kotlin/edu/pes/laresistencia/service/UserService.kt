package edu.pes.laresistencia.service

import edu.pes.laresistencia.model.Gender
import edu.pes.laresistencia.model.Role
import edu.pes.laresistencia.model.User
import edu.pes.laresistencia.repository.RoleRepository
import edu.pes.laresistencia.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.*

@Service
class UserService(private val passwordEncoder: BCryptPasswordEncoder,
                  private val userRepository: UserRepository,
                  private val roleRepository: RoleRepository) {

    fun registerUser(email: String,
                     password: String,
                     name: String,
                     surname: String,
                     country: String,
                     birthDate: Date,
                     gender: Gender
    ): User {
        if (userRepository.findByEmail(email) != null) {
            throw UserExists()
        }

        val role = roleRepository.findByName("USER")
        val user = User(
                email = email,
                name = name,
                surname = surname,
                gender = gender,
                country = country,
                birthDate = birthDate,
                password = passwordEncoder.encode(password),
                enabled = true,
                creationDate = Date(),
                modifiedDate = Date(),
                roles = listOf(role)
        )
        userRepository.save(user)
        return user
    }

    fun updateUser(email: String,
                     password: String,
                     name: String,
                     surname: String,
                     country: String,
                     birthDate: Date,
                     gender: Gender,
                     roles: List<Role>
    ): User {

        val user = User(
                email = email,
                name = name,
                surname = surname,
                gender = gender,
                country = country,
                birthDate = birthDate,
                password = passwordEncoder.encode(password),
                enabled = true,
                creationDate = Date(),
                modifiedDate = Date(),
                roles = roles
        )
        userRepository.save(user)
        return user
    }

    fun changePassword(email: String, oldPassword: String, newPassword: String) {
        val user = userRepository.findByEmail(email) ?: throw UserDoesNotExist()
        if (!passwordEncoder.matches(oldPassword,user.password)) throw PasswordsDontMatch()
        user.password = passwordEncoder.encode(newPassword)

        userRepository.save(user)
    }


    fun findAll(): List<User> {
        return userRepository.findAll().toList()
    }

    fun findByEmail(email: String): User {
        val user = userRepository.findByEmail(email) ?: throw UserDoesNotExist()
        return user

    }

    fun deleteUser(email: String) {
        if (userRepository.findByEmail(email) == null) throw UserDoesNotExist()
        return userRepository.delete(email);
    }
}

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "User with that email already exits")
class UserExists : Exception()

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User with that email not found.")
class UserDoesNotExist: Exception()

@ResponseStatus(value = HttpStatus.FORBIDDEN)
class PasswordsDontMatch: Exception()