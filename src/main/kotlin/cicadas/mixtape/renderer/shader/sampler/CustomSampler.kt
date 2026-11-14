package cicadas.mixtape.renderer.shader.sampler

import com.mojang.renderpearl.api.textures.FilterMode
import com.mojang.renderpearl.api.textures.GpuTextureView

data class CustomSampler(
    val name: String,
    val texture: GpuTextureView?,
    val filter: FilterMode
) {
    constructor(index: Int, texture: GpuTextureView?, filter: FilterMode) : this("Sampler$index", texture, filter)
}