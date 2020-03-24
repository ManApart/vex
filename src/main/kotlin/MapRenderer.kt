import org.lwjgl.opengl.GL11.*


class MapRenderer(private val map: Map) {
    private val width = 100.0
    private val height = 100.0
    private val tileSize = 5f

    fun init() {
        glMatrixMode(GL_PROJECTION)
        glLoadIdentity()
        glOrtho(0.0, width, height, 0.0, 1.0, -1.0)
        glMatrixMode(GL_MODELVIEW)

    }


    fun render() {
//        drawTexture(0f, 0f, 20f, 10f)
        drawBackground()
        for (x in 0 until map.getSize()) {
            for (y in 0 until map.getSize()) {
                if (map.getTile(x, y) == 1) {
                    drawSquare(x * tileSize, y * tileSize, tileSize)
                }
            }
        }
    }

//    fun LoadTexture(path: String?, fileType: String?): Texture? {
//        var texture: Texture? = null
//        val `in`: InputStream = ResourceLoader.getResourceAsStream(path)
//        try {
//            texture = TextureLoader.getTexture(fileType, `in`)
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//        return texture
//    }

    private fun drawBackground() {
        glBegin(GL_QUADS)
        glColor3d(.7, .8, .9)
        glVertex2d(0.0, 0.0)
        glVertex2d(width, 0.0)

        glColor3d(.5, .6, .9)
        glVertex2d(width, height)
        glVertex2d(0.0, height)
        glEnd()


    }

    private fun drawSquare(x: Float, y: Float, size: Float) {
        glBegin(GL_QUADS)
        glColor3d(0.0, 1.0, 0.0)
        glVertex2f(x, y)
        glVertex2f(x + size, y)

        glVertex2f(x + size, y + size)
        glVertex2f(x, y + size)
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