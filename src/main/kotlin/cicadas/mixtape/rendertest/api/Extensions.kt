package cicadas.mixtape.rendertest.api

import cicadas.mixtape.rendertest.shader.Formats
import cicadas.mixtape.rendertest.mixins.accessors.BufferBuilderAccessor
import com.github.weisj.jsvg.SVGDocument
import com.github.weisj.jsvg.view.FloatSize
import com.github.weisj.jsvg.view.ViewBox
import com.mojang.blaze3d.buffers.GpuBufferSlice
import com.mojang.blaze3d.pipeline.RenderPipeline
import com.mojang.blaze3d.pipeline.RenderTarget
import com.mojang.blaze3d.platform.NativeImage
import com.mojang.blaze3d.systems.CommandEncoder
import com.mojang.blaze3d.systems.RenderPass
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.textures.FilterMode
import com.mojang.blaze3d.textures.GpuTextureView
import com.mojang.blaze3d.vertex.Tesselator
import com.mojang.blaze3d.vertex.VertexConsumer
import com.mojang.blaze3d.vertex.VertexFormat
import com.mojang.blaze3d.vertex.VertexFormatElement
import net.minecraft.client.DeltaTracker
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.Entity
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3
import org.joml.Matrix4f
import org.joml.Vector2f
import org.joml.Vector3f
import org.joml.Vector4f
import org.lwjgl.system.MemoryUtil
import java.awt.Color
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.util.OptionalDouble
import java.util.OptionalInt
import kotlin.time.Duration
import kotlin.time.DurationUnit

fun Any.init() = this

fun Duration.toFloat(unit: DurationUnit) = toInt(unit).toFloat()

val Color.vector4f get() = Vector4f(red / 255f, green / 255f, blue / 255f, alpha / 255f)

fun Color.withAlpha(alpha: Int) = Color(red, green, blue, alpha)

fun Tesselator.beginQuads(format: VertexFormat) = begin(VertexFormat.Mode.QUADS, format)

fun Tesselator.beginLines(format: VertexFormat) = begin(VertexFormat.Mode.LINES, format)

fun VertexConsumer.setFloat(element: VertexFormatElement, a: Float): VertexConsumer {
    val ptr = (this as BufferBuilderAccessor)._beginElement(element)

    if (ptr != -1L) {
        MemoryUtil.memPutFloat(ptr, a)
    }

    return this
}

fun VertexConsumer.setVec2(element: VertexFormatElement, a: Float, b: Float): VertexConsumer {
    val ptr = (this as BufferBuilderAccessor)._beginElement(element)

    if (ptr != -1L) {
        MemoryUtil.memPutFloat(ptr, a)
        MemoryUtil.memPutFloat(ptr + 4, b)
    }

    return this
}

fun VertexConsumer.addVertex(x: Float, y: Float, width: Float, height: Float, radius: Float) = addVertex(x, y, 0f)
    .setVec2(Formats.SIZE, width, height)
    .setFloat(Formats.RADIUS, radius)

fun VertexConsumer.addLine(matrix4f: Matrix4f, x1: Float, y1: Float, z1: Float, x2: Float, y2: Float, z2: Float, color: Int, lineWidth: Float): VertexConsumer {
    val normal = Vector3f(x2 - x1, y2 - y1, z2 - z1).normalize()
    addVertex(matrix4f, x1, y1, z1).setNormal(normal.x, normal.y, normal.z).setColor(color).setLineWidth(lineWidth)
    addVertex(matrix4f, x2, y2, z2).setNormal(normal.x, normal.y, normal.z).setColor(color).setLineWidth(lineWidth)
    return this
}

fun CommandEncoder.createRenderPass(target: RenderTarget, pipeline: RenderPipeline, unit: (RenderPass) -> Unit) {
    val transforms = RenderSystem.getDynamicUniforms().writeTransform(RenderSystem.getModelViewMatrix(), Vector4f(1f), Vector3f(), Matrix4f())
    val projection = RenderSystem.getProjectionMatrixBuffer()

    createRenderPass(
        { "rendertest-pass" },
        target.colorTextureView!!, OptionalInt.empty(),
        target.depthTextureView, OptionalDouble.empty()
    ).use {
        it.setPipeline(pipeline)
        it.setDefaultUniforms(transforms, projection)
        unit(it)
    }
}

val RenderTarget.screenSize get() = Vector2f(width.toFloat(), height.toFloat())

fun RenderTarget.clearColorAndDepth() = RenderSystem.getDevice().createCommandEncoder().clearColorAndDepthTextures(colorTexture!!, 0, depthTexture!!, 1.0)

fun RenderPass.setNearest(name: String, textureView: GpuTextureView?) = bindTexture(name, textureView, RenderSystem.getSamplerCache().getClampToEdge(FilterMode.NEAREST))

fun RenderPass.setLinear(name: String, textureView: GpuTextureView?) = bindTexture(name, textureView, RenderSystem.getSamplerCache().getClampToEdge(FilterMode.LINEAR))

fun RenderPass.setDefaultUniforms(transforms: GpuBufferSlice, projection: GpuBufferSlice?) {
    if (projection != null) {
        setUniform("Projection", projection)
    }

    setUniform("DynamicTransforms", transforms)
}

val DeltaTracker.partialTicks get() = getGameTimeDeltaPartialTick(false)

val AABB.withCamera: AABB get() {
    val camera = mc.gameRenderer.mainCamera.position()
    val min = Vec3(minX, minY, minZ).subtract(camera)
    val max = Vec3(maxX, maxY, maxZ).subtract(camera)
    return AABB(min, max)
}

val Entity.lerpedAABB: AABB get() {
    val pos = oldPosition().lerp(position(), mc.deltaTracker.partialTicks.toDouble())
    val width = bbWidth / 2.0
    val height = bbHeight.toDouble()
    val min = pos.add(-width, 0.0, -width)
    val max = pos.add(width, height, width)
    return AABB(min, max)
}

val BlockPos.blockState get() = level.getBlockState(this)

val BlockPos.aabb get() = AABB(this)

fun SVGDocument.toBuffered(size: FloatSize): BufferedImage {
    val size = FloatSize(size.width * 2f, size.height * 2f)
    val image = BufferedImage(size.width.toInt(), size.height.toInt(), BufferedImage.TYPE_INT_ARGB)
    val graphics = image.createGraphics()
    graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
    graphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE)
    render(null, graphics, ViewBox(0f, 0f, size.width, size.height))
    graphics.dispose()
    return image
}

fun BufferedImage.toNative(): NativeImage {
    val nativeImage = NativeImage(width, height, false)

    for (x in 0 until width) {
        for (y in 0 until height) {
            val color = getRGB(x, y)
            nativeImage.setPixel(x, y, color)
        }
    }

    return nativeImage
}