package cicadas.mixtape.rendertest.api

import cicadas.mixtape.rendertest.shader.Formats
import cicadas.mixtape.rendertest.mixins.accessors.BufferBuilderAccessor
import com.mojang.blaze3d.pipeline.RenderPipeline
import com.mojang.blaze3d.systems.CommandEncoder
import com.mojang.blaze3d.systems.RenderPass
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.VertexConsumer
import com.mojang.blaze3d.vertex.VertexFormatElement
import net.minecraft.client.renderer.DynamicUniforms
import net.minecraft.world.entity.Entity
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3
import org.joml.Matrix4f
import org.joml.Vector3f
import org.joml.Vector4f
import org.lwjgl.system.MemoryUtil
import java.awt.Color
import java.util.OptionalDouble
import java.util.OptionalInt
import kotlin.math.sqrt

fun Any.init() = this

fun VertexConsumer.setVec2(element: VertexFormatElement, a: Float, b: Float): VertexConsumer {
    val ptr = (this as BufferBuilderAccessor)._beginElement(element)

    if (ptr != -1L) {
        MemoryUtil.memPutFloat(ptr, a)
        MemoryUtil.memPutFloat(ptr + 4, b)
    }

    return this
}

fun VertexConsumer.addVertex(px: Float, py: Float, cx: Float, cy: Float, width: Float, height: Float, color: Int) = addVertex(px, py, 0f).setColor(color).setVec2(Formats.COORD, cx, cy).setVec2(Formats.SIZE, width, height)

fun VertexConsumer.addLine(matrix4f: Matrix4f, x1: Double, y1: Double, z1: Double, x2: Double, y2: Double, z2: Double, width: Float, color: Int) {
    var nx = (x2 - x1).toFloat()
    var ny = (y2 - y1).toFloat()
    var nz = (z2 - z1).toFloat()
    val len = sqrt(nx * nx + ny * ny + nz * nz)

    if (len == 0f) {
        return
    }

    nx /= len
    ny /= len
    nz /= len

    addVertex(matrix4f, x1.toFloat(), y1.toFloat(), z1.toFloat()).setNormal(nx, ny, nz).setLineWidth(width).setColor(color)
    addVertex(matrix4f, x2.toFloat(), y2.toFloat(), z2.toFloat()).setNormal(nx, ny, nz).setLineWidth(width).setColor(color)
}

fun DynamicUniforms.defaultUniforms() = writeTransform(RenderSystem.getModelViewMatrix(), Vector4f(1f), Vector3f(), Matrix4f())

fun CommandEncoder.defaultRenderPass(pipeline: RenderPipeline, unit: (RenderPass) -> Unit) {
    val target = mc.mainRenderTarget
    val transforms = RenderSystem.getDynamicUniforms().defaultUniforms()

    createRenderPass(
        { "rendertest-pass" },
        target.colorTextureView!!, OptionalInt.empty(),
        target.depthTextureView, OptionalDouble.empty()
    ).use {
        it.setPipeline(pipeline)
        it.setUniform("DynamicTransforms", transforms)
        RenderSystem.bindDefaultUniforms(it)
        unit(it)
    }
}

val AABB.withCamera: AABB get() {
    val camera = mc.gameRenderer.mainCamera.position()
    val min = Vec3(minX - camera.x, minY - camera.y, minZ - camera.z)
    val max = Vec3(maxX - camera.x, maxY - camera.y, maxZ - camera.z)
    return AABB(min, max)
}

val Entity.lerpedAABB: AABB get() {
    val pos = oldPosition().lerp(position(), mc.deltaTracker.getGameTimeDeltaPartialTick(false).toDouble())
    val width = bbWidth.toDouble() / 2.0
    val height = bbHeight.toDouble()
    val min = pos.add(-width, 0.0, -width)
    val max = pos.add(width, height, width)
    return AABB(min, max)
}

fun Color.withAlpha(alpha: Int) = Color(red, green, blue, alpha)