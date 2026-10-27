package cicadas.mixtape.rendertest.shader.target

import com.mojang.blaze3d.pipeline.RenderTarget

class CustomTarget(
    name: String,
    depth: Boolean
) : RenderTarget(name, depth)