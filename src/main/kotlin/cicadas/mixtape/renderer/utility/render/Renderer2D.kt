package cicadas.mixtape.renderer.utility.render

import cicadas.mixtape.renderer.api.addVertex
import cicadas.mixtape.renderer.shader.CicadasPipelines
import cicadas.mixtape.renderer.api.mainTarget
import cicadas.mixtape.renderer.api.mc
import cicadas.mixtape.renderer.api.mul
import cicadas.mixtape.renderer.api.screenSize
import cicadas.mixtape.renderer.api.window
import cicadas.mixtape.renderer.batching.Batched
import cicadas.mixtape.renderer.batching.Batching
import cicadas.mixtape.renderer.pass.KawasePass
import cicadas.mixtape.renderer.font.Font
import cicadas.mixtape.renderer.objects.Gradient
import cicadas.mixtape.renderer.objects.Radius
import cicadas.mixtape.renderer.objects.Squirt
import cicadas.mixtape.renderer.shader.sampler.CustomSampler
import cicadas.mixtape.renderer.shader.storage.api.UniformStorage
import cicadas.mixtape.renderer.shader.uniform.LiquidUniform
import cicadas.mixtape.renderer.shader.uniform.MsdfUniform
import cicadas.mixtape.renderer.shader.uniform.ProjectionUniform
import cicadas.mixtape.renderer.shader.uniform.ScreenUniform
import cicadas.mixtape.renderer.svg.SVGTexture
import com.mojang.blaze3d.textures.FilterMode
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.renderer.texture.AbstractTexture
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import org.joml.Matrix4f
import java.awt.Color

object Renderer2D {
    private var projection = Matrix4f()
    private var modelView = Matrix4f()

    private val batching = Batching()
    private var backupColor = Color.WHITE
    private var shaderColor = Color.WHITE

    fun drawRect(
        stack: PoseStack,
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        gradient: Gradient
    ) {
        batching.add(
            Batched.builder()
                .pipeline(CicadasPipelines.POSITION_COLOR_PIPELINE)
                .vertex {
                    it.addVertex(stack.last().pose(), x,         y + height, 0f).setColor(gradient.leftBottomColor.mul(shaderColor).rgb)
                    it.addVertex(stack.last().pose(), x + width, y + height, 0f).setColor(gradient.rightBottomColor.mul(shaderColor).rgb)
                    it.addVertex(stack.last().pose(), x + width, y,          0f).setColor(gradient.rightTopColor.mul(shaderColor).rgb)
                    it.addVertex(stack.last().pose(), x,         y,          0f).setColor(gradient.leftTopColor.mul(shaderColor).rgb)
                    1
                }
                .build()
        )
    }

    fun drawRoundedRect(
        stack: PoseStack,
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        radius: Radius,
        gradient: Gradient
    ) {
        batching.add(
            Batched.builder()
                .pipeline(CicadasPipelines.RECT_PIPELINE)
                .vertex {
                    it.addVertex(stack.last().pose(), x,         y + height, width, height, radius.leftBottomRadius).setColor(gradient.leftBottomColor.mul(shaderColor).rgb)
                    it.addVertex(stack.last().pose(), x + width, y + height, width, height, radius.rightBottomRadius).setColor(gradient.rightBottomColor.mul(shaderColor).rgb)
                    it.addVertex(stack.last().pose(), x + width, y,          width, height, radius.rightTopRadius).setColor(gradient.rightTopColor.mul(shaderColor).rgb)
                    it.addVertex(stack.last().pose(), x,         y,          width, height, radius.leftTopRadius).setColor(gradient.leftTopColor.mul(shaderColor).rgb)
                    1
                }
                .build()
        )
    }

    fun drawTexture(
        stack: PoseStack,
        texture: AbstractTexture,
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        gradient: Gradient = Gradient.EMPTY
    ) {
        batching.add(
            Batched.builder()
                .pipeline(CicadasPipelines.POSITION_TEXTURE_COLOR_PIPELINE)
                .sampler(CustomSampler(0, texture.textureView, FilterMode.NEAREST))
                .vertex {
                    it.addVertex(stack.last().pose(), x,         y + height, 0f).setUv(0f, 1f).setColor(gradient.leftBottomColor.mul(shaderColor).rgb)
                    it.addVertex(stack.last().pose(), x + width, y + height, 0f).setUv(1f, 1f).setColor(gradient.rightBottomColor.mul(shaderColor).rgb)
                    it.addVertex(stack.last().pose(), x + width, y,          0f).setUv(1f, 0f).setColor(gradient.rightTopColor.mul(shaderColor).rgb)
                    it.addVertex(stack.last().pose(), x,         y,          0f).setUv(0f, 0f).setColor(gradient.leftTopColor.mul(shaderColor).rgb)
                    1
                }
                .build()
        )
    }

