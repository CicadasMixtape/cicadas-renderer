package cicadas.mixtape.rendertest.blur

import cicadas.mixtape.rendertest.api.mc
import cicadas.mixtape.rendertest.shader.Pipelines
import cicadas.mixtape.rendertest.shader.uniform.BlurUniform
import com.mojang.blaze3d.pipeline.RenderPipeline
import com.mojang.blaze3d.pipeline.RenderTarget
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.textures.FilterMode
import com.mojang.blaze3d.textures.GpuSampler
import org.joml.Vector2f
import java.util.OptionalInt

object BlurPass {
    private val targets = List(4) { BlurTarget() }

    fun texture() = targets.first().colorTextureView

    fun update() {
        val mainTarget = mc.mainRenderTarget

        targets.forEachIndexed { i, target ->
            val width = maxOf(1, mainTarget.width shr (i + 1))
            val height = maxOf(1, mainTarget.height shr (i + 1))

            if (target.width != width || target.height != height) {
                target.resize(width, height)
            }
        }

        val sampler = RenderSystem.getSamplerCache().getClampToEdge(FilterMode.LINEAR)

        for (i in targets.indices) {
            createPass(Pipelines.KAWASE_DOWN_PIPELINE, if (i == 0) mainTarget else targets[i - 1], targets[i], sampler)
        }

        for (i in targets.lastIndex downTo 1) {
            createPass(Pipelines.KAWASE_UP_PIPELINE, targets[i], targets[i - 1], sampler)
        }
    }

    private fun createPass(pipeline: RenderPipeline, src: RenderTarget, dst: RenderTarget, sampler: GpuSampler) {
        val blurData = BlurUniform.update(
            5f,
            Vector2f(0.5f / src.width, 0.5f / src.height)
        )

        RenderSystem.getDevice().createCommandEncoder().createRenderPass({ "blur" }, dst.colorTextureView!!, OptionalInt.empty()).use {
            it.setPipeline(pipeline)
            it.bindTexture("sampler0", src.colorTextureView!!, sampler)
            it.setUniform("BlurData", blurData)
            it.draw(0, 3)
        }
    }
}