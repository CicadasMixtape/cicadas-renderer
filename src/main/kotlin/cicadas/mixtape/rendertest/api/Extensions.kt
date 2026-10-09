package cicadas.mixtape.rendertest.api

import cicadas.mixtape.rendertest.Formats
import cicadas.mixtape.rendertest.mixins.accessors.BufferBuilderAccessor
import com.mojang.blaze3d.pipeline.RenderPipeline
import com.mojang.blaze3d.systems.CommandEncoder
import com.mojang.blaze3d.systems.RenderPass
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.BufferBuilder
import com.mojang.blaze3d.vertex.VertexConsumer
import com.mojang.blaze3d.vertex.VertexFormatElement
import net.minecraft.client.renderer.DynamicUniforms
import org.joml.Matrix4f
import org.joml.Vector3f
import org.joml.Vector4f
import org.lwjgl.system.MemoryUtil
import java.util.OptionalDouble
import java.util.OptionalInt

fun Any.init() = this

fun VertexConsumer.setVec2(element: VertexFormatElement, a: Float, b: Float): VertexConsumer {
    val ptr = (this as BufferBuilderAccessor)._beginElement(element)

    if (ptr != -1L) {
        MemoryUtil.memPutFloat(ptr, a)
        MemoryUtil.memPutFloat(ptr + 4, b)
    }

    return this
}

fun BufferBuilder.addVertex(px: Float, py: Float, cx: Float, cy: Float, width: Float, height: Float, color: Int) {
    addVertex(px, py, 0f)
        .setColor(color)
        .setVec2(Formats.COORD, cx, cy)
        .setVec2(Formats.SIZE, width, height)
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