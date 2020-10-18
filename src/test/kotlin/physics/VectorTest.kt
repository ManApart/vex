package physics

import horizontalPoints
import org.junit.Assert.assertEquals
import org.junit.Test
import verticalPoints

class VectorTest {

    @Test
    fun distance() {
        val source = Vector()

        assertEquals(1, source.distanceTo(Vector(1, 0)))
        assertEquals(2, source.distanceTo(Vector(2, 0)))
        assertEquals(1, source.distanceTo(Vector(0, 1)))
        assertEquals(10, Vector(10, 0).distanceTo(source))
        assertEquals(5, source.distanceTo(Vector(4, 3)))
    }

    @Test
    fun getRay() {
        val source = Vector()

        //Horizontal
        assertEquals(listOf(Vector(0, 0), Vector(1, 0)), source.getRayTo(Vector(1, 0)))
        assertEquals(horizontalPoints(11), source.getRayTo(Vector(10, 0)))

        //Horizontal Negative
        assertEquals(listOf(Vector(0, 0), Vector(1, 0)), source.getRayTo(Vector(1, 0)))
        assertEquals(horizontalPoints(11).reversed(), Vector(10, 0).getRayTo(source))

        //Vertical
        assertEquals(listOf(Vector(1, 0), Vector(0, 0)), Vector(1, 0).getRayTo(source))
        assertEquals(verticalPoints(11), source.getRayTo(Vector(0, 10)))

        //Vertical Negative
        assertEquals(listOf(Vector(0, 0), Vector(0, 1)), source.getRayTo(Vector(0, 1)))
        assertEquals(verticalPoints(11).reversed(), Vector(0, 10).getRayTo(source))

        assertEquals(listOf(Vector(0, 1), Vector(0, 0)), Vector(0, 1).getRayTo(source))

        assertEquals(listOf(Vector(0, 0), Vector(0, 1)), source.getRayTo(Vector(0, 1)))

        assertEquals(listOf(Vector(0, 0), Vector(1, 0), Vector(2, 0)), source.getRayTo(Vector(2, 0)))

        assertEquals(listOf(Vector(0, 0), Vector(1, 1), Vector(2, 2)), source.getRayTo(Vector(2, 2)))

        assertEquals(listOf(Vector(0, 0), Vector(1, 0), Vector(2, 1)), source.getRayTo(Vector(2, 1)))

        assertEquals(listOf(Vector(0, 0)), source.getRayTo(Vector(0.2f, 0f)))
        assertEquals(listOf(Vector(0, 0)), Vector(0.2f, 0f).getRayTo(source))

        assertEquals(listOf(Vector(0, 0)), source.getRayTo(Vector(0f, 0.2f)))

        //Diagonal
        assertEquals(listOf(Vector(0, 0), Vector(1, 1), Vector(2, 2)), source.getRayTo(Vector(2f, 2f)))

    }

}