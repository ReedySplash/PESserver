package edu.pes.laresistencia.service

import edu.pes.laresistencia.model.Activity
import edu.pes.laresistencia.repository.ActivityRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ResponseStatus

@Service
class ActivityService(private val activityRepository: ActivityRepository) {
    fun findAll(): List<Activity> {
        return activityRepository.findAll().toList()
    }

    fun findById(id: Int): Activity {
        val activity = activityRepository.findById(id) ?: throw ActivityDoesNotExist()
        return activity
    }
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Activity with that ID not found.")
    class ActivityDoesNotExist: Exception()
}