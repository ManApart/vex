import org.junit.Assert.assertEquals
import org.junit.Test
import physics.Vector
import TileTemplateType.*
import level.TileType

class TestToolsTest {

    @Test
    fun createMapBuildsCorrectly() {
        val map = createMap(listOf(
                listOf(0, 0, 0),
                listOf(0, 1, 0),
                listOf(1, 1, 1)
        ))

        assertEquals(TileType.TILE, map.getTile(0, 0).type)
        assertEquals(TileType.TILE, map.getTile(1, 0).type)
        assertEquals(TileType.TILE, map.getTile(2, 0).type)

        assertEquals(0, map.getTile(0, 0).x)
        assertEquals(0, map.getTile(0, 0).y)

        assertEquals(TileType.SPACE, map.getTile(0, 1).type)
        assertEquals(TileType.TILE, map.getTile(1, 1).type)
        assertEquals(TileType.SPACE, map.getTile(2, 1).type)

        assertEquals(0, map.getTile(0, 1).x)
        assertEquals(1, map.getTile(0, 1).y)

        assertEquals(TileType.SPACE, map.getTile(0, 2).type)
        assertEquals(TileType.SPACE, map.getTile(1, 2).type)
        assertEquals(TileType.SPACE, map.getTile(2, 2).type)

        assertEquals(2, map.getTile(2, 2).x)
        assertEquals(2, map.getTile(2, 2).y)

    }

    @Test
    fun createHorizontalPoints() {
        assertEquals(listOf(Vector()), horizontalPoints(1))
        assertEquals(listOf(Vector(), Vector(1, 0), Vector(2, 0)), horizontalPoints(3))
        assertEquals(listOf(Vector(1, 2), Vector(2, 2), Vector(3, 2)), horizontalPoints(3, 1, 2))
    }

    @Test
    fun createVerticalPoints() {
        assertEquals(listOf(Vector()), verticalPoints(1))
        assertEquals(listOf(Vector(), Vector(0, 1), Vector(0, 2)), verticalPoints(3))
        assertEquals(listOf(Vector(1, 2), Vector(1, 3), Vector(1, 4)), verticalPoints(3, 1, 2))
    }

    @Test
    fun createAndMoveBuildsCorrectly() {
        val body = createAndMoveBody(listOf(
                listOf(S, O, G),
                listOf(O, C, O),
                listOf(C, C, C)
        ))

        assertEquals(Vector(2, 2), body.bounds.source())
        assertEquals(Vector(2, 0), body.velocity)
        assertEquals(Vector(-1, 0), body.acceleration)
    }

    @Test
    fun createAndMoveBuildsCorrectly2() {
        val body = createAndMoveBody(listOf(
                listOf(O, C, O),
                listOf(C, C, C),
                listOf(S, O, GG)
        ))

        assertEquals(Vector(1, 0), body.bounds.source())
        assertEquals(Vector(2, 0), body.velocity)
        assertEquals(Vector(-1, 0), body.acceleration)
    }


}