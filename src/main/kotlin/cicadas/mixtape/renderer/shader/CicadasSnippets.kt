package cicadas.mixtape.renderer.shader

import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.renderpearl.api.pipeline.BlendFunction
import com.mojang.renderpearl.api.pipeline.ColorTargetState
import com.mojang.renderpearl.api.pipeline.PrimitiveTopology
import com.mojang.renderpearl.api.pipeline.RenderPipeline
import java.util.Optional

object CicadasSnippets {
    @JvmField
    val WITHOUT_DEPTH_SNIPPET = RenderPipeline.builder()
        .withDepthStencilState(Optional.empty())
        .withCull(false)
        .buildSnippet()

    @JvmField
    val PROJECTION_SNIPPET = RenderPipeline.builder()
        .withBindGroupLayout(CicadasLayouts.PROJECTION_LAYOUT)
        .buildSnippet()

    @JvmField
    val POSITION_COLOR_SNIPPET = RenderPipeline.builder(WITHOUT_DEPTH_SNIPPET, PROJECTION_SNIPPET)
        .withVertexBinding(0, DefaultVertexFormat.POSITION_COLOR)
        .withPrimitiveTopology(PrimitiveTopology.QUADS)
        .withColorTargetState(ColorTargetState(BlendFunction.TRANSLUCENT))
        .buildSnippet()

    @JvmField
    val POSITION_TEXTURE_COLOR_SNIPPET = RenderPipeline.builder(WITHOUT_DEPTH_SNIPPET, PROJECTION_SNIPPET)
        .withVertexBinding(0, DefaultVertexFormat.POSITION_TEX_COLOR)
        .withPrimitiveTopology(PrimitiveTopology.QUADS)
        .withColorTargetState(ColorTargetState(BlendFunction.TRANSLUCENT))
        .withBindGroupLayout(CicadasLayouts.SAMPLER_LAYOUT)
        .buildSnippet()

    @JvmField
    val LINES_SNIPPET = RenderPipeline.builder(WITHOUT_DEPTH_SNIPPET, PROJECTION_SNIPPET)
        .withVertexBinding(0, DefaultVertexFormat.POSITION_COLOR_NORMAL_LINE_WIDTH)
        .withPrimitiveTopology(PrimitiveTopology.QUADS)
        .withColorTargetState(ColorTargetState(BlendFunction.TRANSLUCENT))
        .withBindGroupLayout(CicadasLayouts.SCREEN_LAYOUT)
        .buildSnippet()

    @JvmField
    val RECT_SNIPPET = RenderPipeline.builder(WITHOUT_DEPTH_SNIPPET, PROJECTION_SNIPPET)
        .withVertexBinding(0, CicadasFormats.RECT)
        .withPrimitiveTopology(PrimitiveTopology.QUADS)
        .withColorTargetState(ColorTargetState(BlendFunction.TRANSLUCENT))
        .buildSnippet()

    @JvmField
    val SHADOW_SNIPPET = RenderPipeline.builder(WITHOUT_DEPTH_SNIPPET, PROJECTION_SNIPPET)
        .withVertexBinding(0, CicadasFormats.RECT)
        .withPrimitiveTopology(PrimitiveTopology.QUADS)
        .withColorTargetState(ColorTargetState(BlendFunction.TRANSLUCENT))
        .withBindGroupLayout(CicadasLayouts.SHADOW_LAYOUT)
        .buildSnippet()

    @JvmField
    val TEXTURE_SNIPPET = RenderPipeline.builder(WITHOUT_DEPTH_SNIPPET, PROJECTION_SNIPPET)
        .withVertexBinding(0, CicadasFormats.RECT)
        .withPrimitiveTopology(PrimitiveTopology.QUADS)
        .withColorTargetState(ColorTargetState(BlendFunction.TRANSLUCENT))
        .withBindGroupLayout(CicadasLayouts.SAMPLER_LAYOUT)
        .buildSnippet()

    @JvmField
    val SQUIRCLE_SNIPPET = RenderPipeline.builder(WITHOUT_DEPTH_SNIPPET, PROJECTION_SNIPPET)
        .withVertexBinding(0, CicadasFormats.SQUIRCLE)
        .withPrimitiveTopology(PrimitiveTopology.QUADS)
        .withColorTargetState(ColorTargetState(BlendFunction.TRANSLUCENT))
        .withBindGroupLayout(CicadasLayouts.SQUIRCLE_LAYOUT)
        .buildSnippet()

    @JvmField
    val BLUR_SNIPPET = RenderPipeline.builder(WITHOUT_DEPTH_SNIPPET, PROJECTION_SNIPPET)
        .withVertexBinding(0, CicadasFormats.RECT)
        .withPrimitiveTopology(PrimitiveTopology.QUADS)
        .withColorTargetState(ColorTargetState(BlendFunction.TRANSLUCENT))
        .withBindGroupLayout(CicadasLayouts.SAMPLER_LAYOUT)
        .withBindGroupLayout(CicadasLayouts.SCREEN_LAYOUT)
        .buildSnippet()

    @JvmField
    val LIQUID_SNIPPET = RenderPipeline.builder(WITHOUT_DEPTH_SNIPPET, PROJECTION_SNIPPET)
        .withVertexBinding(0, CicadasFormats.RECT)
        .withPrimitiveTopology(PrimitiveTopology.QUADS)
        .withColorTargetState(ColorTargetState(BlendFunction.TRANSLUCENT))
        .withBindGroupLayout(CicadasLayouts.SAMPLER_LAYOUT)
        .withBindGroupLayout(CicadasLayouts.LIQUID_LAYOUT)
        .withBindGroupLayout(CicadasLayouts.SCREEN_LAYOUT)
        .buildSnippet()

    @JvmField
    val MSDF_SNIPPET = RenderPipeline.builder(WITHOUT_DEPTH_SNIPPET, PROJECTION_SNIPPET)
        .withVertexBinding(0, DefaultVertexFormat.POSITION_TEX_COLOR)
        .withPrimitiveTopology(PrimitiveTopology.QUADS)
        .withColorTargetState(ColorTargetState(BlendFunction.TRANSLUCENT))
        .withBindGroupLayout(CicadasLayouts.SAMPLER_LAYOUT)
        .withBindGroupLayout(CicadasLayouts.MSDF_LAYOUT)
        .buildSnippet()

    @JvmField
    val KAWASE_SNIPPET = RenderPipeline.builder(WITHOUT_DEPTH_SNIPPET)
        .withPrimitiveTopology(PrimitiveTopology.TRIANGLES)
        .withColorTargetState(ColorTargetState.DEFAULT)
        .withBindGroupLayout(CicadasLayouts.SAMPLER_LAYOUT)
        .withBindGroupLayout(CicadasLayouts.KAWASE_LAYOUT)
        .buildSnippet()
}