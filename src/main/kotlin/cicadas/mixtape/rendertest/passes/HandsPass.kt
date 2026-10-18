package cicadas.mixtape.rendertest.passes

import cicadas.mixtape.rendertest.api.clearColorAndDepth
import cicadas.mixtape.rendertest.api.createRenderPass
import cicadas.mixtape.rendertest.api.mainTarget
import cicadas.mixtape.rendertest.api.screenSize
import cicadas.mixtape.rendertest.api.vector4f
import cicadas.mixtape.rendertest.api.withAlpha
import cicadas.mixtape.rendertest.shader.Pipelines
import cicadas.mixtape.rendertest.shader.uniform.HandsUniform
import cicadas.mixtape.rendertest.shader.uniform.ScreenUniform
import cicadas.mixtape.rendertest.targets.HandsTarget
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.textures.FilterMode
import java.awt.Color

object HandsPass {
    val target = HandsTarget()
    var toggled = false

    fun begin() {
        toggled = true

        if (target.width != mainTarget.width || target.height != mainTarget.height) {
            target.resize(mainTarget.width, mainTarget.height)
        }

        target.clearColorAndDepth()
    }

    fun end() {
        val handsUniform = HandsUniform.update(Color.WHITE.withAlpha(150).vector4f, 3f)
        val screenUniform = ScreenUniform.update(mainTarget.screenSize)

        target.blitAndBlendToTexture(mainTarget.colorTextureView!!)

        RenderSystem.getDevice().createCommandEncoder().createRenderPass(mainTarget, Pipelines.OUTLINE_PIPELINE) {
            it.bindTexture("Sampler0", target.colorTextureView, RenderSystem.getSamplerCache().getClampToEdge(FilterMode.NEAREST))
            it.setUniform("Hands", handsUniform)
            it.setUniform("Screen", screenUniform)
            it.draw(0, 3)
        }

        toggled = false
    }
}