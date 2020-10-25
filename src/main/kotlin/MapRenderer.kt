import de.matthiasmann.twl.utils.PNGDecoder
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL30.glGenerateMipmap
import physics.Rectangle
import physics.Vector
import player.Player
import java.nio.ByteBuffer


class MapRenderer(private val map: LevelMap, private val player: Player) {
    private val screenWidth = 300.0
    private val screenHeight = 300.0

    private val tileSize = 5f

    private val tileColor = Color(1f, 1f, 1f)
    private val playerColor = Color(1f, 0f, 0f)

    fun init() {
        glMatrixMode(GL_PROJECTION)
        glLoadIdentity()
        glOrtho(0.0, screenWidth / 2, screenHeight / 2, 0.0, 1.0, -1.0)
        glMatrixMode(GL_MODELVIEW)

    }

    fun render() {
        val offsetX = -player.body.bounds.x * tileSize + 70
        val offsetY = -player.body.bounds.y * tileSize + 230
        drawBackground()
        for (x in 0 until map.getSize()) {
            for (y in 0 until map.getSize()) {
                if (map.getTile(x, y).type == TileType.TILE) {
                    drawRectangle(Rectangle(x * tileSize + offsetX, offsetY + y * tileSize, tileSize, tileSize), tileColor)
//                } else if (map.getTile(x, y) != SPAWN){
//                    println("Got: " + map.getTile(x, y))
                }
            }
        }
        drawRectangle((player.body.bounds * tileSize) + Vector(offsetX, offsetY), playerColor)

    }

    fun loadTexture(fileName: String) {
        val decoder = PNGDecoder(MapRenderer::class.java.getResourceAsStream(fileName))

        val buffer: ByteBuffer = ByteBuffer.allocateDirect(4 * decoder.width * decoder.height)

        decoder.decode(buffer, decoder.width * 4, PNGDecoder.Format.RGBA)
        buffer.flip()

        val id = glGenTextures()
        glBindTexture(GL_TEXTURE_2D, id)
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1)
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR.toFloat())
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR.toFloat())
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.width, decoder.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer)
        glGenerateMipmap(GL_TEXTURE_2D)

    }

    private fun drawBackground() {
        glBegin(GL_QUADS)
        glColor3d(.7, .8, .9)
        glVertex2d(0.0, 0.0)
        glVertex2d(screenWidth/2, 0.0)

        glColor3d(.5, .6, .9)
        glVertex2d(screenWidth/2, screenHeight/2)
        glVertex2d(0.0, screenHeight/2)
        glEnd()


    }

    private fun drawRectangle(rect: Rectangle, color: Color) {
        val y = (screenHeight - 1 - rect.y).toFloat()
        glBegin(GL_QUADS)
        glColor3f(color.red, color.blue, color.green)
        glVertex2f(rect.x, y)
        glVertex2f(rect.x + rect.width, y)

        glVertex2f(rect.x + rect.width, y + rect.height)
        glVertex2f(rect.x, y + rect.height)
        glEnd()
    }

    private fun drawTexture(x: Float, y: Float, width: Float, height: Float) {
//        tex.bind()
        glTranslatef(x, y, 0f)
        glBegin(GL_QUADS)
        glTexCoord2f(0f, 0f)
        glVertex2f(0f, 0f)
        glTexCoord2f(1f, 0f)
        glVertex2f(width, 0f)
        glTexCoord2f(1f, 1f)
        glVertex2f(width, height)
        glTexCoord2f(0f, 1f)
        glVertex2f(0f, height)
        glEnd()
        glLoadIdentity()
    }

}