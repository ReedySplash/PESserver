package edu.pes.laresistencia.repository

import edu.pes.laresistencia.model.Friendship
import edu.pes.laresistencia.model.FriendshipId
import edu.pes.laresistencia.model.User
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface FriendshipRepository : CrudRepository<Friendship, FriendshipId> {

    @Query("SELECT f FROM Friendship f WHERE f.id.sourceFriend = ?1 OR f.id.receiverFriend = ?1 ORDER BY f.status")
    fun findByIdSourceFriendOrIdReceiverFriendOrderByStatus(user: User): List<Friendship>
}