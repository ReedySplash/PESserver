package edu.pes.laresistencia.model

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.Instant
import java.util.*

class ActivityTest {
    @Test
    fun activityCreation() {
        val date = Date()
        val activity = Activity(
                1,
                "Test activity",
                date,
                "12:00",
                "14:00",
                "This is a test activity.",
                "Provenza"
        )
        assertEquals(1, activity.id)
        assertEquals("Test activity", activity.title)
        assertEquals(date, activity.date)
        assertEquals("12:00", activity.from_)
        assertEquals("14:00", activity.to_)
        assertEquals("This is a test activity.", activity.description)
        assertEquals("Provenza", activity.location)
    }
}