    fun drawTexture(
        stack: PoseStack,
        id: Identifier,
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        gradient: Gradient = Gradient.EMPTY
    ) {
        drawTexture(stack, mc.textureManager.getTexture(id), x, y, width, height, gradient)
    }

    fun drawRoundedTexture(
        stack: PoseStack,
        texture: AbstractTexture,
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        radius: Radius = Radius.EMPTY,
        gradient: Gradient = Gradient.EMPTY
    ) {
        batching.add(
            Batched.builder()
                .pipeline(CicadasPipelines.TEXTURE_PIPELINE)
                .sampler(CustomSampler(0, texture.textureView, FilterMode.NEAREST))
                .vertex {
                    it.addVertex(stack.last().pose(), x,         y + height, width, height, radius.leftBottomRadius).setColor(gradient.leftBottomColor.mul(shaderColor).rgb)
                    it.addVertex(stack.last().pose(), x + width, y + height, width, height, radius.rightBottomRadius).setColor(gradient.rightBottomColor.mul(shaderColor).rgb)
                    it.addVertex(stack.last().pose(), x + width, y,          width, height, radius.rightTopRadius).setColor(gradient.rightTopColor.mul(shaderColor).rgb)
                    it.addVertex(stack.last().pose(), x,         y,          width, height, radius.leftTopRadius).setColor(gradient.leftTopColor.mul(shaderColor).rgb)
                    1
                }
                .build()
        )
    }

    fun drawRoundedTexture(
        stack: PoseStack,
        id: Identifier,
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        radius: Radius = Radius.EMPTY,
        gradient: Gradient = Gradient.EMPTY
    ) {
        drawRoundedTexture(stack, mc.textureManager.getTexture(id), x, y, width, height, radius, gradient)
    }

    fun drawSquircle(
        stack: PoseStack,
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        squirt: Squirt,
        radius: Radius,
        gradient: Gradient
    ) {
        batching.add(
            Batched.builder()
                .pipeline(CicadasPipelines.SQUIRCLE_PIPELINE)
                .vertex {
                    it.addVertex(stack.last().pose(), x,         y + height, width, height, radius.leftBottomRadius,  squirt.leftBottomSquirt).setColor(gradient.leftBottomColor.mul(shaderColor).rgb)
                    it.addVertex(stack.last().pose(), x + width, y + height, width, height, radius.rightBottomRadius, squirt.rightBottomSquirt).setColor(gradient.rightBottomColor.mul(shaderColor).rgb)
                    it.addVertex(stack.last().pose(), x + width, y,          width, height, radius.rightTopRadius,    squirt.rightTopSquirt).setColor(gradient.rightTopColor.mul(shaderColor).rgb)
                    it.addVertex(stack.last().pose(), x,         y,          width, height, radius.leftTopRadius,     squirt.leftTopSquirt).setColor(gradient.leftTopColor.mul(shaderColor).rgb)
                    1
                }
                .build()
        )
    }

    fun drawRoundedBlur(
        stack: PoseStack,
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        radius: Radius,
        gradient: Gradient = Gradient.EMPTY
    ) {
        batching.add(
            Batched.builder()
                .pipeline(CicadasPipelines.BLUR_PIPELINE)
                .sampler(CustomSampler(0, KawasePass.texture(), FilterMode.LINEAR))
                .slice(ScreenUniform.update(mainTarget.screenSize))
                .vertex {
                    it.addVertex(stack.last().pose(), x,         y + height, width, height, radius.leftBottomRadius).setColor(gradient.leftBottomColor.mul(shaderColor).rgb)
                    it.addVertex(stack.last().pose(), x + width, y + height, width, height, radius.rightBottomRadius).setColor(gradient.rightBottomColor.mul(shaderColor).rgb)
                    it.addVertex(stack.last().pose(), x + width, y,          width, height, radius.rightTopRadius).setColor(gradient.rightTopColor.mul(shaderColor).rgb)
                    it.addVertex(stack.last().pose(), x,         y,          width, height, radius.leftTopRadius).setColor(gradient.leftTopColor.mul(shaderColor).rgb)
                    1
                }
                .build()
        )
    }

