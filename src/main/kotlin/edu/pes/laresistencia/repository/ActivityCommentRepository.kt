package edu.pes.laresistencia.repository

import edu.pes.laresistencia.model.Activity
import edu.pes.laresistencia.model.ActivityComment
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ActivityCommentRepository: CrudRepository<ActivityComment, Long> {
    @Query("SELECT a FROM activitiescomments a WHERE a.activity = ?1")
    fun findByActivity(activity: Activity): List<ActivityComment>?
}