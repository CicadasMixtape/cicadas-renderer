package cicadas.mixtape.renderer.shader.uniform.impl

import cicadas.mixtape.renderer.shader.data.MsdfData
import cicadas.mixtape.renderer.shader.uniform.api.CustomUniform
import com.mojang.blaze3d.buffers.Std140SizeCalculator

object MsdfUniform : CustomUniform<MsdfData>(
    "Msdf",
    Std140SizeCalculator().putFloat().putFloat().get()
)