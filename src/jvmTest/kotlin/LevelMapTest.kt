import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class LevelMapTest {
    private val map = createMap(listOf(
            listOf(0,0,0),
            listOf(0,1,0),
            listOf(1,1,1)
    ))

    @Test
    fun noCollisionWithRay(){
        val ray = listOf(Vec2(0, 2), Vec2(1, 2), Vec2(1, 2))

        assertNull(map.getFirstCollision(ray))
    }

    @Test
    fun collidesWithRay(){
        val ray = listOf(Vec2(0, 1), Vec2(1, 1), Vec2(1, 1))

        assertEquals(map.getTile(1,1), map.getFirstCollision(ray))
    }

    @Test
    fun collidesWithRayBackwards(){
        val ray = listOf(Vec2(2, 2), Vec2(1, 1), Vec2(0, 0))

        assertEquals(map.getTile(1,1), map.getFirstCollision(ray))
    }

//    @Test
//    fun collidesWithCorner(){
//        val cornerMap = createMap(listOf(
//                listOf(0,1),
//                listOf(1,0)
//        ))
//
//        val ray = listOf(Vec2(0, 0), Vec2(1, 1))
//
//        //What is expected behavior for this?
//        assertEquals(cornerMap.getTile(1,1), cornerMap.getFirstCollision(ray))
//    }

}