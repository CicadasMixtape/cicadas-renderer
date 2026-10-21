package cicadas.mixtape.rendertest.pass

import cicadas.mixtape.rendertest.api.clearColorAndDepth
import cicadas.mixtape.rendertest.api.createRenderPass
import cicadas.mixtape.rendertest.api.mainTarget
import cicadas.mixtape.rendertest.api.screenSize
import cicadas.mixtape.rendertest.api.setNearest
import cicadas.mixtape.rendertest.api.setUniform
import cicadas.mixtape.rendertest.api.vector4f
import cicadas.mixtape.rendertest.api.withAlpha
import cicadas.mixtape.rendertest.shader.Pipelines
import cicadas.mixtape.rendertest.shader.uniform.OutlineUniform
import cicadas.mixtape.rendertest.shader.uniform.ScreenUniform
import cicadas.mixtape.rendertest.target.HandsTarget
import com.mojang.blaze3d.systems.RenderSystem
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
        val outlineUniform = OutlineUniform.update(Color.WHITE.withAlpha(150).vector4f, 3f)
        val screenUniform = ScreenUniform.update(mainTarget.screenSize)

        target.blitAndBlendToTexture(mainTarget.colorTextureView!!)

        RenderSystem.getDevice().createCommandEncoder().createRenderPass(mainTarget, Pipelines.OUTLINE_PIPELINE) {
            it.setNearest("Sampler0", target.colorTextureView)
            it.setUniform(outlineUniform)
            it.setUniform(screenUniform)
            it.draw(0, 3)
        }

        toggled = false
    }
}