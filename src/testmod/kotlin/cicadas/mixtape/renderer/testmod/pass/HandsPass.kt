package cicadas.mixtape.renderer.testmod.pass

import cicadas.mixtape.renderer.api.clearColorAndDepth
import cicadas.mixtape.renderer.api.createRenderPass
import cicadas.mixtape.renderer.api.mainTarget
import cicadas.mixtape.renderer.api.screenSize
import cicadas.mixtape.renderer.api.setSampler
import cicadas.mixtape.renderer.api.setUniform
import cicadas.mixtape.renderer.api.vector4f
import cicadas.mixtape.renderer.api.withAlpha
import cicadas.mixtape.renderer.shader.Pipelines
import cicadas.mixtape.renderer.shader.sampler.CustomSampler
import cicadas.mixtape.renderer.shader.target.CustomTarget
import cicadas.mixtape.renderer.shader.uniform.OutlineUniform
import cicadas.mixtape.renderer.shader.uniform.ScreenUniform
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.textures.FilterMode
import java.awt.Color

object HandsPass {
    val target = CustomTarget("hands-target", true)
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
            it.setSampler(CustomSampler("Sampler0", target.colorTextureView, FilterMode.NEAREST))
            it.setUniform(outlineUniform)
            it.setUniform(screenUniform)
            it.draw(0, 3)
        }

        toggled = false
    }
}