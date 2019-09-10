package edu.pes.laresistencia.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.io.Serializable
import java.util.*
import javax.persistence.Embeddable
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
class Friendship(
    @EmbeddedId
    val id: FriendshipId,
    var status: FriendshipStatus,
    @CreatedDate val createdDate: Date
                ) {

    init {
        require(id.sourceFriend != id.receiverFriend, { "Friendship implies two different users" })
    }

    fun settleRelationship(receiverUser: User) {
        if (id.receiverFriend == receiverUser) {
            status = FriendshipStatus.SETTLED
            return
        }
        throw SettleFriendshipException()
    }

    fun settled(): Boolean {
        return FriendshipStatus.SETTLED == status
    }

    fun statusFor(user: User): FriendshipStatus {
        if (status == FriendshipStatus.PENDING && id.sourceFriend == user) return FriendshipStatus.PENDING
        else if (status == FriendshipStatus.PENDING && id.receiverFriend == user) return FriendshipStatus.TO_ACCEPT
        return status
    }

    companion object {
        fun requestFriendship(sourceFriend: User, receiverFriend: User): Friendship {
            return Friendship(FriendshipId(sourceFriend, receiverFriend), FriendshipStatus.PENDING, Date())
        }

        fun nonExistentFriendship(sourceFriend: User, receiverFriend: User): Friendship {
            return Friendship(FriendshipId(sourceFriend, receiverFriend), FriendshipStatus.NONE, Date())
        }
    }
}

@Embeddable
class FriendshipId(@ManyToOne(targetEntity = User::class)
                   val sourceFriend: User,
                   @ManyToOne(targetEntity = User::class)
                   val receiverFriend: User) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FriendshipId) return false

        if (sourceFriend != other.sourceFriend) return false
        if (receiverFriend != other.receiverFriend) return false

        return true
    }

    override fun hashCode(): Int {
        var result = sourceFriend.hashCode()
        result = 31 * result + receiverFriend.hashCode()
        return result
    }
}

enum class FriendshipStatus {
    NONE,
    PENDING,
    TO_ACCEPT,
    SETTLED,
}

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "just the receiverUser can settle the friendship")
class SettleFriendshipException : RuntimeException()