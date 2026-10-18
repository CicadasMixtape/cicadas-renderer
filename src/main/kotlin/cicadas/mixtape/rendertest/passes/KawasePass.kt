package cicadas.mixtape.rendertest.passes

import cicadas.mixtape.rendertest.api.mainTarget
import cicadas.mixtape.rendertest.shader.Pipelines
import cicadas.mixtape.rendertest.shader.uniform.KawaseUniform
import cicadas.mixtape.rendertest.targets.KawaseTarget
import com.mojang.blaze3d.pipeline.RenderPipeline
import com.mojang.blaze3d.pipeline.RenderTarget
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.textures.FilterMode
import com.mojang.blaze3d.textures.GpuSampler
import org.joml.Vector2f
import java.util.OptionalInt

object KawasePass {
    private val targets = List(4) { KawaseTarget() }

    fun target() = targets.first()

    fun update() {
        targets.forEachIndexed { i, target ->
            val width = maxOf(1, mainTarget.width shr (i + 1))
            val height = maxOf(1, mainTarget.height shr (i + 1))

            if (target.width != width || target.height != height) {
                target.resize(width, height)
            }
        }

        val sampler = RenderSystem.getSamplerCache().getClampToEdge(FilterMode.LINEAR)

        for (i in targets.indices) {
            val src = if (i == 0) mainTarget else targets[i - 1]
            val dst = targets[i]
            createPass(Pipelines.KAWASE_DOWN_PIPELINE, src, dst, sampler)
        }

        for (i in targets.lastIndex downTo 1) {
            val src = targets[i]
            val dst = targets[i - 1]
            createPass(Pipelines.KAWASE_UP_PIPELINE, src, dst, sampler)
        }
    }

    private fun createPass(pipeline: RenderPipeline, src: RenderTarget, dst: RenderTarget, sampler: GpuSampler) {
        val kawaseUniform = KawaseUniform.update(5f, Vector2f(0.5f / src.width, 0.5f / src.height))

        RenderSystem.getDevice().createCommandEncoder().createRenderPass(
            { "kawase-pass" },
            dst.colorTextureView!!, OptionalInt.empty()
        ).use {
            it.setPipeline(pipeline)
            it.bindTexture("Sampler0", src.colorTextureView!!, sampler)
            it.setUniform("Kawase", kawaseUniform)
            it.draw(0, 3)
        }
    }
}