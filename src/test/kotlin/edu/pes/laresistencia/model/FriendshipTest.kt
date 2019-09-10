package edu.pes.laresistencia.model

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*

class FriendshipTest {
    lateinit var sourceUser: User
    lateinit var receiverUser: User

    @Before
    fun setup() {
        sourceUser = User("a@a.com",
                "LG",
                "Surname",
                Gender.OTHER,
                Date(),
                "Spain",
                "123456789",
                true,
                Date(),
                Date(),
                "photo",
                listOf())
        receiverUser = User("bb@bb.com",
                "Asus",
                "Surname",
                Gender.OTHER,
                Date(),
                "Spain",
                "123456789",
                true,
                Date(),
                Date(),
                "photo",
                listOf())
    }

    @Test
    fun `requesting a friendship`() {
        val friendship = Friendship.requestFriendship(sourceUser, receiverUser)
        assertEquals(friendship.id.sourceFriend, sourceUser)
        assertEquals(friendship.id.receiverFriend, receiverUser)
        assertEquals(friendship.status, FriendshipStatus.PENDING)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `throws exception when the sourceFriend and receiverFriend are the same`() {
        Friendship(FriendshipId(sourceUser, sourceUser), FriendshipStatus.PENDING, Date())
    }

    @Test
    fun `settle relationship`() {
        val friendship = Friendship.requestFriendship(sourceUser, receiverUser)
        friendship.settleRelationship(receiverUser)
        assert(friendship.settled())
    }

    @Test(expected = SettleFriendshipException::class)
    fun `throws exception if it is not the receiver user who settles the relationship`() {
        val friendship = Friendship.requestFriendship(sourceUser, receiverUser)
        friendship.settleRelationship(sourceUser)
    }

    @Test
    fun `return true when the friendship is settled`() {
        val friendship = Friendship(FriendshipId(sourceUser, receiverUser), FriendshipStatus.SETTLED, Date())
        assert(friendship.settled())
    }

    @Test
    fun `return false when the friendship is not settled`() {
        val friendship = Friendship(FriendshipId(sourceUser, receiverUser), FriendshipStatus.NONE, Date())
        assert(!friendship.settled())
    }

    @Test
    fun `should return pending when the source user asks for the pending friendship status`() {
        val friendship = Friendship(FriendshipId(sourceUser, receiverUser), FriendshipStatus.PENDING, Date())
        assertEquals(friendship.statusFor(sourceUser), FriendshipStatus.PENDING)
    }

    @Test
    fun `should return to accept when the receiver user asks for a pending friendship status`() {
        val friendship = Friendship(FriendshipId(sourceUser, receiverUser), FriendshipStatus.PENDING, Date())
        assertEquals(friendship.statusFor(receiverUser), FriendshipStatus.TO_ACCEPT)
    }

    @Test
    fun `should return none when the sourceUser or receiver and asks for none friendship`() {
        val friendship = Friendship(FriendshipId(sourceUser, receiverUser), FriendshipStatus.NONE, Date())
        assertEquals(friendship.statusFor(sourceUser), FriendshipStatus.NONE)
        assertEquals(friendship.statusFor(receiverUser), FriendshipStatus.NONE)
    }

    @Test
    fun `should return settled when the sourceUser or receiver and asks for settled friendship`() {
        val friendship = Friendship(FriendshipId(sourceUser, receiverUser), FriendshipStatus.SETTLED, Date())
        assertEquals(friendship.statusFor(sourceUser), FriendshipStatus.SETTLED)
        assertEquals(friendship.statusFor(receiverUser), FriendshipStatus.SETTLED)
    }
}