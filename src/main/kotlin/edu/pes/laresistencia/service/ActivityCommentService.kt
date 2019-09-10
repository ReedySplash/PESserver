package edu.pes.laresistencia.service

import edu.pes.laresistencia.model.Activity
import edu.pes.laresistencia.model.ActivityComment
import edu.pes.laresistencia.repository.ActivityCommentRepository
import edu.pes.laresistencia.repository.ActivityRepository
import edu.pes.laresistencia.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.*

@Service
class ActivityCommentService(private val activityCommentRepository: ActivityCommentRepository, private val activityRepository: ActivityRepository, private val userRepository: UserRepository) {

    fun createComment(activityId: Int, userEmail: String, comment: String) {
        val activity = activityRepository.findById(activityId) ?: throw ActivityDoesNotExist()
        val user = userRepository.findByEmail(userEmail) ?: throw UserDoesNotExist()
        val activityComment = ActivityComment(0, user, activity, comment, Date())
        activityCommentRepository.save(activityComment)
    }


    fun findByActivity(activity: Activity): List<ActivityComment> {
        val listActivityComment = activityCommentRepository.findByActivity(activity)
                ?: throw ActivityCommentDoesNotExist()
        return listActivityComment
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Comments not found.")
    class ActivityCommentDoesNotExist : Exception()

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Activity not found.")
    class ActivityDoesNotExist : Exception()

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User with that email not found.")
    class UserDoesNotExist: Exception()
}