package edu.pes.laresistencia.model

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.Instant
import java.util.*
import javax.mail.internet.AddressException

class UserTest {

    @Test
    fun userCreation() {
        val creationDate = Date.from(Instant.now())
        val modifiedDate = Date.from(Instant.now())
        val user = User(
                email = "email@est.fib.upc.edu",
                name = "estudiant",
                surname = "cognom",
                gender = Gender.OTHER,
                country = "Spain",
                birthDate = Date(),
                password = "password",
                enabled = true,
                creationDate = creationDate,
                modifiedDate = modifiedDate,
                photo = "photo",
                roles = listOf(Role("USER")))
        assertEquals("email@est.fib.upc.edu", user.email)
        assertEquals("estudiant", user.name)
        assertEquals("cognom", user.surname)
        assertEquals("Spain", user.country)
        assertEquals(Gender.OTHER, user.gender)
        assertEquals("password", user.password)
        assertEquals(true, user.enabled)
        assertEquals(creationDate, user.creationDate)
        assertEquals(modifiedDate, user.modifiedDate)
    }

    @Test(expected = AddressException::class)
    fun invalidEmail() {
        User(email = "invalidemail",
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
    }

    @Test
    fun equality() {
        val creationDate = Date.from(Instant.now())
        val modifiedDate = Date.from(Instant.now())
        val user = User(
                email = "email@est.fib.upc.edu",
                name = "estudiant",
                surname = "cognom",
                gender = Gender.OTHER,
                country = "Spain",
                birthDate = Date(),
                password = "password",
                enabled = true,
                creationDate = creationDate,
                modifiedDate = modifiedDate,
                photo = "photo",
                roles = listOf(Role("USER")))
        val sameUserDifferentInstance = User(
                email = "EMAIL@est.fib.upc.edu",
                name = "estudiant",
                surname = "cognom",
                gender = Gender.OTHER,
                country = "Spain",
                birthDate = Date(),
                password = "password",
                enabled = true,
                creationDate = creationDate,
                modifiedDate = modifiedDate,
                photo = "photo",
                roles = listOf(Role("USER")))
        assert(user == sameUserDifferentInstance)
    }
}