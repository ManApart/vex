import input.Controller
import input.ControllerDebugger
import org.lwjgl.Version
import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFW.glfwGetTime
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil
import player.Player

object Vex {
    var window: Long = 0
    val map = LevelMapBuilder().createMap()
    val player = Player(map)
    private val mapRenderer = MapRenderer(map, player)

    init {
        map.spawnPlayer(player)
    }

    fun run() {
        println("Hello LWJGL " + Version.getVersion() + "!")
        init()
        loop()
        shutDown()
    }

    private fun init() {
        GLFWErrorCallback.createPrint(System.err).set()

        check(GLFW.glfwInit()) { "Unable to initialize GLFW" }

        GLFW.glfwDefaultWindowHints()
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE)
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE)

        window = GLFW.glfwCreateWindow(600, 600, "Vex", MemoryUtil.NULL, MemoryUtil.NULL)
        if (window == MemoryUtil.NULL) throw RuntimeException("Failed to create the GLFW window")

        GLFW.glfwSetKeyCallback(window) { window: Long, key: Int, scancode: Int, action: Int, mods: Int ->
            if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) GLFW.glfwSetWindowShouldClose(window, true)
        }
        MemoryStack.stackPush().use { stack ->
            val pWidth = stack.mallocInt(1)
            val pHeight = stack.mallocInt(1)

            GLFW.glfwGetWindowSize(window, pWidth, pHeight)

            val vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor())

            GLFW.glfwSetWindowPos(
                    window,
                    (vidmode!!.width() - pWidth[0]) / 2,
                    (vidmode.height() - pHeight[0]) / 2
            )
        }

        GLFW.glfwMakeContextCurrent(window)
        GLFW.glfwSwapInterval(1)

        GLFW.glfwShowWindow(window)

        GL.createCapabilities()
        GL11.glClearColor(0.0f, 0.0f, 1.0f, 0.0f)
        mapRenderer.init()
    }

    private fun loop() {
        var lastTime = glfwGetTime()
        while (!GLFW.glfwWindowShouldClose(window)) {
            val newTime = glfwGetTime()
            val deltaTime = (newTime - lastTime).toFloat()
            lastTime = newTime
            processInput(deltaTime)
            render()
            player.update(deltaTime)
        }
    }

    private fun render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT)
        mapRenderer.render()
        GLFW.glfwSwapBuffers(window)
    }

    private fun processInput(deltaTime: Float) {
        GLFW.glfwPollEvents()
        Controller.update(deltaTime)
        ControllerDebugger.update()
    }

    private fun shutDown() {
        Callbacks.glfwFreeCallbacks(window)
        GLFW.glfwDestroyWindow(window)
        GLFW.glfwTerminate()
        GLFW.glfwSetErrorCallback(null)?.free()
    }

}