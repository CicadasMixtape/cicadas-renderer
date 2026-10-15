package cicadas.mixtape.rendertest.utility.math

object MathUtility {
    fun isHovered(x: Float, y: Float, width: Float, height: Float, mouseX: Float, mouseY: Float) = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height
}