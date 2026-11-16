package cicadas.mixtape.renderer.pass

import cicadas.mixtape.renderer.api.mainTarget
import cicadas.mixtape.renderer.api.setSampler
import cicadas.mixtape.renderer.api.setUniform
import cicadas.mixtape.renderer.shader.CicadasPipelines
import cicadas.mixtape.renderer.shader.data.KawaseData
import cicadas.mixtape.renderer.shader.sampler.CustomSampler
import cicadas.mixtape.renderer.shader.target.CustomTarget
import cicadas.mixtape.renderer.shader.uniform.impl.KawaseUniform
import com.mojang.blaze3d.pipeline.RenderPipeline
import com.mojang.blaze3d.pipeline.RenderTarget
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.textures.FilterMode
import org.joml.Vector2f
import java.util.OptionalInt

object KawasePass {
    private val targets = List(4) { CustomTarget("kawase-target-$it", false) }

    fun texture() = targets.first().colorTextureView

    fun update() {
        targets.forEachIndexed { i, target ->
            val width = maxOf(1, mainTarget.width shr (i + 1))
            val height = maxOf(1, mainTarget.height shr (i + 1))

            if (target.width != width || target.height != height) {
                target.resize(width, height)
            }
        }

        for (i in targets.indices) {
            val src = if (i == 0) mainTarget else targets[i - 1]
            val dst = targets[i]
            createPass(CicadasPipelines.KAWASE_DOWN_PIPELINE, src, dst)
        }

        for (i in targets.lastIndex downTo 1) {
            val src = targets[i]
            val dst = targets[i - 1]
            createPass(CicadasPipelines.KAWASE_UP_PIPELINE, src, dst)
        }
    }

    private fun createPass(
        pipeline: RenderPipeline,
        src: RenderTarget,
        dst: RenderTarget
    ) {
        val kawaseUniform = KawaseUniform.update(KawaseData(5f, Vector2f(0.5f / src.width, 0.5f / src.height)))

        RenderSystem.getDevice().createCommandEncoder().createRenderPass(
            { "kawase-pass" },
            dst.colorTextureView!!, OptionalInt.empty()
        ).use {
            it.setPipeline(pipeline)
            it.setSampler(CustomSampler(0, src.colorTextureView!!, FilterMode.LINEAR))
            it.setUniform(kawaseUniform)
            it.draw(0, 3)
        }
    }
}