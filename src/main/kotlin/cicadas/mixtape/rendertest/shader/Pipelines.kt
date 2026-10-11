package cicadas.mixtape.rendertest.shader

import cicadas.mixtape.rendertest.RenderTest
import com.mojang.blaze3d.pipeline.RenderPipeline
import com.mojang.blaze3d.platform.DepthTestFunction
import com.mojang.blaze3d.shaders.UniformType
import net.minecraft.client.renderer.RenderPipelines

object Pipelines {
    @JvmField
    val QUAD_PIPELINE = RenderPipelines.register(
        RenderPipeline.builder(RenderPipelines.DEBUG_FILLED_SNIPPET)
            .withLocation(RenderTest.id("pipeline/quad"))
            .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
            .withCull(false)
            .build()
    )

    @JvmField
    val LINES_PIPELINE = RenderPipelines.register(
        RenderPipeline.builder(RenderPipelines.LINES_SNIPPET)
            .withLocation(RenderTest.id("pipeline/lines"))
            .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
            .build()
    )

    @JvmField
    val RECT_PIPELINE = RenderPipelines.register(
        RenderPipeline.builder(Snippets.RECT_SNIPPET)
            .withLocation(RenderTest.id("pipeline/rect"))
            .withFragmentShader(RenderTest.id("core/rect"))
            .withVertexShader(RenderTest.id("core/rect"))
            .build()
    )

    @JvmField
    val BLUR_RECT_PIPELINE = RenderPipelines.register(
        RenderPipeline.builder(Snippets.TEXTURE_SNIPPET)
            .withLocation(RenderTest.id("pipeline/blur_rect"))
            .withFragmentShader(RenderTest.id("core/blur_rect"))
            .withVertexShader(RenderTest.id("core/rect"))
            .withUniform("ScreenData", UniformType.UNIFORM_BUFFER)
            .build()
    )

    @JvmField
    val SQUIRCLE_PIPELINE = RenderPipelines.register(
        RenderPipeline.builder(Snippets.SQUIRCLE_SNIPPET)
            .withLocation(RenderTest.id("pipeline/squircle"))
            .withFragmentShader(RenderTest.id("core/squircle"))
            .withVertexShader(RenderTest.id("core/rect"))
            .build()
    )

    @JvmField
    val TEXTURE_PIPELINE = RenderPipelines.register(
        RenderPipeline.builder(Snippets.TEXTURE_SNIPPET)
            .withLocation(RenderTest.id("pipeline/rect"))
            .withFragmentShader(RenderTest.id("core/texture"))
            .withVertexShader(RenderTest.id("core/texture"))
            .build()
    )

    @JvmField
    val MSDF_PIPELINE = RenderPipelines.register(
        RenderPipeline.builder(Snippets.MSDF_SNIPPET)
            .withLocation(RenderTest.id("pipeline/msdf"))
            .withFragmentShader(RenderTest.id("core/msdf"))
            .withVertexShader(RenderTest.id("core/msdf"))
            .build()
    )

    @JvmField
    val KAWASE_UP_PIPELINE = RenderPipelines.register(
        RenderPipeline.builder(Snippets.KAWASE_SNIPPET)
            .withLocation(RenderTest.id("pipeline/kawase_up"))
            .withFragmentShader(RenderTest.id("core/kawase_up"))
            .withVertexShader(RenderTest.id("core/kawase"))
            .build()
    )

    @JvmField
    val KAWASE_DOWN_PIPELINE = RenderPipelines.register(
        RenderPipeline.builder(Snippets.KAWASE_SNIPPET)
            .withLocation(RenderTest.id("pipeline/kawase_down"))
            .withFragmentShader(RenderTest.id("core/kawase_down"))
            .withVertexShader(RenderTest.id("core/kawase"))
            .build()
    )

    @JvmField
    val HAND_PIPELINE = RenderPipelines.register(
        RenderPipeline.builder(RenderPipelines.MATRICES_PROJECTION_SNIPPET, Snippets.HAND_SNIPPET)
            .withLocation(RenderTest.id("pipeline/hand"))
            .withFragmentShader(RenderTest.id("core/hand"))
            .withVertexShader(RenderTest.id("core/hand"))
            .build()
    )
}