package cicadas.mixtape.renderer.testmod.shader

import com.mojang.blaze3d.pipeline.BlendFunction
import com.mojang.blaze3d.pipeline.RenderPipeline
import com.mojang.blaze3d.platform.DepthTestFunction
import com.mojang.blaze3d.shaders.UniformType
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.VertexFormat

object TestSnippets {
    @JvmField
    val OUTLINE_SNIPPET = RenderPipeline.builder()
        .withSampler("Sampler0")
        .withUniform("Outline", UniformType.UNIFORM_BUFFER)
        .withUniform("Screen", UniformType.UNIFORM_BUFFER)
        .withVertexFormat(DefaultVertexFormat.EMPTY, VertexFormat.Mode.TRIANGLES)
        .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
        .withBlend(BlendFunction.TRANSLUCENT)
        .withCull(false)
        .buildSnippet()
}