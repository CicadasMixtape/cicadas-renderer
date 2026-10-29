package cicadas.mixtape.renderer.api

import cicadas.mixtape.renderer.shader.CicadasFormats
import cicadas.mixtape.renderer.mixins.accessors.BufferBuilderAccessor
import cicadas.mixtape.renderer.shader.sampler.CustomSampler
import cicadas.mixtape.renderer.shader.slice.CustomSlice
import com.github.weisj.jsvg.SVGDocument
import com.github.weisj.jsvg.view.FloatSize
import com.github.weisj.jsvg.view.ViewBox
import com.mojang.blaze3d.buffers.GpuBuffer
import com.mojang.blaze3d.pipeline.RenderTarget
import com.mojang.blaze3d.platform.NativeImage
import com.mojang.blaze3d.systems.GpuDevice
import com.mojang.blaze3d.systems.RenderPass
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.VertexConsumer
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3
import org.joml.Matrix4f
import org.joml.Vector2f
import org.joml.Vector3f
import org.lwjgl.system.MemoryUtil
import java.awt.Color
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import kotlin.time.Duration
import kotlin.time.DurationUnit

internal fun Any.init() = this

internal fun Duration.toFloat(unit: DurationUnit) = toInt(unit).toFloat()

internal fun Color.mul(other: Color): Color {
    val red = red * other.red / 255
    val green = green * other.green / 255
    val blue = blue * other.blue / 255
    val alpha = alpha * other.alpha / 255
    return Color(red, green, blue, alpha)
}

fun VertexConsumer.setSize(width: Float, height: Float): VertexConsumer {
    val ptr = (this as BufferBuilderAccessor)._beginElement(CicadasFormats.SIZE)

    if (ptr != -1L) {
        MemoryUtil.memPutFloat(ptr, width)
        MemoryUtil.memPutFloat(ptr + 4, height)
    }

    return this
}

fun VertexConsumer.setRadius(radius: Float): VertexConsumer {
    val ptr = (this as BufferBuilderAccessor)._beginElement(CicadasFormats.RADIUS)

    if (ptr != -1L) {
        MemoryUtil.memPutFloat(ptr, radius)
    }

    return this
}

fun VertexConsumer.setSquirt(squirt: Float): VertexConsumer {
    val ptr = (this as BufferBuilderAccessor)._beginElement(CicadasFormats.SQUIRT)

    if (ptr != -1L) {
        MemoryUtil.memPutFloat(ptr, squirt)
    }

    return this
}

fun VertexConsumer.addVertex(
    matrix4f: Matrix4f,
    x: Float,
    y: Float,
    width: Float,
    height: Float,
    radius: Float
) = addVertex(matrix4f, x, y, 0f).setSize(width, height).setRadius(radius)

fun VertexConsumer.addVertex(
    matrix4f: Matrix4f,
    x: Float,
    y: Float,
    width: Float,
    height: Float,
    radius: Float,
    squirt: Float
) = addVertex(matrix4f, x, y, 0f).setSize(width, height).setRadius(radius).setSquirt(squirt)

fun VertexConsumer.addLine(
    matrix4f: Matrix4f,
    x1: Float,
    y1: Float,
    z1: Float,
    x2: Float,
    y2: Float,
    z2: Float,
    color: Int,
    lineWidth: Float
): VertexConsumer {
    val normal = Vector3f(x2 - x1, y2 - y1, z2 - z1).normalize()
    addVertex(matrix4f, x1, y1, z1).setNormal(normal.x, normal.y, normal.z).setColor(color).setLineWidth(lineWidth)
    addVertex(matrix4f, x2, y2, z2).setNormal(normal.x, normal.y, normal.z).setColor(color).setLineWidth(lineWidth)
    return this
}

val RenderTarget.screenSize get() = Vector2f(width.toFloat(), height.toFloat())

fun RenderTarget.clearColorAndDepth() = RenderSystem.getDevice().createCommandEncoder().clearColorAndDepthTextures(colorTexture!!, 0, depthTexture!!, 1.0)

fun GpuDevice.createBuffer(name: String, size: Int) = createBuffer({ "${name}Buffer" }, GpuBuffer.USAGE_UNIFORM or GpuBuffer.USAGE_COPY_DST, size.toLong())

fun RenderPass.setSampler(sampler: CustomSampler) = bindTexture(sampler.name, sampler.texture, RenderSystem.getSamplerCache().getClampToEdge(sampler.filter))

fun RenderPass.setUniform(slice: CustomSlice) = setUniform(slice.name, slice.slice)

val AABB.withCamera: AABB get() {
    val camera = mc.gameRenderer.mainCamera.position()
    val min = Vec3(minX, minY, minZ).subtract(camera)
    val max = Vec3(maxX, maxY, maxZ).subtract(camera)
    return AABB(min, max)
}

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