    fun drawLiquidGlass(
        stack: PoseStack,
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        power: Float,
        clean: Boolean,
        radius: Radius,
        gradient: Gradient = Gradient.EMPTY
    ) {
        batching.add(
            Batched.builder()
                .pipeline(CicadasPipelines.LIQUID_GLASS)
                .sampler(CustomSampler(0, if (clean) mainTarget.colorTextureView else KawasePass.texture(), FilterMode.LINEAR))
                .slice(LiquidUniform.update(power))
                .slice(ScreenUniform.update(mainTarget.screenSize))
                .vertex {
                    it.addVertex(stack.last().pose(), x,         y + height, width, height, radius.leftBottomRadius).setColor(gradient.leftBottomColor.mul(shaderColor).rgb)
                    it.addVertex(stack.last().pose(), x + width, y + height, width, height, radius.rightBottomRadius).setColor(gradient.rightBottomColor.mul(shaderColor).rgb)
                    it.addVertex(stack.last().pose(), x + width, y,          width, height, radius.rightTopRadius).setColor(gradient.rightTopColor.mul(shaderColor).rgb)
                    it.addVertex(stack.last().pose(), x,         y,          width, height, radius.leftTopRadius).setColor(gradient.leftTopColor.mul(shaderColor).rgb)
                    1
                }
                .build()
        )
    }

    fun drawText(
        stack: PoseStack,
        font: Font,
        text: Component,
        x: Float,
        y: Float,
        color: Color = Color.WHITE
    ) {
        val msdf = font.msdf
        val size = font.size

        batching.add(
            Batched.builder()
                .pipeline(CicadasPipelines.MSDF_PIPELINE)
                .sampler(CustomSampler(0, msdf.texture.textureView, FilterMode.LINEAR))
                .slice(MsdfUniform.update(msdf.atlas.distanceRange, msdf.thickness))
                .vertex { msdf.applyGlyphs(it, stack.last().pose(), text, size, x, y, color.mul(shaderColor)) }
                .build()
        )
    }

    fun drawText(
        stack: PoseStack,
        font: Font,
        text: String,
        x: Float,
        y: Float,
        color: Color = Color.WHITE
    ) {
        drawText(stack, font, Component.literal(text), x, y, color)
    }

    fun drawCenteredText(
        stack: PoseStack,
        font: Font,
        text: Component,
        x: Float,
        y: Float,
        color: Color = Color.WHITE
    ) {
        drawText(stack, font, text, x - font.getWidth(text) / 2f, y, color)
    }

    fun drawCenteredText(
        stack: PoseStack,
        font: Font,
        text: String,
        x: Float,
        y: Float,
        color: Color = Color.WHITE
    ) {
        drawText(stack, font, text, x - font.getWidth(text) / 2f, y, color)
    }

    fun drawRightText(
        stack: PoseStack,
        font: Font,
        text: Component,
        x: Float,
        y: Float,
        color: Color = Color.WHITE
    ) {
        drawText(stack, font, text, x - font.getWidth(text), y, color)
    }

    fun drawRightText(
        stack: PoseStack,
        font: Font,
        text: String,
        x: Float,
        y: Float,
        color: Color = Color.WHITE
    ) {
        drawText(stack, font, text, x - font.getWidth(text), y, color)
    }

    fun drawVector(
        stack: PoseStack,
        texture: SVGTexture,
        x: Float,
        y: Float,
        gradient: Gradient = Gradient.EMPTY
    ) {
        drawTexture(stack, texture, x, y, texture.width(), texture.height(), gradient)
    }

    fun update() {
        val width = window.width.toFloat() / 2f
        val height = window.height.toFloat() / 2f
        val projectionUniform = ProjectionUniform.update(projection.setOrtho(0f, width, height, 0f, -1000f, 1000f), modelView)
        batching.update(projectionUniform)
        UniformStorage.endFrame()
    }

    fun backupColor() {
        backupColor = shaderColor
    }

    fun shaderColor(shaderColor: Color) {
        this.shaderColor = this.shaderColor.mul(shaderColor)
    }

    fun resetColor() {
        shaderColor = backupColor
    }
}