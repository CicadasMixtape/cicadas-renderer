package cicadas.mixtape.renderer.batching

import cicadas.mixtape.renderer.api.setUniform
import cicadas.mixtape.renderer.shader.sampler.CustomSampler
import cicadas.mixtape.renderer.shader.slice.CustomSlice
import cicadas.mixtape.renderer.utility.scissor.ScissorState
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.BufferBuilder
import com.mojang.renderpearl.api.commands.RenderPass
import com.mojang.renderpearl.api.pipeline.RenderPipeline
import net.minecraft.client.renderer.RenderPipelines
import org.joml.Matrix4f
import org.joml.Vector3f
import org.joml.Vector4f

class Batched private constructor(
    val matrix4f: Matrix4f,
    val pipeline: RenderPipeline,
    val scissorState: ScissorState,
    val vertices: List<(BufferBuilder) -> Int>, // Вертексы
    val samplers: List<CustomSampler>, // Текстуры
    val slices: List<CustomSlice> // Юниформы
) {
    companion object {
        @JvmStatic
        fun builder() = Builder()
    }

    val state = BatchState(pipeline, scissorState, samplers.toSet(), slices.toSet())

    fun update(pass: RenderPass) {
        pass.setPipeline(RenderSystem.getCompiledPipeline(pipeline))

        if (scissorState.toggled()) {
            val position = matrix4f.transformPosition(scissorState.x(), scissorState.y(), 0f, Vector3f())
            val size = matrix4f.transform(scissorState.width(), scissorState.height(), 0f, 0f, Vector4f())
            pass.enableScissor(position.x.toInt(), position.y.toInt(), size.x.toInt(), size.y.toInt())
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
        private var matrix4f = Matrix4f()
        private var pipeline = RenderPipelines.ANIMATE_SPRITE_BLIT
        private var scissorState = ScissorState()
        private val vertices = arrayListOf<(BufferBuilder) -> Int>()
        private val samplers = arrayListOf<CustomSampler>()
        private val slices = arrayListOf<CustomSlice>()

        fun matrix4f(matrix4f: Matrix4f): Builder {
            this.matrix4f = matrix4f
            return this
        }

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

        fun build() = Batched(matrix4f, pipeline, scissorState, vertices, samplers, slices)
    }
}