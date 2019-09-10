package edu.pes.laresistencia.service

import edu.pes.laresistencia.model.*
import edu.pes.laresistencia.repository.RoleRepository
import edu.pes.laresistencia.repository.UserRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.runners.MockitoJUnitRunner
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class UserServiceTest {
    @Mock
    private lateinit var userRepository: UserRepository
    @Mock
    private lateinit var roleRepository: RoleRepository
    @Mock
    private lateinit var userService: UserService
    lateinit var userA: User
    lateinit var userB: User
    @Mock
    private lateinit var passwordEncoder: BCryptPasswordEncoder
    val userAEmail = "estudiant@a.com"
    val userBEmail = "estudiant@b.com"

    @Before
    fun setUp() {

        userA = User(
                email = userAEmail,
                name = "estudiant",
                surname = "cognom",
                gender = Gender.OTHER,
                country = "Spain",
                birthDate = Date(),
                password = "password",
                enabled = true,
                creationDate = Date(),
                modifiedDate = Date(),
                photo = "photo",
                roles = listOf(Role("USER")))
        userService = UserService(passwordEncoder, userRepository, roleRepository)
        `when`(userRepository.findByEmail(userAEmail)).thenReturn(userA)

        `when`(userRepository.findByEmail("xxxxx@xxxx.com")).thenReturn(null)
        `when`(userRepository.delete(userAEmail)).then {
            `when`(userRepository.findByEmail(userAEmail)).thenReturn(null)

        }
        userA.password = "anotherPassword"
        `when`(userRepository.save(userA)).then {
            `when`(userRepository.findByEmail(userAEmail)).thenReturn(userA)
        }

        userB = User(
                email = userBEmail,
                name = "estudiant",
                surname = "cognom",
                gender = Gender.OTHER,
                country = "Spain",
                birthDate = Date(),
                password = "password",
                enabled = true,
                creationDate = Date(),
                modifiedDate = Date(),
                photo = "photo",
                roles = listOf(Role("USER")))

        `when`(userRepository.findByEmail(userBEmail)).thenReturn(userB)

        userB.photo = "anotherPhoto"
        `when`(userRepository.save(userB)).then {
            `when`(userRepository.findByEmail(userBEmail)).thenReturn(userB)
        }

    }


    @Test
    fun `find a user`() {
        assert(userService.findByEmail(userAEmail) == userA)
    }

    @Test(expected = UserDoesNotExist::class)
    fun `find a user that doesn't exist`() {
        userService.findByEmail("xxxxx@xxxx.com")
    }

    @Test(expected = UserDoesNotExist::class)
    fun `delete a user`() {
        userService.deleteUser(userAEmail)
        userService.findByEmail(userAEmail)
    }

    @Test(expected = UserExists::class)
    fun `create a user that already exists`() {
        userService.registerUser(email = userAEmail,
                name = "estudiant",
                surname = "cognom",
                gender = Gender.OTHER,
                country = "Spain",
                birthDate = Date(),
                password = "password",
                photo = "photo")
    }

    // TODO: fer que funcioni!!!
    @Test
    fun `change a user password`() {
        userService.changePassword(userAEmail, "anotherPassword")
        val updatedUser = userService.findByEmail(userAEmail)
        assert(updatedUser.password == "anotherPassword")
    }

    @Test
    fun `change a user photo`() {
        userService.changePhoto(userAEmail, "anotherPhoto")
        val updatedUser = userService.findByEmail(userAEmail)
        assert(updatedUser.photo == "anotherPhoto")
    }
}