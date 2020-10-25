package physics

import horizontalPoints
import org.junit.Assert.assertEquals
import org.junit.Test
import verticalPoints

class VectorTest {
    private val source = Vector()

    @Test
    fun operators() {
        assertEquals(Vector(2,2), Vector(0,1) + Vector(2, 1))
        assertEquals(Vector(2,2), Vector(4,3) - Vector(2, 1))
    }

    @Test
    fun distance() {
        assertEquals(1, source.distanceTo(Vector(1, 0)))
        assertEquals(2, source.distanceTo(Vector(2, 0)))
        assertEquals(1, source.distanceTo(Vector(0, 1)))
        assertEquals(10, Vector(10, 0).distanceTo(source))
        assertEquals(5, source.distanceTo(Vector(4, 3)))
    }

    @Test
    fun getRayNoDistance() {
        assertEquals(listOf(Vector(0, 0)), source.getRayTo(Vector(.2f, .1f)))
        assertEquals(listOf(Vector(20, 30)), Vector(20,30).getRayTo(Vector(20.2f, 30.1f)))
    }

    @Test
    fun getRayHorizontal() {
        assertEquals(listOf(Vector(0, 0), Vector(1, 0)), source.getRayTo(Vector(1, 0)))
        assertEquals("Ray from non 0 origin", listOf(Vector(2, 1), Vector(3, 1)), Vector(2, 1).getRayTo(Vector(3, 1)))
        assertEquals("Partial Ray shouldn't count", listOf(Vector(0, 0)), source.getRayTo(Vector(0.2f, 0f)))
        assertEquals(listOf(Vector(0, 0), Vector(1, 0), Vector(2, 0)), source.getRayTo(Vector(2, 0)))
        assertEquals(horizontalPoints(11), source.getRayTo(Vector(10, 0)))

        //Horizontal Negative
        assertEquals(listOf(Vector(0, 0), Vector(1, 0)), source.getRayTo(Vector(1, 0)))
        assertEquals("Partial Ray shouldn't count", listOf(Vector(0, 0)), Vector(0.2f, 0f).getRayTo(source))
        assertEquals(horizontalPoints(11).reversed(), Vector(10, 0).getRayTo(source))
    }

    @Test
    fun getRayVertical() {
        assertEquals(listOf(Vector(1, 0), Vector(0, 0)), Vector(1, 0).getRayTo(source))
        assertEquals(verticalPoints(11), source.getRayTo(Vector(0, 10)))
        assertEquals("Partial Ray shouldn't count", listOf(Vector(0, 0)), source.getRayTo(Vector(0f, 0.2f)))

        //Vertical Negative
        assertEquals(listOf(Vector(0, 0), Vector(0, 1)), source.getRayTo(Vector(0, 1)))
        assertEquals(listOf(Vector(0, 1), Vector(0, 0)), Vector(0, 1).getRayTo(source))
        assertEquals(verticalPoints(11).reversed(), Vector(0, 10).getRayTo(source))
    }

    @Test
    fun getRayDiagonal() {
        assertEquals(listOf(Vector(0, 0), Vector(1, 1), Vector(2, 2)), source.getRayTo(Vector(2f, 2f)))
        assertEquals(listOf(Vector(0, 0), Vector(1, 0), Vector(2, 1)), source.getRayTo(Vector(2, 1)))

        assertEquals(listOf(Vector(0, 2), Vector(1, 3), Vector(2, 4)), Vector(0, 2).getRayTo(Vector(2f, 4f)))
    }

}