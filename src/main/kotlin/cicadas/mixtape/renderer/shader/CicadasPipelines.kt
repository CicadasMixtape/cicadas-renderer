package cicadas.mixtape.renderer.shader

import cicadas.mixtape.renderer.CicadasRenderer
import com.mojang.blaze3d.pipeline.RenderPipeline
import com.mojang.blaze3d.platform.DepthTestFunction
import net.minecraft.client.renderer.RenderPipelines

object CicadasPipelines {
    @JvmField
    val QUAD_PIPELINE = RenderPipelines.register(
        RenderPipeline.builder(RenderPipelines.DEBUG_FILLED_SNIPPET)
            .withLocation(CicadasRenderer.id("pipeline/quad"))
            .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
            .withCull(false)
            .build()
    )

    @JvmField
    val LINES_PIPELINE = RenderPipelines.register(
        RenderPipeline.builder(RenderPipelines.LINES_SNIPPET)
            .withLocation(CicadasRenderer.id("pipeline/lines"))
            .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
            .withCull(false)
            .build()
    )

    @JvmField
    val RECT_PIPELINE = RenderPipelines.register(
        RenderPipeline.builder(CicadasSnippets.RECT_SNIPPET)
            .withLocation(CicadasRenderer.id("pipeline/rect"))
            .withFragmentShader(CicadasRenderer.id("core/rect"))
            .withVertexShader(CicadasRenderer.id("core/rect"))
            .build()
    )

    @JvmField
    val TEXTURE_PIPELINE = RenderPipelines.register(
        RenderPipeline.builder(CicadasSnippets.TEXTURE_SNIPPET)
            .withLocation(CicadasRenderer.id("pipeline/texture"))
            .withFragmentShader(CicadasRenderer.id("core/texture"))
            .withVertexShader(CicadasRenderer.id("core/texture"))
            .build()
    )

    @JvmField
    val SQUIRCLE_PIPELINE = RenderPipelines.register(
        RenderPipeline.builder(CicadasSnippets.SQUIRCLE_SNIPPET)
            .withLocation(CicadasRenderer.id("pipeline/squircle"))
            .withFragmentShader(CicadasRenderer.id("core/squircle"))
            .withVertexShader(CicadasRenderer.id("core/rect"))
            .build()
    )

    @JvmField
    val BLUR_PIPELINE = RenderPipelines.register(
        RenderPipeline.builder(CicadasSnippets.BLUR_SNIPPET)
            .withLocation(CicadasRenderer.id("pipeline/blur"))
            .withFragmentShader(CicadasRenderer.id("core/blur"))
            .withVertexShader(CicadasRenderer.id("core/rect"))
            .build()
    )

    @JvmField
    val LIQUID_GLASS = RenderPipelines.register(
        RenderPipeline.builder(CicadasSnippets.LIQUID_SNIPPET)
            .withLocation(CicadasRenderer.id("pipeline/liquid_glass"))
            .withFragmentShader(CicadasRenderer.id("core/liquid_glass"))
            .withVertexShader(CicadasRenderer.id("core/rect"))
            .build()
    )

    @JvmField
    val MSDF_PIPELINE = RenderPipelines.register(
        RenderPipeline.builder(CicadasSnippets.MSDF_SNIPPET)
            .withLocation(CicadasRenderer.id("pipeline/msdf"))
            .withFragmentShader(CicadasRenderer.id("core/msdf"))
            .withVertexShader(CicadasRenderer.id("core/msdf"))
            .build()
    )

    @JvmField
    val KAWASE_UP_PIPELINE = RenderPipelines.register(
        RenderPipeline.builder(CicadasSnippets.KAWASE_SNIPPET)
            .withLocation(CicadasRenderer.id("pipeline/kawase_up"))
            .withFragmentShader(CicadasRenderer.id("post/kawase_up"))
            .withVertexShader(CicadasRenderer.id("post/kawase"))
            .build()
    )

    @JvmField
    val KAWASE_DOWN_PIPELINE = RenderPipelines.register(
        RenderPipeline.builder(CicadasSnippets.KAWASE_SNIPPET)
            .withLocation(CicadasRenderer.id("pipeline/kawase_down"))
            .withFragmentShader(CicadasRenderer.id("post/kawase_down"))
            .withVertexShader(CicadasRenderer.id("post/kawase"))
            .build()
    )
}