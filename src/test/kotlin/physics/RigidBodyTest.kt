package physics

import TileTemplateType.*
import createAndMoveBody
import createMap
import org.junit.Assert.*
import org.junit.Test

class RigidBodyTest {

    @Test
    fun notCollidedByDefault() {
        val body = createAndMoveBody(listOf(
                listOf(S, G),
                listOf(C, C)
        ))

        assertEquals(Vector(1, 1), body.bounds.source())
        assertFalse(body.isCollidedAny(*Direction.values()))
    }

    @Test
    fun movesForward() {
        val body = createAndMoveBody(listOf(
                listOf(C, C, C),
                listOf(C, C, C),
                listOf(S, O, G)
        ))

        assertEquals(Vector(2, 0), body.bounds.source())
    }

    @Test
    fun doesNotPassThroughCollision() {
        val body = createAndMoveBody(listOf(
                listOf(C, C, C),
                listOf(C, C, C),
                listOf(S, C, G)
        ))

        assertEquals(Vector(0, 0), body.bounds.source())

        assertTrue(body.isCollidedAny(Direction.RIGHT))
        assertFalse(body.isCollidedAny(Direction.LEFT, Direction.DOWN, Direction.UP))
    }

    @Test
    fun stopsInFrontOfCollisionHorizontal() {
        val body = createAndMoveBody(listOf(
                listOf(C, C, C, C),
                listOf(C, C, C, C),
                listOf(C, C, C, C),
                listOf(S, O, O, GG)
        ))

        assertEquals(Vector(2, 0), body.bounds.source())

        assertTrue(body.isCollidedAny(Direction.RIGHT))
        assertFalse(body.isCollidedAny(Direction.LEFT, Direction.DOWN, Direction.UP))
    }

    @Test
    fun stopsInFrontOfPartialCollisionHorizontal() {
        val map = createMap(listOf(
                listOf(1, 1),
                listOf(0, 1)
        ))
        val owner = RigidBodyStubbedOwner()
        val body = RigidBody(map, owner, 1f, 1f)
        body.bounds.x = 0f
        body.bounds.y = 0f

        body.acceleration.x = -1f
        body.velocity.x = 1.5f

        body.update(1f)

        assertEquals(Vector(0, 0), body.bounds.source())

        assertTrue(body.isCollidedAny(Direction.RIGHT))
        assertFalse(body.isCollidedAny(Direction.LEFT, Direction.DOWN, Direction.UP))
    }

    @Test
    fun stopsInFrontOfCollisionHorizontalNegative() {
        val body = createAndMoveBody(listOf(
                listOf(C, C, C, C),
                listOf(C, C, C, C),
                listOf(C, C, C, C),
                listOf(GG, O, O, S)
        ))

        assertEquals(Vector(1, 0), body.bounds.source())

        assertTrue(body.isCollidedAny(Direction.LEFT))
        assertFalse(body.isCollidedAny(Direction.RIGHT, Direction.DOWN, Direction.UP))
    }

    @Test
    fun stopsInFrontOfPartialCollisionHorizontalNegative() {
        val map = createMap(listOf(
                listOf(1, 1),
                listOf(1, 0)
        ))
        val owner = RigidBodyStubbedOwner()
        val body = RigidBody(map, owner, 1f, 1f)
        body.bounds.x = 1f
        body.bounds.y = 0f

        body.acceleration.x = 1f
        body.velocity.x = -1.5f

        body.update(1f)

        assertEquals(Vector(1, 0), body.bounds.source())

        assertTrue(body.isCollidedAny(Direction.LEFT))
        assertFalse(body.isCollidedAny(Direction.RIGHT, Direction.DOWN, Direction.UP))
    }

    @Test
    fun stopsInFrontOfCollisionDown() {
        val body = createAndMoveBody(listOf(
                listOf(S, C, C),
                listOf(O, C, C),
                listOf(GG, C, C)
        ))

        assertEquals(Vector(0, 1), body.bounds.source())

        assertTrue(body.isCollidedAny(Direction.DOWN))
        assertFalse(body.isCollidedAny(Direction.LEFT, Direction.RIGHT, Direction.UP))
    }

    @Test
    fun stopsInFrontOfPartialCollisionDown() {
        val map = createMap(listOf(
                listOf(0, 1),
                listOf(1, 1)
        ))
        val owner = RigidBodyStubbedOwner()
        val body = RigidBody(map, owner, 1f, 1f)
        body.bounds.x = 0f
        body.bounds.y = 1f

        body.velocity.y = 0.5f * Direction.DOWN.vector.y

        body.update(1f)

        assertEquals(Vector(0, 1), body.bounds.source())

        assertTrue(body.isCollidedAny(Direction.DOWN))
        assertFalse(body.isCollidedAny(Direction.LEFT, Direction.RIGHT, Direction.UP))
    }

    @Test
    fun stopsInFrontOfCollisionUp() {
        val body = createAndMoveBody(listOf(
                listOf(GG, C, C),
                listOf(O, C, C),
                listOf(S, C, C)
        ))

        assertEquals(Vector(0, 1), body.bounds.source())

        assertTrue(body.isCollidedAny(Direction.UP))
        assertFalse(body.isCollidedAny(Direction.LEFT, Direction.RIGHT, Direction.DOWN))
    }

    @Test
    fun stopsInFrontOfPartialCollisionUp() {
        val map = createMap(listOf(
                listOf(1, 1),
                listOf(1, 0)
        ))
        val owner = RigidBodyStubbedOwner()
        val body = RigidBody(map, owner, 1f, 1f)
        body.bounds.x = 1f
        body.bounds.y = 0f

        body.velocity.y = 0.5f * Direction.UP.vector.y

        body.update(1f)

        assertEquals(Vector(1, 0), body.bounds.source())

        assertTrue(body.isCollidedAny(Direction.UP))
        assertFalse(body.isCollidedAny(Direction.LEFT, Direction.DOWN, Direction.RIGHT))
    }

    @Test
    fun stopsInFrontOfCollisionDiagonalXFirst() {
        val body = createAndMoveBody(listOf(
                listOf(S, O, O),
                listOf(O, GG, C),
                listOf(C, C, C)
        ))

        assertEquals(Vector(1, 2), body.bounds.source())
    }

    @Test
    fun stopsInFrontOfCollisionDiagonalYSecond() {
        val body = createAndMoveBody(listOf(
                listOf(S, C, C),
                listOf(O, GG, C),
                listOf(C, C, C)
        ))

        assertEquals(Vector(0, 1), body.bounds.source())
    }

    @Test
    fun stopsInFrontOfCollisionDiagonalStuck() {
        val body = createAndMoveBody(listOf(
                listOf(S, C, C),
                listOf(C, GG, C),
                listOf(C, C, C)
        ))

        assertEquals(Vector(0, 2), body.bounds.source())
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

}