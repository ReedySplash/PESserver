package edu.pes.laresistencia.model

import org.junit.Assert
import org.junit.Test

class PointsLocTest {
    @Test
    fun pointsLocCreation() {
        val pointsLoc = PointsLoc(
                1,
                "Test pointsLoc",
                41.3907974,
                2.1225344,
                "food"
        )
        Assert.assertEquals(1.toLong(), pointsLoc.id)
        Assert.assertEquals("Test pointsLoc", pointsLoc.name)
        Assert.assertEquals(41.3907974, pointsLoc.latitude)
        Assert.assertEquals(2.1225344, pointsLoc.longitude)
        Assert.assertEquals("food", pointsLoc.kind)
    }
}