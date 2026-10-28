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
import cicadas.mixtape.renderer.shader.uniform.LiquidUniform
import cicadas.mixtape.renderer.shader.uniform.MsdfUniform
import cicadas.mixtape.renderer.shader.uniform.ProjectionUniform
import cicadas.mixtape.renderer.shader.uniform.ScreenUniform
import com.mojang.blaze3d.textures.FilterMode
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.renderer.texture.AbstractTexture
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import org.joml.Matrix4f
import java.awt.Color

object Renderer2D {
    private var stack = PoseStack()
    private val batching = Batching()

    private var projection = Matrix4f()
    private var modelView = Matrix4f()
    private var globalColor = Color.WHITE

    fun drawRoundedRect(
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
                    it.addVertex(stack.last().pose(), x,         y + height, width, height, radius.leftBottomRadius).setColor(gradient.leftBottomColor.mul(globalColor).rgb)
                    it.addVertex(stack.last().pose(), x + width, y + height, width, height, radius.rightBottomRadius).setColor(gradient.rightBottomColor.mul(globalColor).rgb)
                    it.addVertex(stack.last().pose(), x + width, y,          width, height, radius.rightTopRadius).setColor(gradient.rightTopColor.mul(globalColor).rgb)
                    it.addVertex(stack.last().pose(), x,         y,          width, height, radius.leftTopRadius).setColor(gradient.leftTopColor.mul(globalColor).rgb)
                    1
                }
                .build()
        )
    }

    fun drawRoundedTexture(
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
                .sampler(CustomSampler("Sampler0", texture.textureView, FilterMode.NEAREST))
                .vertex {
                    it.addVertex(stack.last().pose(), x,         y + height, width, height, radius.leftBottomRadius).setColor(gradient.leftBottomColor.mul(globalColor).rgb)
                    it.addVertex(stack.last().pose(), x + width, y + height, width, height, radius.rightBottomRadius).setColor(gradient.rightBottomColor.mul(globalColor).rgb)
                    it.addVertex(stack.last().pose(), x + width, y,          width, height, radius.rightTopRadius).setColor(gradient.rightTopColor.mul(globalColor).rgb)
                    it.addVertex(stack.last().pose(), x,         y,          width, height, radius.leftTopRadius).setColor(gradient.leftTopColor.mul(globalColor).rgb)
                    1
                }
                .build()
        )
    }

    fun drawRoundedTexture(
        id: Identifier,
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        radius: Radius = Radius.EMPTY,
        gradient: Gradient = Gradient.EMPTY
    ) {
        drawRoundedTexture(mc.textureManager.getTexture(id), x, y, width, height, radius, gradient)
    }

    fun drawSquircle(
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
                    it.addVertex(stack.last().pose(), x,         y + height, width, height, radius.leftBottomRadius,  squirt.leftBottomSquirt).setColor(gradient.leftBottomColor.mul(globalColor).rgb)
                    it.addVertex(stack.last().pose(), x + width, y + height, width, height, radius.rightBottomRadius, squirt.rightBottomSquirt).setColor(gradient.rightBottomColor.mul(globalColor).rgb)
                    it.addVertex(stack.last().pose(), x + width, y,          width, height, radius.rightTopRadius,    squirt.rightTopSquirt).setColor(gradient.rightTopColor.mul(globalColor).rgb)
                    it.addVertex(stack.last().pose(), x,         y,          width, height, radius.leftTopRadius,     squirt.leftTopSquirt).setColor(gradient.leftTopColor.mul(globalColor).rgb)
                    1
                }
                .build()
        )
    }

    fun drawRoundedBlur(
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
                .sampler(CustomSampler("Sampler0", KawasePass.texture(), FilterMode.LINEAR))
                .slice(ScreenUniform.update(mainTarget.screenSize))
                .vertex {
                    it.addVertex(stack.last().pose(), x,         y + height, width, height, radius.leftBottomRadius).setColor(gradient.leftBottomColor.mul(globalColor).rgb)
                    it.addVertex(stack.last().pose(), x + width, y + height, width, height, radius.rightBottomRadius).setColor(gradient.rightBottomColor.mul(globalColor).rgb)
                    it.addVertex(stack.last().pose(), x + width, y,          width, height, radius.rightTopRadius).setColor(gradient.rightTopColor.mul(globalColor).rgb)
                    it.addVertex(stack.last().pose(), x,         y,          width, height, radius.leftTopRadius).setColor(gradient.leftTopColor.mul(globalColor).rgb)
                    1
                }
                .build()
        )
    }

    fun drawLiquidGlass(
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
                .sampler(CustomSampler("Sampler0", if (clean) mainTarget.colorTextureView else KawasePass.texture(), FilterMode.LINEAR))
                .slice(LiquidUniform.update(power))
                .slice(ScreenUniform.update(mainTarget.screenSize))
                .vertex {
                    it.addVertex(stack.last().pose(), x,         y + height, width, height, radius.leftBottomRadius).setColor(gradient.leftBottomColor.mul(globalColor).rgb)
                    it.addVertex(stack.last().pose(), x + width, y + height, width, height, radius.rightBottomRadius).setColor(gradient.rightBottomColor.mul(globalColor).rgb)
                    it.addVertex(stack.last().pose(), x + width, y,          width, height, radius.rightTopRadius).setColor(gradient.rightTopColor.mul(globalColor).rgb)
                    it.addVertex(stack.last().pose(), x,         y,          width, height, radius.leftTopRadius).setColor(gradient.leftTopColor.mul(globalColor).rgb)
                    1
                }
                .build()
        )
    }

    fun drawText(
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
                .sampler(CustomSampler("Sampler0", msdf.texture.textureView, FilterMode.LINEAR))
                .slice(MsdfUniform.update(msdf.atlas.distanceRange, msdf.thickness))
                .vertex { msdf.applyGlyphs(it, stack.last().pose(), text, size, x, y, color.mul(globalColor)) }
                .build()
        )
    }

    fun drawText(
        font: Font,
        text: String,
        x: Float,
        y: Float,
        color: Color = Color.WHITE
    ) {
        drawText(font, Component.literal(text), x, y, color)
    }

    fun update() {
        val width = window.width.toFloat() / 2f
        val height = window.height.toFloat() / 2f
        val projectionUniform = ProjectionUniform.update(projection.setOrtho(0f, width, height, 0f, -1000f, 1000f), modelView)
        batching.update(projectionUniform)
    }

    fun stack(stack: PoseStack) {
        this.stack = stack
    }

    fun globalColor(globalColor: Color) {
        this.globalColor = globalColor
    }
}