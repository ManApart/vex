package player

import com.soywiz.klock.TimeSpan
import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.Bitmap

class PlayerAnimator(image: Bitmap, private val sprite: Sprite) {
    private val animations = buildAnimations(image)

    private fun buildAnimations(image: Bitmap) = mapOf(
        PlayerState.IDLE to anim(image, 0, 4),
        PlayerState.RUNNING to anim(image, 1, 6),
        PlayerState.JUMPING to anim(image, 2, 4),
        PlayerState.FALLING to anim(image, 3, 2),
        PlayerState.DASHING to anim(image, 15, 4),
        PlayerState.GRAPPLING to anim(image, 0, 4),
        PlayerState.WALL_RUNNING to anim(image, 12, 4),
        PlayerState.WALL_JUMPING to anim(image, 11, 4),
    )

    private fun anim(image: Bitmap, row: Int, cols: Int): SpriteAnimation {
        val topMargin = row * 36
        return SpriteAnimation(image, 39, 36, topMargin, columns = cols)
    }

    fun setFacing(right: Boolean) {
        when {
            right && sprite.scaleX < 0 -> sprite.scaleX *= -1
            !right && sprite.scaleX > 0 -> sprite.scaleX *= -1
        }

    }

    fun evaluate(state: PlayerState) {
        sprite.playAnimationLooped(animations[state], spriteDisplayTime = TimeSpan(200.0))
    }

}