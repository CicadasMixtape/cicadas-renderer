package cicadas.mixtape.renderer.testmod.shader

import cicadas.mixtape.renderer.testmod.Testmod
import com.mojang.blaze3d.pipeline.RenderPipeline
import net.minecraft.client.renderer.RenderPipelines

object TestPipelines {
    @JvmField
    val OUTLINE_PIPELINE = RenderPipelines.register(
        RenderPipeline.builder(TestSnippets.OUTLINE_SNIPPET)
            .withLocation(Testmod.id("pipeline/outline"))
            .withFragmentShader(Testmod.id("post/outline"))
            .withVertexShader(Testmod.id("post/outline"))
            .build()
    )
}