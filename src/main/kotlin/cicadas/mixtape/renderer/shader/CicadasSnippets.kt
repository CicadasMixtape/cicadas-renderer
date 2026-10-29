package cicadas.mixtape.renderer.shader

import com.mojang.blaze3d.pipeline.BlendFunction
import com.mojang.blaze3d.pipeline.RenderPipeline
import com.mojang.blaze3d.platform.DepthTestFunction
import com.mojang.blaze3d.shaders.UniformType
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.VertexFormat

object CicadasSnippets {
    @JvmField
    val DEPTH_TEST_SNIPPET = RenderPipeline.builder()
        .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
        .withDepthWrite(false)
        .withCull(false)
        .buildSnippet()

    @JvmField
    val PROJECTION_SNIPPET = RenderPipeline.builder()
        .withUniform("Projection", UniformType.UNIFORM_BUFFER)
        .buildSnippet()

    @JvmField
    val POSITION_COLOR_SNIPPET = RenderPipeline.builder(DEPTH_TEST_SNIPPET, PROJECTION_SNIPPET)
        .withVertexFormat(DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS)
        .withBlend(BlendFunction.TRANSLUCENT)
        .buildSnippet()

    @JvmField
    val POSITION_TEXTURE_COLOR_SNIPPET = RenderPipeline.builder(DEPTH_TEST_SNIPPET, PROJECTION_SNIPPET)
        .withVertexFormat(DefaultVertexFormat.POSITION_TEX_COLOR, VertexFormat.Mode.QUADS)
        .withBlend(BlendFunction.TRANSLUCENT)
        .withSampler("Sampler0")
        .buildSnippet()

    @JvmField
    val LINES_SNIPPET = RenderPipeline.builder(DEPTH_TEST_SNIPPET, PROJECTION_SNIPPET)
        .withVertexFormat(DefaultVertexFormat.POSITION_COLOR_NORMAL_LINE_WIDTH, VertexFormat.Mode.QUADS)
        .withBlend(BlendFunction.TRANSLUCENT)
        .withUniform("Screen", UniformType.UNIFORM_BUFFER)
        .buildSnippet()

    @JvmField
    val RECT_SNIPPET = RenderPipeline.builder(DEPTH_TEST_SNIPPET, PROJECTION_SNIPPET)
        .withVertexFormat(CicadasFormats.RECT, VertexFormat.Mode.QUADS)
        .withBlend(BlendFunction.TRANSLUCENT)
        .buildSnippet()

    @JvmField
    val TEXTURE_SNIPPET = RenderPipeline.builder(DEPTH_TEST_SNIPPET, PROJECTION_SNIPPET)
        .withVertexFormat(CicadasFormats.RECT, VertexFormat.Mode.QUADS)
        .withBlend(BlendFunction.TRANSLUCENT)
        .withSampler("Sampler0")
        .buildSnippet()

    @JvmField
    val SQUIRCLE_SNIPPET = RenderPipeline.builder(DEPTH_TEST_SNIPPET, PROJECTION_SNIPPET)
        .withVertexFormat(CicadasFormats.SQUIRCLE, VertexFormat.Mode.QUADS)
        .withBlend(BlendFunction.TRANSLUCENT)
        .withUniform("Squircle", UniformType.UNIFORM_BUFFER)
        .buildSnippet()

    @JvmField
    val BLUR_SNIPPET = RenderPipeline.builder(DEPTH_TEST_SNIPPET, PROJECTION_SNIPPET)
        .withVertexFormat(CicadasFormats.RECT, VertexFormat.Mode.QUADS)
        .withBlend(BlendFunction.TRANSLUCENT)
        .withSampler("Sampler0")
        .withUniform("Screen", UniformType.UNIFORM_BUFFER)
        .buildSnippet()

    @JvmField
    val LIQUID_SNIPPET = RenderPipeline.builder(DEPTH_TEST_SNIPPET, PROJECTION_SNIPPET)
        .withVertexFormat(CicadasFormats.RECT, VertexFormat.Mode.QUADS)
        .withBlend(BlendFunction.TRANSLUCENT)
        .withSampler("Sampler0")
        .withUniform("Liquid", UniformType.UNIFORM_BUFFER)
        .withUniform("Screen", UniformType.UNIFORM_BUFFER)
        .buildSnippet()

    @JvmField
    val MSDF_SNIPPET = RenderPipeline.builder(DEPTH_TEST_SNIPPET, PROJECTION_SNIPPET)
        .withVertexFormat(DefaultVertexFormat.POSITION_TEX_COLOR, VertexFormat.Mode.QUADS)
        .withBlend(BlendFunction.TRANSLUCENT)
        .withSampler("Sampler0")
        .withUniform("Msdf", UniformType.UNIFORM_BUFFER)
        .buildSnippet()

    @JvmField
    val KAWASE_SNIPPET = RenderPipeline.builder(DEPTH_TEST_SNIPPET)
        .withVertexFormat(DefaultVertexFormat.EMPTY, VertexFormat.Mode.TRIANGLES)
        .withSampler("Sampler0")
        .withUniform("Kawase", UniformType.UNIFORM_BUFFER)
        .buildSnippet()
}