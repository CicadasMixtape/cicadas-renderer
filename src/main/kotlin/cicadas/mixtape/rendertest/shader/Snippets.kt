package cicadas.mixtape.rendertest.shader

import com.mojang.blaze3d.pipeline.BlendFunction
import com.mojang.blaze3d.pipeline.RenderPipeline
import com.mojang.blaze3d.platform.DepthTestFunction
import com.mojang.blaze3d.shaders.UniformType
import com.mojang.blaze3d.vertex.VertexFormat

object Snippets {
    @JvmField
    val MATRICES_SNIPPET = RenderPipeline.builder()
        .withUniform("DynamicTransforms", UniformType.UNIFORM_BUFFER)
        .withUniform("Projection", UniformType.UNIFORM_BUFFER)
        .buildSnippet()

    @JvmField
    val RECT_SNIPPET = RenderPipeline.builder()
        .withUniform("RectData", UniformType.UNIFORM_BUFFER)
        .withVertexFormat(Formats.RECT, VertexFormat.Mode.QUADS)
        .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
        .withBlend(BlendFunction.TRANSLUCENT)
        .withDepthWrite(false)
        .withCull(false)
        .buildSnippet()

    @JvmField
    val MSDF_SNIPPET = RenderPipeline.builder()
        .withUniform("FontData", UniformType.UNIFORM_BUFFER)
        .withVertexFormat(Formats.RECT, VertexFormat.Mode.QUADS)
        .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
        .withBlend(BlendFunction.TRANSLUCENT)
        .withDepthWrite(false)
        .withCull(false)
        .buildSnippet()
}