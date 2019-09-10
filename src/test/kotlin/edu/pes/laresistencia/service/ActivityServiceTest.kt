package edu.pes.laresistencia.service

import edu.pes.laresistencia.model.Activity
import edu.pes.laresistencia.repository.ActivityRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.runners.MockitoJUnitRunner
import org.junit.Assert.assertEquals
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class ActivityServiceTest() {

    private lateinit var activityService: ActivityService

    @Mock
    private lateinit var activityRepository: ActivityRepository

    val date = Date()

    @Before
    fun setUp() {
        val activity1 = Activity(
                1,
                "Test activity",
                date,
                "12:00",
                "14:00",
                "This is a test activity.",
                "Provenza"
        )

        val activity2 = Activity(
                2,
                "Test activity 2",
                date,
                "14:00",
                "16:00",
                "This is a test activity.",
                "Entenza"
        )
        val list = listOf(activity1, activity2)
        `when`(activityRepository.findAll()).thenReturn(list)
        `when`(activityRepository.findById(1)).thenReturn(activity1)
    }

    @Test
    fun testFindAll() {
        activityService = ActivityService(activityRepository)
        val list = activityService.findAll()
        assert(list.size == 2)
        assert(list.get(0).id == 1)
        assert(list.get(1).id == 2)
    }

    @Test
    fun testFindById() {
        activityService = ActivityService(activityRepository)
        val act = activityService.findById(1)
        if (act != null) {
            assertEquals(1, act.id)
            assertEquals("Test activity", act.title)
            assertEquals(date, act.date)
            assertEquals("12:00", act.from_)
            assertEquals("14:00", act.to_)
            assertEquals("This is a test activity.", act.description)
            assertEquals("Provenza", act.location)
        }
    }
}