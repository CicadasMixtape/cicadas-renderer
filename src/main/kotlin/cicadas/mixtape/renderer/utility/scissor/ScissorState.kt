package cicadas.mixtape.renderer.utility.scissor

import cicadas.mixtape.renderer.api.window

class ScissorState private constructor(
    private var x: Int,
    private var y: Int,
    private var width: Int,
    private var height: Int,
    private var toggled: Boolean
) {
    constructor() : this(0, 0, 0, 0, false)

    fun push(x: Int, y: Int, width: Int, height: Int) {
        toggled = true
        this.x = x
        this.y = y
        this.width = width
        this.height = height
    }

    fun pop() {
        toggled = false
        this.x = 0
        this.y = 0
        this.width = 0
        this.height = 0
    }

    fun x() = x * 2f
    fun y() = window.height - (y + height) * 2f
    fun width() = width * 2f
    fun height() = height * 2f
    fun toggled() = toggled
    fun copy() = ScissorState(x, y, width, height, toggled)
}