package cicadas.mixtape.renderer.shader.target

import com.mojang.blaze3d.pipeline.RenderTarget
import com.mojang.renderpearl.api.GpuFormat

class CustomTarget(
    name: String,
    depth: Boolean
) : RenderTarget(name, GpuFormat.RGBA8_UNORM, if (depth) GpuFormat.D32_FLOAT else null)