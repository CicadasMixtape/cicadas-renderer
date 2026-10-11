package cicadas.mixtape.rendertest.shader

import com.mojang.blaze3d.pipeline.BlendFunction
import com.mojang.blaze3d.pipeline.RenderPipeline
import com.mojang.blaze3d.platform.DepthTestFunction
import com.mojang.blaze3d.shaders.UniformType
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.VertexFormat
import net.minecraft.client.renderer.RenderPipelines

object Snippets {
    @JvmField
    val DEFAULT_SNIPPET = RenderPipeline.builder()
        .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
        .withDepthWrite(false)
        .withCull(false)
        .buildSnippet()

    @JvmField
    val RECT_SNIPPET = RenderPipeline.builder(RenderPipelines.MATRICES_PROJECTION_SNIPPET, DEFAULT_SNIPPET)
        .withUniform("RectData", UniformType.UNIFORM_BUFFER)
        .withVertexFormat(Formats.RECT, VertexFormat.Mode.QUADS)
        .withBlend(BlendFunction.TRANSLUCENT)
        .buildSnippet()

    @JvmField
    val TEXTURE_SNIPPET = RenderPipeline.builder(RECT_SNIPPET)
        .withSampler("sampler0")
        .buildSnippet()

    @JvmField
    val SQUIRCLE_SNIPPET = RenderPipeline.builder(RECT_SNIPPET)
        .withUniform("SquircleData", UniformType.UNIFORM_BUFFER)
        .buildSnippet()

    @JvmField
    val MSDF_SNIPPET = RenderPipeline.builder(RenderPipelines.MATRICES_PROJECTION_SNIPPET, DEFAULT_SNIPPET)
        .withSampler("sampler0")
        .withUniform("FontData", UniformType.UNIFORM_BUFFER)
        .withVertexFormat(DefaultVertexFormat.POSITION_TEX_COLOR, VertexFormat.Mode.QUADS)
        .withBlend(BlendFunction.TRANSLUCENT)
        .buildSnippet()

    @JvmField
    val KAWASE_SNIPPET = RenderPipeline.builder(DEFAULT_SNIPPET)
        .withSampler("sampler0")
        .withUniform("BlurData", UniformType.UNIFORM_BUFFER)
        .withVertexFormat(DefaultVertexFormat.EMPTY, VertexFormat.Mode.TRIANGLES)
        .buildSnippet()

    @JvmField
    val HAND_SNIPPET = RenderPipeline.builder(RenderPipelines.MATRICES_PROJECTION_SNIPPET)
        .withSampler("sampler0")
        .withSampler("sampler1")
        .withUniform("ScreenData", UniformType.UNIFORM_BUFFER)
        .withUniform("ColorData", UniformType.UNIFORM_BUFFER)
        .withVertexFormat(DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS)
        .withBlend(BlendFunction.TRANSLUCENT)
        .withCull(false)
        .buildSnippet()
}