package cicadas.mixtape.renderer.font

import cicadas.mixtape.renderer.shader.uniform.MsdfUniform
import com.mojang.blaze3d.vertex.BufferBuilder
import net.minecraft.network.chat.Component
import org.joml.Matrix4f
import java.awt.Color

data class Font(
    private val msdf: MsdfFont,
    private val size: Float
) {
    fun getWidth(text: Component) = msdf.getWidth(text, size)
    fun getWidth(text: String) = msdf.getWidth(text, size)
    fun getHeight() = msdf.getHeight(size)

    fun textureView() = msdf.textureView()
    fun uniform() = MsdfUniform.update(msdf.distanceRange(), msdf.thickness())
    fun glyphs(builder: BufferBuilder, matrix4f: Matrix4f, text: Component, x: Float, y: Float, color: Color) = msdf.applyGlyphs(builder, matrix4f, text, size, x, y, color)
}