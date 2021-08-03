import com.soywiz.korma.geom.*
import kotlin.math.abs
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AngleTest {

    @Test
    fun angleToPoint0(){
        val point = Point(1,0)
        val angle = point.toAngle()
        assertEquals(0.0, abs(angle.degrees))

        val actual = angle.toPoint()
        assertThresholdEquals(point.x, actual.x)
        assertThresholdEquals(point.y, actual.y)
    }

    @Test
    fun angleToPoint90(){
        val point = Point(0,1)
        val angle = point.toAngle()
        assertEquals(90.0, abs(angle.degrees))

        val actual = angle.toPoint()
        assertThresholdEquals(point.x, actual.x)
        assertThresholdEquals(point.y, actual.y)
    }

    @Test
    fun angleToPoint180(){
        val point = Point(-1,0)
        val angle = point.toAngle()
        assertEquals(180.0, abs(angle.degrees))

        val actual = angle.toPoint()
        assertThresholdEquals(point.x, actual.x)
        assertThresholdEquals(point.y, actual.y)
    }

    @Test
    fun angleToPoint270(){
        val point = Point(0,-1)
        val angle = point.toAngle()
        assertEquals(270.0, abs(angle.degrees))

        val actual = angle.toPoint()
        assertThresholdEquals(point.x, actual.x)
        assertThresholdEquals(point.y, actual.y)
    }

    private fun assertThresholdEquals(expected: Double, actual: Double, threshold: Double = 0.00001) {
        val amount = expected - actual
        assertTrue(abs(amount) < threshold, "$actual is not close to $expected")
    }

}