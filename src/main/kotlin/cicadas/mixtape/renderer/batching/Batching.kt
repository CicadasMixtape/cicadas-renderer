package cicadas.mixtape.renderer.batching

import cicadas.mixtape.renderer.api.mainTarget
import cicadas.mixtape.renderer.shader.builder.CustomBuffer
import cicadas.mixtape.renderer.shader.builder.CustomBuilder
import cicadas.mixtape.renderer.shader.builder.CustomFormat
import cicadas.mixtape.renderer.shader.slice.CustomSlice
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.MeshData
import java.util.OptionalDouble
import java.util.OptionalInt

class Batching {
    val builders = hashMapOf<CustomFormat, CustomBuilder>()
    val batches = arrayListOf<Batch>()
    val meshes = arrayListOf<MeshData>()

    fun add(batched: Batched) {
        val last = batches.lastOrNull()
        val format = CustomFormat(batched.pipeline.vertexFormatMode, batched.pipeline.vertexFormat)
        val customBuilder = builders.getOrPut(format) { CustomBuilder(format) }
        var count = 0

        batched.vertices.forEach {
            count += it(customBuilder.get())
        }

        if (last != null && last.match(batched)) {
            last.count += count * 6
        } else {
            batches.add(Batch(batched, customBuilder, customBuilder.count * 6, count * 6))
        }

        customBuilder.count += count
    }

    fun update(projectionUniform: CustomSlice) {
        if (batches.isEmpty()) {
            return
        }

        builders.forEach { (_, it) ->
            val mesh = it.builder?.buildOrThrow()
            it.builder = null
            it.buffer = if (mesh != null) CustomBuffer.create(mesh).also { meshes.add(mesh) } else null
        }

        RenderSystem.getDevice().createCommandEncoder().createRenderPass(
            { "batching-pass" },
            mainTarget.colorTextureView!!, OptionalInt.empty(),
            mainTarget.depthTextureView, OptionalDouble.empty()
        ).use { pass ->
            batches.forEach {
                val buffer = it.builder.buffer ?: return@forEach
                it.batched.update(pass, projectionUniform)
                buffer.setBuffer(pass)
                buffer.drawIndexed(pass, it.start, it.count)
            }
        }

        builders.forEach { (_, it) -> it.count = 0 }
        meshes.forEach { it.close() }
        meshes.clear()
        batches.clear()
    }
}