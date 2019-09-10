package edu.pes.laresistencia.model

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.Instant
import java.util.*

class ActivityCommentTest {
    @Test
    fun activityCommentCreation() {
        val activity = Activity(
                1,
                "Test activity",
                Date(),
                "12:00",
                "14:00",
                "This is a test activity.",
                "Provenza"
        )
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
        val date = Date()
        val activityComment = ActivityComment(
                id = 1.toLong(),
                activity = activity,
                user = user,
                comment = "Test comment",
                creationDate = date

        )

        assertEquals(1.toLong(), activityComment.id)
        assertEquals(activity, activityComment.activity)
        assertEquals(user, activityComment.user)
        assertEquals("Test comment", activityComment.comment)
        assertEquals(date, activityComment.creationDate)

    }
}