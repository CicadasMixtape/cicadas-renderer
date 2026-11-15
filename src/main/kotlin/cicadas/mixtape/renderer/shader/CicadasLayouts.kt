package cicadas.mixtape.renderer.shader

import com.mojang.renderpearl.api.pipeline.BindGroupLayout
import com.mojang.renderpearl.api.pipeline.UniformType

object CicadasLayouts {
    @JvmField
    val PROJECTION_LAYOUT = BindGroupLayout.builder()
        .withUniform("Projection", UniformType.UNIFORM_BUFFER)
        .build()

    @JvmField
    val SAMPLER_LAYOUT = BindGroupLayout.builder()
        .withUniform("Sampler0", UniformType.COMBINED_IMAGE_SAMPLER)
        .build()

    @JvmField
    val SCREEN_LAYOUT = BindGroupLayout.builder()
        .withUniform("Screen", UniformType.UNIFORM_BUFFER)
        .build()

    @JvmField
    val SHADOW_LAYOUT = BindGroupLayout.builder()
        .withUniform("Shadow", UniformType.UNIFORM_BUFFER)
        .build()

    @JvmField
    val SQUIRCLE_LAYOUT = BindGroupLayout.builder()
        .withUniform("Squircle", UniformType.UNIFORM_BUFFER)
        .build()

    @JvmField
    val LIQUID_LAYOUT = BindGroupLayout.builder()
        .withUniform("Liquid", UniformType.UNIFORM_BUFFER)
        .build()

    @JvmField
    val MSDF_LAYOUT = BindGroupLayout.builder()
        .withUniform("Msdf", UniformType.UNIFORM_BUFFER)
        .build()

    @JvmField
    val KAWASE_LAYOUT = BindGroupLayout.builder()
        .withUniform("Kawase", UniformType.UNIFORM_BUFFER)
        .build()
}