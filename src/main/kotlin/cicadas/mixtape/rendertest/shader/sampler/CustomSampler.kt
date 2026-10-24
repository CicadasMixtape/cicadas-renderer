package cicadas.mixtape.rendertest.shader.sampler

import com.mojang.blaze3d.textures.FilterMode
import com.mojang.blaze3d.textures.GpuTextureView

data class CustomSampler(
    val name: String,
    val texture: GpuTextureView?,
    val filter: FilterMode
)