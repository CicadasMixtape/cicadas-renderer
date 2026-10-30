package cicadas.mixtape.renderer.utility.color

import cicadas.mixtape.renderer.api.mul
import java.awt.Color

class ShaderColor {
    private var current = Color.WHITE!!
    private val stack = ArrayDeque<Color>()

    fun backup() {
        stack.addLast(current)
    }

    fun reset() {
        current = stack.removeLast()
    }

    fun set(color: Color) {
        current = current.mul(color)
    }

    fun current() = current
}