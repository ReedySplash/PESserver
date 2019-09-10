package edu.pes.laresistencia.service

import edu.pes.laresistencia.model.PointsLoc
import edu.pes.laresistencia.repository.PointsLocRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PointsLocServiceTest() {

    private lateinit var pointsLocService: PointsLocService

    @Mock
    private lateinit var pointsLocRepository: PointsLocRepository

    @Before
    fun setUp() {
        val pointsLoc1 = PointsLoc(
                1,
                "Test activity",
                41.3907974,
                2.1225344,
                "food"
        )

        val pointsLoc2 = PointsLoc(
                2,
                "Test activity2",
                50.3907974,
                10.1225344,
                "food"
        )
        val list = listOf(pointsLoc1,pointsLoc2)
        val array = arrayOf(pointsLoc1,pointsLoc2)
        `when`(pointsLocRepository.findAll()).thenReturn(list)
        `when`(pointsLocRepository.findByKind("food")).thenReturn(array)
    }

    @Test
    fun testFindAll() {
        pointsLocService = PointsLocService(pointsLocRepository)
        val list = pointsLocService.findAll()
        assert(list.size == 2)
        assert(list.get(0).id == 1.toLong())
        assert(list.get(1).id == 2.toLong())
    }

    @Test
    fun testByKind() {
        pointsLocService = PointsLocService(pointsLocRepository)
        val list = pointsLocService.findByKind("food")
        assert(list.size == 2)
        assert(list.get(0).id == 1.toLong())
        assert(list.get(1).id == 2.toLong())
    }
}