package physics

import org.junit.Assert.assertEquals
import org.junit.Test

class RectangleTest {

    @Test
    fun boundsPositive(){
        val rect = Rectangle(0,0, 10, 10)

        assertEquals(Vector(0,0), rect.source())
        assertEquals(Vector(5,5), rect.center())
        assertEquals(Vector(10,10), rect.far())
    }

    @Test
    fun boundsNegative(){
        val rect = Rectangle(-10,-10, 10, 10)

        assertEquals(Vector(-10,-10), rect.source())
        assertEquals(Vector(-5,-5), rect.center())
        assertEquals(Vector(0,0), rect.far())
    }
}