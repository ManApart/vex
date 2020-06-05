package physics

import org.junit.Assert.assertEquals
import org.junit.Test

class VectorTest {

    @Test
    fun getRay() {
        val source = Vector()

        assertEquals(listOf(Vector(0, 0), Vector(1, 0)), source.getRayTo(Vector(1, 0)))

        assertEquals(listOf(Vector(0, 0), Vector(0, 1)), source.getRayTo(Vector(0, 1)))

        assertEquals(listOf(Vector(0, 0), Vector(1, 0), Vector(2, 0)), source.getRayTo(Vector(2, 0)))

        assertEquals(listOf(Vector(0, 0)), source.getRayTo(Vector(0.2f, 0f)))

        assertEquals(listOf(Vector(0, 0), Vector(1, 1), Vector(2, 2)), source.getRayTo(Vector(2, 2)))

        assertEquals(listOf(Vector(0, 0), Vector(1, 1), Vector(2, 1)), source.getRayTo(Vector(2, 1)))
    }

}