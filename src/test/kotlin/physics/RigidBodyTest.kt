package physics

import createMap
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class RigidBodyTest {

    private val map = createMap(listOf(
            listOf(0,0,0,0),
            listOf(0,0,0,1),
            listOf(0,1,0,0),
            listOf(1,1,1,1)
    ))

    @Test
    fun notCollidedByDefault(){
        val owner = RigidBodyStubbedOwner()
        val body = RigidBody(map, owner, 1f, 1f)
        body.bounds.x = 0f
        body.bounds.y = 1f

        body.update(1f)

        assertEquals(0f, body.bounds.x)
        assertEquals(1f, body.bounds.y)

        assertFalse(body.isCollidedAny(*Direction.values()))
    }

    @Test
    fun movesForward(){
        val owner = RigidBodyStubbedOwner()
        val body = RigidBody(map, owner, 1f, 1f)
        body.bounds.x = 0f
        body.bounds.y = 1f

        //try move from 0,1 to 2,1
        body.acceleration.x = 1f
        body.velocity.x = 1f

        body.update(1f)

        assertEquals(2f, body.bounds.x)
        assertEquals(1f, body.bounds.y)

    }

    @Test
    fun doesNotPassThroughCollision(){
        val owner = RigidBodyStubbedOwner()
        val body = RigidBody(map, owner, 1f, 1f)
        body.bounds.x = 0f
        body.bounds.y = 2f

        //try move from 0,1 to 2,1
        body.acceleration.x = 1f
        body.velocity.x = 1f

        body.update(1f)

        assertEquals(0f, body.bounds.x)
        assertEquals(2f, body.bounds.y)

    }

    @Test
    fun stopsInFrontOfCollisionHorizontal(){
        val owner = RigidBodyStubbedOwner()
        val body = RigidBody(map, owner, 1f, 1f)
        body.bounds.x = 0f
        body.bounds.y = 1f

        //try move from 0,1 to 3,1
        body.acceleration.x = 1f
        body.velocity.x = 2f

        body.update(1f)

        assertEquals(2f, body.bounds.x)
        assertEquals(1f, body.bounds.y)

    }

    @Test
    fun stopsInFrontOfCollisionHorizontalNegative(){
        val owner = RigidBodyStubbedOwner()
        val body = RigidBody(map, owner, 1f, 1f)
        body.bounds.x = 3f
        body.bounds.y = 2f

        //try move from 3,2 to 0,2
        body.acceleration.x = 1f
        body.velocity.x = -4f

        body.update(1f)

        assertEquals(2f, body.bounds.x)
        assertEquals(2f, body.bounds.y)
    }

    @Test
    fun stopsInFrontOfCollisionDown(){
        val owner = RigidBodyStubbedOwner()
        val body = RigidBody(map, owner, 1f, 1f)
        body.bounds.x = 0f
        body.bounds.y = 0f

        //try move from 0,0 to 0,3
        body.velocity.y = 3f

        body.update(1f)

        assertEquals(0f, body.bounds.x)
        assertEquals(2f, body.bounds.y)
    }

    @Test
    fun stopsInFrontOfCollisionUp(){
        val owner = RigidBodyStubbedOwner()
        val body = RigidBody(map, owner, 1f, 1f)
        body.bounds.x = 3f
        body.bounds.y = 2f

        //try move from 3,2 to 3,0
        body.velocity.y = -3f

        body.update(1f)

        assertEquals(3f, body.bounds.x)
        assertEquals(2f, body.bounds.y)
    }

    @Test
    fun stopsInFrontOfCollisionDiagonalXFirst(){
        val owner = RigidBodyStubbedOwner()
        val body = RigidBody(map, owner, 1f, 1f)
        body.bounds.x = 0f
        body.bounds.y = 1f

        //try move from 0,1 to 1,2
        /*
        0,0,0,0
        S,E,0,1
        0,1G,0,0
        1,1,1,1
        */

        body.acceleration.x = -1f
        body.velocity.x = 2f
        body.velocity.y = 1f

        body.update(1f)

        assertEquals(1f, body.bounds.x)
        assertEquals(1f, body.bounds.y)
    }

    @Test
    fun stopsInFrontOfCollisionDiagonalYSecond(){
        val map = createMap(listOf(
                listOf(0,1,0),
                listOf(0,1,1),
                listOf(1,1,1)
        ))
        val owner = RigidBodyStubbedOwner()
        val body = RigidBody(map, owner, 1f, 1f)
        body.bounds.x = 0f
        body.bounds.y = 0f

        //try move from 0,0 to 1,1
        /*
        S,1,0
        E,1G,1
        */

        body.acceleration.x = -1f
        body.velocity.x = 2f
        body.velocity.y = 1f

        body.update(1f)

        assertEquals(0f, body.bounds.x)
        assertEquals(1f, body.bounds.y)
    }

    @Test
    fun stopsInFrontOfCollisionDiagonalStuck(){
        val owner = RigidBodyStubbedOwner()
        val body = RigidBody(map, owner, 1f, 1f)
        body.bounds.x = 0f
        body.bounds.y = 2f

        //try move from 0,2 to 1,3
        /*
        0,0,0,0
        0,0,0,1
        S,1,0,0
        1,G1,1,1
        */
        body.acceleration.x = -1f
        body.velocity.x = 2f
        body.velocity.y = 1f

        body.update(1f)

        assertEquals(0f, body.bounds.x)
        assertEquals(2f, body.bounds.y)
    }
//
//    @Test
//    fun stopsInFrontOfCollisionDiagonalCorner(){
//        val map = createMap(listOf(
//                listOf(0,1),
//                listOf(1,0)
//        ))
//
//        val owner = RigidBodyStubbedOwner()
//        val body = RigidBody(map, owner, 1f, 1f)
//        body.bounds.x = 0f
//        body.bounds.y = 2f
//
//        //try move from 0,0 to 1,1
//        body.acceleration.x = -1f
//        body.velocity.x = 2f
//        body.velocity.y = 1f
//
//        body.update(1f)
//
//        assertEquals(0f, body.bounds.x)
//        assertEquals(0f, body.bounds.y)
//    }

    //test proper collides with things are set

}