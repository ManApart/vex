package physics

import createMap
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class RigidBodyTest {

    private val blockedMap = createMap(listOf(
            listOf(0,0,0),
            listOf(0,1,0),
            listOf(1,1,1)
    ))

    private val openMap = createMap(listOf(
            listOf(0,0,0),
            listOf(0,0,0),
            listOf(1,1,1)
    ))

    @Test
    fun notCollidedByDefault(){
        val owner = RigidBodyStubbedOwner()
        val body = RigidBody(blockedMap, owner, 1f, 1f)
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
        val body = RigidBody(openMap, owner, 1f, 1f)
        body.bounds.x = 0f
        body.bounds.y = 1f

        //move from 0,1 to 2,1
        body.acceleration.x = 1f
        body.velocity.x = 1f

        body.update(1f)

        assertEquals(2f, body.bounds.x)
        assertEquals(1f, body.bounds.y)

    }

    @Test
    fun doesNotPassThroughCollision(){
        val owner = RigidBodyStubbedOwner()
        val body = RigidBody(blockedMap, owner, 1f, 1f)
        body.bounds.x = 0f
        body.bounds.y = 1f

        //move from 0,1 to 2,1
        body.acceleration.x = 1f
        body.velocity.x = 1f

        body.update(1f)

        assertEquals(0f, body.bounds.x)
        assertEquals(1f, body.bounds.y)

    }

}