package edu.pes.laresistencia.service

import edu.pes.laresistencia.model.Friendship
import edu.pes.laresistencia.model.FriendshipId
import edu.pes.laresistencia.model.FriendshipStatus
import edu.pes.laresistencia.repository.FriendshipRepository
import edu.pes.laresistencia.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.*

@Service
class FriendshipService(private val friendshipRepository: FriendshipRepository,
                        private val userRepository: UserRepository) {

    fun areFriends(sourceEmail: String, receiverEmail: String): Boolean {
        return friendship(sourceEmail, receiverEmail).settled()
    }

    fun settleFriendship(me: String, requesterUser: String) {
        val myself = userRepository.findByEmail(me) ?: throw UserNotExists()
        val friendship = friendship(requesterUser, me)
        friendship.settleRelationship(myself)
        friendshipRepository.save(friendship)
    }

    fun requestFriendship(me: String, receiverEmail: String) {
        val sourceUser = userRepository.findByEmail(me) ?: throw UserNotExists()
        val receiverUser = userRepository.findByEmail(receiverEmail) ?: throw UserNotExists()
        val friendship = Friendship.requestFriendship(sourceUser, receiverUser)
        friendshipRepository.save(friendship)
    }

    fun friendshipStatus(sourceEmail: String, receiverEmail: String): FriendshipStatus {
        val sourceUser = userRepository.findByEmail(sourceEmail) ?: throw UserNotExists()
        return friendship(sourceEmail, receiverEmail).statusFor(sourceUser)
    }

    fun friendship(sourceEmail: String, receiverEmail: String): Friendship {
        val sourceUser = userRepository.findByEmail(sourceEmail)
        val receiverUser = userRepository.findByEmail(receiverEmail)
        if (sourceUser == null || receiverUser == null) throw UserNotExists()
        return friendshipRepository.findOne(FriendshipId(sourceUser, receiverUser))
                ?: friendshipRepository.findOne(FriendshipId(receiverUser, sourceUser))
                ?: Friendship.nonExistentFriendship(sourceUser, receiverUser)
    }

    fun friendships(userEmail: String): List<Friendship> {
        val user = userRepository.findByEmail(userEmail) ?: throw UserNotExists()
        val contacts = friendshipRepository.findByIdSourceFriendOrIdReceiverFriendOrderByStatus(user)
        val nonFriends = userRepository.findByEmailNotIn(contacts.map {
            if (it.id.sourceFriend == user) it.id.receiverFriend.email else it.id.sourceFriend.email
        }.toSet() + user.email).map { it -> Friendship(FriendshipId(user, it), FriendshipStatus.NONE, Date()) }
        return contacts + nonFriends
    }
}

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "user with that email not exits")
class UserNotExists : Exception()

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Friendship does not exist")
class FriendshipNotExists : Exception()

