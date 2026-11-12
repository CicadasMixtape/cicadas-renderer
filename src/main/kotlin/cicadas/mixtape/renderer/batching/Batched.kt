package cicadas.mixtape.renderer.batching

import cicadas.mixtape.renderer.api.setSampler
import cicadas.mixtape.renderer.api.setUniform
import cicadas.mixtape.renderer.shader.sampler.CustomSampler
import cicadas.mixtape.renderer.shader.slice.CustomSlice
import cicadas.mixtape.renderer.utility.scissor.ScissorState
import com.mojang.blaze3d.pipeline.RenderPipeline
import com.mojang.blaze3d.systems.RenderPass
import com.mojang.blaze3d.vertex.BufferBuilder
import net.minecraft.client.renderer.RenderPipelines

class Batched private constructor(
    var pipeline: RenderPipeline,
    var scissorState: ScissorState,
    var vertices: List<(BufferBuilder) -> Int>,
    val samplers: List<CustomSampler>, // Текстуры
    val slices: List<CustomSlice> // Юниформы
) {
    companion object {
        @JvmStatic
        fun builder() = Builder()
    }

    val state = BatchState(pipeline, scissorState, samplers.toSet(), slices.toSet())

    fun update(pass: RenderPass) {
        pass.setPipeline(pipeline)

        if (scissorState.toggled()) {
            pass.enableScissor(scissorState.x(), scissorState.y(), scissorState.width(), scissorState.height())
        } else {
            pass.disableScissor()
        }

        slices.forEach {
            pass.setUniform(it)
        }

        samplers.forEach {
            pass.setSampler(it)
        }
    }

    class Builder {
        private var pipeline = RenderPipelines.DEBUG_QUADS
        private var scissorState = ScissorState()
        private val vertices = arrayListOf<(BufferBuilder) -> Int>()
        private val samplers = arrayListOf<CustomSampler>()
        private val slices = arrayListOf<CustomSlice>()

        fun pipeline(pipeline: RenderPipeline): Builder {
            this.pipeline = pipeline
            return this
        }

        fun scissorState(scissorState: ScissorState): Builder {
            this.scissorState = scissorState
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

        fun build() = Batched(pipeline, scissorState, vertices, samplers, slices)
    }
}