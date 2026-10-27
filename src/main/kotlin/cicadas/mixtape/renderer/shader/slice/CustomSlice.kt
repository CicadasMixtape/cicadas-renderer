package cicadas.mixtape.renderer.shader.slice

import com.mojang.blaze3d.buffers.GpuBufferSlice

data class CustomSlice(
    val name: String,
    val slice: GpuBufferSlice
)