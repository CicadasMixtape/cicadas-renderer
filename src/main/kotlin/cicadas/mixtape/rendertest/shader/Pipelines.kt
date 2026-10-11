package cicadas.mixtape.rendertest.shader

import cicadas.mixtape.rendertest.RenderTest
import com.mojang.blaze3d.pipeline.RenderPipeline
import com.mojang.blaze3d.platform.DepthTestFunction
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
    val QUAD_PIPELINE = RenderPipelines.register(
        RenderPipeline.builder(Snippets.MATRICES_SNIPPET, RenderPipelines.DEBUG_FILLED_SNIPPET)
            .withLocation("pipeline/quad")
            .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
            .withCull(false)
            .build()
    )

    @JvmField
    val LINES_PIPELINE = RenderPipelines.register(
        RenderPipeline.builder(Snippets.MATRICES_SNIPPET, RenderPipelines.LINES_SNIPPET)
            .withLocation("pipeline/lines")
            .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
            .withCull(false)
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

    @JvmField
    val KAWASE_UP_PIPELINE = RenderPipelines.register(
        RenderPipeline.builder(Snippets.KAWASE_SNIPPET)
            .withLocation("pipeline/kawase_up")
            .withFragmentShader(RenderTest.id("core/kawase_up"))
            .withVertexShader(RenderTest.id("core/kawase"))
            .withSampler("sampler0")
            .build()
    )

    @JvmField
    val KAWASE_DOWN_PIPELINE = RenderPipelines.register(
        RenderPipeline.builder(Snippets.KAWASE_SNIPPET)
            .withLocation("pipeline/kawase_down")
            .withFragmentShader(RenderTest.id("core/kawase_down"))
            .withVertexShader(RenderTest.id("core/kawase"))
            .withSampler("sampler0")
            .build()
    )

    @JvmField
    val BLUR_RECT_PIPELINE = RenderPipelines.register(
        RenderPipeline.builder(Snippets.MATRICES_SNIPPET, Snippets.RECT_SNIPPET)
            .withLocation("pipeline/blur_rect")
            .withFragmentShader(RenderTest.id("core/blur_rect"))
            .withVertexShader(RenderTest.id("core/rect"))
            .withSampler("sampler0")
            .build()
    )
}