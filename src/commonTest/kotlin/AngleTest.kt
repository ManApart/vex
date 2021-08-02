import com.soywiz.korma.geom.Point
import kotlin.test.Test
import kotlin.test.assertEquals

class AngleTest {

    @Test
    fun angleToPoint(){
        val point = Point(1,0)
        val angle = point.toAngle()
        val actual = angle.toPoint()
        assertEquals(point.x, actual.x)
        assertEquals(point.y, -actual.y)
    }

    @Test
    fun angleToPoint2(){
        val point = Point(0,10)
        val angle = point.toAngle()
        val actual = angle.toPoint()
        assertEquals(-0.0, actual.x)
        assertEquals(1.0, actual.y)
    }
}