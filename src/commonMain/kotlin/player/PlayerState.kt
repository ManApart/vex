package player

enum class PlayerState {
    IDLE,
    RUNNING,
    JUMPING,
    FALLING,
    DASHING,
    GRAPPLING,
    WALL_RUNNING,
    WALL_JUMPING;

    fun isInState(vararg states: PlayerState): Boolean {
        return states.contains(this)
    }
}