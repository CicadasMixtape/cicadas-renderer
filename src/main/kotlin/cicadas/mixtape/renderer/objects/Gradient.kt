package cicadas.mixtape.renderer.objects

import java.awt.Color

open class Gradient(
    val leftBottomColor: Color,
    val leftTopColor: Color,
    val rightTopColor: Color,
    val rightBottomColor: Color
) {
    companion object {
        @JvmField
        val EMPTY = Gradient(Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE)
    }

    data class All(val color: Color) : Gradient(color, color, color, color)
    data class Horizontal(val leftColor: Color, val rightColor: Color) : Gradient(leftColor, leftColor, rightColor, rightColor)
    data class Vertical(val topColor: Color, val bottomColor: Color) : Gradient(bottomColor, topColor, topColor, bottomColor)
}