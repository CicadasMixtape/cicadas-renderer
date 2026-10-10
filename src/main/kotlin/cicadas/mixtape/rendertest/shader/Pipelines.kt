package cicadas.mixtape.rendertest.shader

import cicadas.mixtape.rendertest.RenderTest
import com.mojang.blaze3d.pipeline.RenderPipeline
import net.minecraft.client.renderer.RenderPipelines

object Pipelines {
    @JvmField
    val RECT_PIPELINE = RenderPipelines.register(
        RenderPipeline.builder(Snippets.MATRICES_SNIPPET, Snippets.RECT_SNIPPET)
            .withLocation("pipeline/rect")
            .withFragmentShader(RenderTest.id("core/rect"))
            .withVertexShader(RenderTest.id("core/rect"))
            .build()
    )

    @JvmField
    val TEXTURE_PIPELINE = RenderPipelines.register(
        RenderPipeline.builder(Snippets.MATRICES_SNIPPET, Snippets.RECT_SNIPPET)
            .withLocation("pipeline/rect")
            .withFragmentShader(RenderTest.id("core/texture"))
            .withVertexShader(RenderTest.id("core/texture"))
            .withSampler("sampler0")
            .build()
    )

    @JvmField
    val MSDF_PIPELINE = RenderPipelines.register(
        RenderPipeline.builder(Snippets.MATRICES_SNIPPET, Snippets.MSDF_SNIPPET)
            .withLocation("pipeline/msdf")
            .withFragmentShader(RenderTest.id("core/msdf"))
            .withVertexShader(RenderTest.id("core/texture"))
            .withSampler("sampler0")
            .build()
    )
}