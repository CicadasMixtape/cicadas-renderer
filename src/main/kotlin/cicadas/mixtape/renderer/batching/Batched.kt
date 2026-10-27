package cicadas.mixtape.renderer.batching

import cicadas.mixtape.renderer.api.setDefaultUniforms
import cicadas.mixtape.renderer.api.setSampler
import cicadas.mixtape.renderer.api.setUniform
import cicadas.mixtape.renderer.shader.sampler.CustomSampler
import cicadas.mixtape.renderer.shader.slice.CustomSlice
import com.mojang.blaze3d.buffers.GpuBufferSlice
import com.mojang.blaze3d.pipeline.RenderPipeline
import com.mojang.blaze3d.systems.RenderPass
import com.mojang.blaze3d.vertex.BufferBuilder
import net.minecraft.client.renderer.RenderPipelines

class Batched private constructor(
    var pipeline: RenderPipeline,
    var vertices: List<(BufferBuilder) -> Int>,
    val samplers: List<CustomSampler>, // Текстуры
    val slices: List<CustomSlice> // Юниформы
) {
    companion object {
        fun builder() = Builder()
    }

    fun update(
        pass: RenderPass,
        transforms: GpuBufferSlice,
        projection: GpuBufferSlice?
    ) {
        pass.setPipeline(pipeline)
        pass.setDefaultUniforms(transforms, projection)

        slices.forEach {
            pass.setUniform(it)
        }

        samplers.forEach {
            pass.setSampler(it)
        }
    }

    class Builder {
        private var pipeline = RenderPipelines.DEBUG_QUADS
        private val vertices = arrayListOf<(BufferBuilder) -> Int>()
        private val samplers = arrayListOf<CustomSampler>()
        private val slices = arrayListOf<CustomSlice>()

        fun pipeline(pipeline: RenderPipeline): Builder {
            this.pipeline = pipeline
            return this
        }

        fun vertex(vertex: (BufferBuilder) -> Int): Builder {
            vertices.add(vertex)
            return this
        }

        fun sampler(sampler: CustomSampler): Builder {
            samplers.add(sampler)
            return this
        }

        fun slice(slice: CustomSlice): Builder {
            slices.add(slice)
            return this
        }

        fun build() = Batched(pipeline, vertices, samplers, slices)
    }
}