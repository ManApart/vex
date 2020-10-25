package physics

class RigidBodyStubbedOwner : RigidBodyOwner {
    val collided = mutableMapOf<Direction, Boolean>()

    override fun onCollided(direction: Direction) {
        collided[direction] = true
    }

    override fun onNoLongerCollided(direction: Direction) {
        collided[direction] = false
    }
}