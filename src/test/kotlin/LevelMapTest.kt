import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import physics.Vector

class LevelMapTest {
    private val map = createMap(listOf(
            listOf(0,0,0),
            listOf(0,1,0),
            listOf(1,1,1)
    ))

    @Test
    fun noCollisionWithRay(){
        val ray = listOf(Vector(0, 0), Vector(1, 0), Vector(1, 0))

        assertNull(map.getFirstCollision(ray))
    }

    @Test
    fun collidesWithRay(){
        val ray = listOf(Vector(0, 1), Vector(1, 1), Vector(1, 1))

        assertEquals(map.getTile(1,1), map.getFirstCollision(ray))
    }

    @Test
    fun collidesWithRayBackwards(){
        val ray = listOf(Vector(2, 0), Vector(1, 1), Vector(0, 2))

        assertEquals(map.getTile(1,1), map.getFirstCollision(ray))
    }

//    @Test
//    fun collidesWithCorner(){
//        val cornerMap = createMap(listOf(
//                listOf(0,1),
//                listOf(1,0)
//        ))
//
//        val ray = listOf(Vector(0, 0), Vector(1, 1))
//
//        //What is expected behavior for this?
//        assertEquals(cornerMap.getTile(1,1), cornerMap.getFirstCollision(ray))
//    }

}