package cicadas.mixtape.rendertest.utility.render

import cicadas.mixtape.rendertest.shader.buffer.CustomBuffer
import cicadas.mixtape.rendertest.shader.Pipelines
import cicadas.mixtape.rendertest.api.addVertex
import cicadas.mixtape.rendertest.api.beginQuads
import cicadas.mixtape.rendertest.api.defaultRenderPass
import cicadas.mixtape.rendertest.api.mc
import cicadas.mixtape.rendertest.api.screenSize
import cicadas.mixtape.rendertest.blur.BlurPass
import cicadas.mixtape.rendertest.fonts.Font
import cicadas.mixtape.rendertest.objects.Gradient
import cicadas.mixtape.rendertest.objects.Radius
import cicadas.mixtape.rendertest.shader.Formats
import cicadas.mixtape.rendertest.shader.uniform.FontUniform
import cicadas.mixtape.rendertest.shader.uniform.ScreenUniform
import cicadas.mixtape.rendertest.shader.uniform.SquircleUniform
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.textures.FilterMode
import com.mojang.blaze3d.vertex.Tesselator
import net.minecraft.client.renderer.texture.AbstractTexture
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import java.awt.Color

object Renderer2D {
    fun drawRoundedRect(x: Float, y: Float, width: Float, height: Float, radius: Radius, gradient: Gradient) {
        val builder = Tesselator.getInstance().beginQuads(Formats.RECT)

        builder.addVertex(x,         y + height, width, height, 0f, 1f, radius.leftBottomRadius).setColor(gradient.leftBottomColor.rgb)
        builder.addVertex(x + width, y + height, width, height, 1f, 1f, radius.rightBottomRadius).setColor(gradient.rightBottomColor.rgb)
        builder.addVertex(x + width, y,          width, height, 1f, 0f, radius.rightTopRadius).setColor(gradient.rightTopColor.rgb)
        builder.addVertex(x,         y,          width, height, 0f, 0f, radius.leftTopRadius).setColor(gradient.leftTopColor.rgb)

        val mesh = builder.buildOrThrow()
        val buffer = CustomBuffer.create(mesh)

        RenderSystem.getDevice().createCommandEncoder().defaultRenderPass(Pipelines.RECT_PIPELINE) {
            buffer.drawIndexed(it)
        }

        mesh.close()
    }

    fun drawSquircle(x: Float, y: Float, width: Float, height: Float, squirt: Float, radius: Radius, gradient: Gradient) {
        val builder = Tesselator.getInstance().beginQuads(Formats.RECT)

        builder.addVertex(x,         y + height, width, height, 0f, 1f, radius.leftBottomRadius).setColor(gradient.leftBottomColor.rgb)
        builder.addVertex(x + width, y + height, width, height, 1f, 1f, radius.rightBottomRadius).setColor(gradient.rightBottomColor.rgb)
        builder.addVertex(x + width, y,          width, height, 1f, 0f, radius.rightTopRadius).setColor(gradient.rightTopColor.rgb)
        builder.addVertex(x,         y,          width, height, 0f, 0f, radius.leftTopRadius).setColor(gradient.leftTopColor.rgb)

        val mesh = builder.buildOrThrow()
        val buffer = CustomBuffer.create(mesh)
        val squircleData = SquircleUniform.update(squirt)

        RenderSystem.getDevice().createCommandEncoder().defaultRenderPass(Pipelines.SQUIRCLE_PIPELINE) {
            it.setUniform("SquircleData", squircleData)
            buffer.drawIndexed(it)
        }

        mesh.close()
    }

    fun drawRoundedBlur(x: Float, y: Float, width: Float, height: Float, radius: Radius, gradient: Gradient = Gradient.EMPTY) {
        val builder = Tesselator.getInstance().beginQuads(Formats.RECT)

        builder.addVertex(x,         y + height, width, height, 0f, 1f, radius.leftBottomRadius).setColor(gradient.leftBottomColor.rgb)
        builder.addVertex(x + width, y + height, width, height, 1f, 1f, radius.rightBottomRadius).setColor(gradient.rightBottomColor.rgb)
        builder.addVertex(x + width, y,          width, height, 1f, 0f, radius.rightTopRadius).setColor(gradient.rightTopColor.rgb)
        builder.addVertex(x,         y,          width, height, 0f, 0f, radius.leftTopRadius).setColor(gradient.leftTopColor.rgb)

        val mesh = builder.buildOrThrow()
        val buffer = CustomBuffer.create(mesh)
        val screenData = ScreenUniform.update(mc.mainRenderTarget.screenSize)

        RenderSystem.getDevice().createCommandEncoder().defaultRenderPass(Pipelines.BLUR_PIPELINE) {
            it.bindTexture("sampler0", BlurPass.target().colorTextureView, RenderSystem.getSamplerCache().getClampToEdge(FilterMode.LINEAR))
            it.setUniform("ScreenData", screenData)
            buffer.drawIndexed(it)
        }

        mesh.close()
    }

    fun drawRoundedTexture(id: Identifier, x: Float, y: Float, width: Float, height: Float, radius: Radius = Radius.EMPTY, gradient: Gradient = Gradient.EMPTY) {
        drawRoundedTexture(mc.textureManager.getTexture(id), x, y, width, height, radius, gradient)
    }

    fun drawRoundedTexture(texture: AbstractTexture, x: Float, y: Float, width: Float, height: Float, radius: Radius = Radius.EMPTY, gradient: Gradient = Gradient.EMPTY) {
        val builder = Tesselator.getInstance().beginQuads(Formats.RECT)

        builder.addVertex(x,         y + height, width, height, 0f, 1f, radius.leftBottomRadius).setColor(gradient.leftBottomColor.rgb)
        builder.addVertex(x + width, y + height, width, height, 1f, 1f, radius.rightBottomRadius).setColor(gradient.rightBottomColor.rgb)
        builder.addVertex(x + width, y,          width, height, 1f, 0f, radius.rightTopRadius).setColor(gradient.rightTopColor.rgb)
        builder.addVertex(x,         y,          width, height, 0f, 0f, radius.leftTopRadius).setColor(gradient.leftTopColor.rgb)

        val mesh = builder.buildOrThrow()
        val buffer = CustomBuffer.create(mesh)

        RenderSystem.getDevice().createCommandEncoder().defaultRenderPass(Pipelines.TEXTURE_PIPELINE) {
            it.bindTexture("sampler0", texture.textureView, RenderSystem.getSamplerCache().getClampToEdge(FilterMode.NEAREST))
            buffer.drawIndexed(it)
        }

        mesh.close()
    }

    fun drawText(font: Font, text: Component, x: Float, y: Float, color: Color = Color.WHITE) {
        val msdf = font.msdf
        val size = font.size

        val builder = msdf.applyGlyphs(text, size, x, y,  color)
        val mesh = builder.buildOrThrow()
        val buffer = CustomBuffer.create(mesh)
        val fontData = FontUniform.update(msdf.atlas.distanceRange, msdf.thickness)

        RenderSystem.getDevice().createCommandEncoder().defaultRenderPass(Pipelines.MSDF_PIPELINE) {
            it.bindTexture("sampler0", msdf.texture.textureView, RenderSystem.getSamplerCache().getClampToEdge(FilterMode.NEAREST))
            it.setUniform("FontData", fontData)
            buffer.drawIndexed(it)
        }

        mesh.close()
    }
}