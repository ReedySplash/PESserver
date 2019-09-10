package edu.pes.laresistencia.service

import edu.pes.laresistencia.model.*
import edu.pes.laresistencia.repository.FriendshipRepository
import edu.pes.laresistencia.repository.UserRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.runners.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class FriendshipServiceTest {

    @Mock
    private lateinit var friendshipRepository: FriendshipRepository

    @Mock
    private lateinit var userRepository: UserRepository

    private lateinit var friendshipService: FriendshipService

    lateinit var sourceUser: User
    lateinit var receiverUser: User

    @Before
    fun setup() {
        sourceUser = User(
                email = "estudiant@a.com",
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
        receiverUser = User(
                email = "receiver@a.com",
                name = "receiver",
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
        friendshipService = FriendshipService(friendshipRepository, userRepository)
        `when`(userRepository.findByEmail("notexists@aaa.com")).thenReturn(null)
        `when`(userRepository.findByEmail("estudiant@a.com")).thenReturn(sourceUser)
        `when`(userRepository.findByEmail("receiver@a.com")).thenReturn(receiverUser)
        val friendshipId = FriendshipId(sourceUser, receiverUser)
        `when`(friendshipRepository.findOne(friendshipId))
            .thenReturn(Friendship(friendshipId, FriendshipStatus.SETTLED, Date()))
    }

    @Test(expected = UserNotExists::class)
    fun `return false if some email does not exist`() {
        friendshipService.areFriends("email@est.fib.upc.edu", "notexists@aaa.com")
    }

    @Test
    fun `return true if the relationship is settled`() {
        val areFriends = friendshipService.areFriends("estudiant@a.com", "receiver@a.com")
        assert(areFriends)
    }
}