package cicadas.mixtape.renderer.shader.slice

import com.mojang.renderpearl.api.buffers.GpuBufferSlice

data class CustomSlice(
    val name: String,
    val slice: GpuBufferSlice
)