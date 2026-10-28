package cicadas.mixtape.renderer.font

import net.minecraft.network.chat.Component

data class Font(
    val msdf: MsdfFont,
    val size: Float
) {
    fun getWidth(text: Component) = msdf.getWidth(text, size)
    fun getWidth(text: String) = msdf.getWidth(text, size)
    fun getHeight() = msdf.getHeight(size)
}