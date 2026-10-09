package cicadas.mixtape.rendertest

import com.mojang.blaze3d.pipeline.BlendFunction
import com.mojang.blaze3d.pipeline.RenderPipeline
import com.mojang.blaze3d.platform.DepthTestFunction
import com.mojang.blaze3d.shaders.UniformType
import com.mojang.blaze3d.vertex.VertexFormat
import net.minecraft.client.renderer.RenderPipelines

object Pipelines {
    @JvmField
    val MATRICES_SNIPPET = RenderPipeline.builder()
        .withUniform("DynamicTransforms", UniformType.UNIFORM_BUFFER)
        .withUniform("Projection", UniformType.UNIFORM_BUFFER)
        .buildSnippet()

    @JvmField
    val RECT_PIPELINE = RenderPipelines.register(
        RenderPipeline.builder(MATRICES_SNIPPET)
            .withLocation("pipeline/rect")
            .withVertexShader(RenderTest.id("core/rect"))
            .withFragmentShader(RenderTest.id("core/rect"))
            .withUniform("RectData", UniformType.UNIFORM_BUFFER)
            .withVertexFormat(Formats.RECT, VertexFormat.Mode.QUADS)
            .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
            .withBlend(BlendFunction.TRANSLUCENT)
            .withDepthWrite(false)
            .withCull(false)
            .build()
    )
}