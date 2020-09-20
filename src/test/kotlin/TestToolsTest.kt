import org.junit.Assert.assertEquals
import org.junit.Test

class TestToolsTest {

    @Test
    fun createMapBuildsCorrectly() {
        val map = createMap(listOf(
                listOf(0,0,0),
                listOf(0,1,0),
                listOf(1,1,1)
        ))

        assertEquals(TileType.SPACE, map.getTile(0,0).type)
        assertEquals(TileType.SPACE, map.getTile(1,0).type)
        assertEquals(TileType.SPACE, map.getTile(2,0).type)

        assertEquals(TileType.SPACE, map.getTile(0,1).type)
        assertEquals(TileType.TILE, map.getTile(1,1).type)
        assertEquals(TileType.SPACE, map.getTile(2,1).type)

        assertEquals(TileType.TILE, map.getTile(0,2).type)
        assertEquals(TileType.TILE, map.getTile(1,2).type)
        assertEquals(TileType.TILE, map.getTile(2,2).type)

    }

}