package edu.pes.laresistencia.controller

import edu.pes.laresistencia.model.FriendshipStatus
import edu.pes.laresistencia.service.FriendshipService
import org.apache.log4j.Logger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/friendship")
class FriendshipController(private val friendshipService: FriendshipService) : BaseController() {

    private val logger = Logger.getLogger(FriendshipController::class.java)

    @GetMapping("/{email:.+}/status")
    fun friendshipStatus(@PathVariable email: String): ResponseEntity<FriendshipStatusResponse> {
        logger.info("friendshipStatus($email) by ${getCurrentUser()}")
        val status = friendshipService.friendshipStatus(getCurrentUser(), email)
        return ResponseEntity.ok(FriendshipStatusResponse(status))
    }

    @PostMapping("/{email:.+}/settle")
    fun settleFriendship(@PathVariable email: String): ResponseEntity<Unit> {
        logger.info("settleFriendship($email) by ${getCurrentUser()}")
        friendshipService.settleFriendship(getCurrentUser(), email)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/{email:.+}/request")
    fun requestFriendship(@PathVariable email: String): ResponseEntity<Unit> {
        logger.info("requestFriendship($email) by ${getCurrentUser()}")
        friendshipService.requestFriendship(getCurrentUser(), email)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("")
    fun friendships(): ResponseEntity<List<FriendshipResponse>> {
        logger.info("friendships() by ${getCurrentUser()}")
        val friendships = friendshipService.friendships(getCurrentUser())
            .map { it ->
                val me = if (it.id.sourceFriend.email == getCurrentUser()) it.id.sourceFriend else it.id.receiverFriend
                val otherUser = if (it.id.sourceFriend.email == getCurrentUser()) it.id.receiverFriend else it.id.sourceFriend
                FriendshipResponse(name = "${otherUser.name} ${otherUser.surname}",
                                   email = otherUser.email,
                                   status = it.statusFor(me),
                                   lastMessage = null)
            }
        return ResponseEntity.ok(friendships)
    }
}

data class FriendshipStatusResponse(val status: FriendshipStatus)

data class FriendshipResponse(
    val name: String,
    val email: String,
    val status: FriendshipStatus,
    val lastMessage: String?
                             )