package edu.pes.laresistencia.service;

import edu.pes.laresistencia.model.*
import edu.pes.laresistencia.repository.ActivityCommentRepository
import edu.pes.laresistencia.repository.ActivityRepository
import edu.pes.laresistencia.repository.UserRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith;
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.runners.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class ActivityCommentServiceTest {
    private lateinit var activityCommentService: ActivityCommentService

    @Mock
    private lateinit var activityCommentRepository: ActivityCommentRepository

    @Mock
    private lateinit var activityRepository: ActivityRepository

    @Mock
    private lateinit var userRepository: UserRepository

    private lateinit var activity1: Activity

    private lateinit var activity2: Activity

    private lateinit var activity3: Activity

    private lateinit var activityComment: ActivityComment

    private lateinit var user: User

    val date = Date()
    val email = "email@est.fib.upc.edu"
    val comment = "Test comment"
    val description = "This is a test activity."

    @Before
    fun setUp() {
        activityCommentService = ActivityCommentService(activityCommentRepository, activityRepository, userRepository)

        activity1 = Activity(
                1,
                "Test activity",
                date,
                "12:00",
                "14:00",
                description,
                "Provenza"
        )

        activity2 = Activity(
                2,
                "Test activity 2",
                date,
                "14:00",
                "16:00",
                description,
                "Entenza"
        )

        activity3 = Activity(
                3,
                "Test activity 3",
                date,
                "14:00",
                "16:00",
                description,
                "Entenza"
        )
        val list = listOf(activity1, activity2)
        `when`(activityRepository.findAll()).thenReturn(list)
        `when`(activityRepository.findById(1)).thenReturn(activity1)
        `when`(activityRepository.findById(2)).thenReturn(activity2)


        user = User(
                email = email,
                name = "estudiant",
                surname = "cognom",
                gender = Gender.OTHER,
                country = "Spain",
                birthDate = Date(),
                password = "password",
                enabled = true,
                creationDate = date,
                modifiedDate = date,
                photo = "photo",
                roles = listOf(Role("USER")))
        val activityComment1 = ActivityComment(
                id = 1.toLong(),
                activity = activity1,
                user = user,
                comment = comment,
                creationDate = date

        )

        val activityComment2 = ActivityComment(
                id = 2.toLong(),
                activity = activity1,
                user = user,
                comment = comment,
                creationDate = date

        )

        activityComment = ActivityComment(
                id = 3.toLong(),
                activity = activity1,
                user = user,
                comment = comment,
                creationDate = date

        )
        val listOfActivityComment = listOf(activityComment1, activityComment2)

        `when`(userRepository.findByEmail(email)).thenReturn(user)
        `when`(activityCommentRepository.findByActivity(activity1)).thenReturn(listOfActivityComment)
        val listOfActivityComment2 = listOf(activityComment)
        `when`(activityCommentRepository.save(activityComment)).then {
            `when`(activityCommentRepository.findByActivity(activity2)).thenReturn(listOfActivityComment2)
        }
        `when`(activityCommentRepository.findByActivity(activity3)).thenReturn(null)
    }

    @Test(expected = ActivityCommentService.ActivityCommentDoesNotExist::class)
    fun `return exception if the activity has no comments`() {
        activityCommentService.findByActivity(activity3)
    }

    @Test(expected = ActivityCommentService.ActivityDoesNotExist::class)
    fun `create a comment of unexisting activity`() {
        activityCommentService.createComment(5, email, comment)
    }

    @Test(expected = ActivityCommentService.UserDoesNotExist::class)
    fun `create a comment of unexisting user`() {
        activityCommentService.createComment(1, "user@est.fib.upc.edu", comment)
    }

    @Test
    fun `return list of comments of an activity`() {
        val listOfComments = activityCommentService.findByActivity(activity1)
        assert(listOfComments.size == 2)
        val activityComment1 = listOfComments[0]
        assert(activityComment1.id == 1.toLong())
        assert(activityComment1.comment == comment)
        assert(activityComment1.creationDate == date)
        assert(activityComment1.user == user)
        assert(activityComment1.activity == activity1)
        val activityComment2 = listOfComments[1]
        assert(activityComment2.id == 2.toLong())
        assert(activityComment2.comment == comment)
        assert(activityComment2.creationDate == date)
        assert(activityComment2.user == user)
        assert(activityComment2.activity == activity1)
    }

}
