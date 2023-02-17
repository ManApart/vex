import level.TileType
import kotlin.test.Test
import kotlin.test.assertEquals

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
        assertEquals(listOf(Vec2()), horizontalPoints(1))
        assertEquals(listOf(Vec2(), Vec2(1, 0), Vec2(2, 0)), horizontalPoints(3))
        assertEquals(listOf(Vec2(1, 2), Vec2(2, 2), Vec2(3, 2)), horizontalPoints(3, 1, 2))
    }

    @Test
    fun createVerticalPoints() {
        assertEquals(listOf(Vec2()), verticalPoints(1))
        assertEquals(listOf(Vec2(), Vec2(0, 1), Vec2(0, 2)), verticalPoints(3))
        assertEquals(listOf(Vec2(1, 2), Vec2(1, 3), Vec2(1, 4)), verticalPoints(3, 1, 2))
    }


}