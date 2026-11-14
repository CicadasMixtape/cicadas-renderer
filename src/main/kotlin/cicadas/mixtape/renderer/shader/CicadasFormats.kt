package cicadas.mixtape.renderer.shader

import com.mojang.renderpearl.api.GpuFormat
import com.mojang.renderpearl.api.vertex.VertexFormat

object CicadasFormats {
    @JvmField
    val RECT = VertexFormat.builder(0)
        .addAttribute("Position", GpuFormat.RG32_FLOAT)
        .addAttribute("Size", GpuFormat.RG32_FLOAT)
        .addAttribute("Radius", GpuFormat.R32_FLOAT)
        .addAttribute("Color", GpuFormat.RGBA8_UNORM)
        .build()

    @JvmField
    val SQUIRCLE = VertexFormat.builder(0)
        .addAttribute("Position", GpuFormat.RG32_FLOAT)
        .addAttribute("Size", GpuFormat.RG32_FLOAT)
        .addAttribute("Radius", GpuFormat.R32_FLOAT)
        .addAttribute("Squirt", GpuFormat.R32_FLOAT)
        .addAttribute("Color", GpuFormat.RGBA8_UNORM)
        .build()
}