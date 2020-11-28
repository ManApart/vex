package worldMap

import Color
import de.matthiasmann.twl.utils.PNGDecoder
import level.LevelMapRenderer
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL30
import physics.Rectangle
import java.nio.ByteBuffer

const val screenWidth = 300.0
const val screenHeight = 300.0


fun initializeRender() {
    GL11.glMatrixMode(GL11.GL_PROJECTION)
    GL11.glLoadIdentity()
    GL11.glOrtho(0.0, screenWidth / 2, screenHeight / 2, 0.0, 1.0, -1.0)
    GL11.glMatrixMode(GL11.GL_MODELVIEW)
}


fun loadTexture(fileName: String) {
    val decoder = PNGDecoder(LevelMapRenderer::class.java.getResourceAsStream(fileName))

    val buffer: ByteBuffer = ByteBuffer.allocateDirect(4 * decoder.width * decoder.height)

    decoder.decode(buffer, decoder.width * 4, PNGDecoder.Format.RGBA)
    buffer.flip()

    val id = GL11.glGenTextures()
    GL11.glBindTexture(GL11.GL_TEXTURE_2D, id)
    GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1)
    GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR.toFloat())
    GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR.toFloat())
    GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, decoder.width, decoder.height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer)
    GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D)

}

fun drawBackground() {
    GL11.glBegin(GL11.GL_QUADS)
    GL11.glColor3d(.7, .8, .9)
    GL11.glVertex2d(0.0, 0.0)
    GL11.glVertex2d(screenWidth / 2, 0.0)

    GL11.glColor3d(.5, .6, .9)
    GL11.glVertex2d(screenWidth / 2, screenHeight / 2)
    GL11.glVertex2d(0.0, screenHeight / 2)
    GL11.glEnd()


}

fun drawRectangle(rect: Rectangle, color: Color) {
    val y = (screenHeight - 1 - rect.y).toFloat()
    GL11.glBegin(GL11.GL_QUADS)
    GL11.glColor3f(color.red, color.blue, color.green)
    GL11.glVertex2f(rect.x, y)
    GL11.glVertex2f(rect.x + rect.width, y)

    GL11.glVertex2f(rect.x + rect.width, y + rect.height)
    GL11.glVertex2f(rect.x, y + rect.height)
    GL11.glEnd()
}

fun drawTexture(x: Float, y: Float, width: Float, height: Float) {
//        tex.bind()
    GL11.glTranslatef(x, y, 0f)
    GL11.glBegin(GL11.GL_QUADS)
    GL11.glTexCoord2f(0f, 0f)
    GL11.glVertex2f(0f, 0f)
    GL11.glTexCoord2f(1f, 0f)
    GL11.glVertex2f(width, 0f)
    GL11.glTexCoord2f(1f, 1f)
    GL11.glVertex2f(width, height)
    GL11.glTexCoord2f(0f, 1f)
    GL11.glVertex2f(0f, height)
    GL11.glEnd()
    GL11.glLoadIdentity()
}