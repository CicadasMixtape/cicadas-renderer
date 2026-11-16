package cicadas.mixtape.renderer.batching

import cicadas.mixtape.renderer.api.mainTarget
import cicadas.mixtape.renderer.shader.buffer.CustomBuffer
import cicadas.mixtape.renderer.shader.buffer.CustomFormat
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.MeshData
import java.util.Optional
import java.util.OptionalDouble

class Batching {
    val builders = hashMapOf<CustomFormat, BatchBuilder>()
    val batches = arrayListOf<Batch>()
    val meshes = arrayListOf<MeshData>()

    fun add(batched: Batched) {
        val last = batches.lastOrNull()
        val topology = batched.pipeline.primitiveTopology
        val format = batched.pipeline.getVertexFormatBinding(0)!!
        val customFormat = CustomFormat(topology, format)
        val customBuilder = builders.getOrPut(customFormat) { BatchBuilder(customFormat) }
        var count = 0

        batched.vertices.forEach {
            count += customFormat.indexCount(it(customBuilder.builder()))
        }

        if (last != null && last.match(batched, customBuilder)) {
            last.count += count
        } else {
            batches.add(Batch(batched, customBuilder, customBuilder.count, count))
        }

        customBuilder.count += count
    }

    fun update() {
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
            mainTarget.colorTextureView!!, Optional.empty(),
            mainTarget.depthTextureView, OptionalDouble.empty()
        ).use { pass ->
            batches.forEach {
                val buffer = it.builder.buffer ?: return@forEach
                it.batched.update(pass)
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