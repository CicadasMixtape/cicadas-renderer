package cicadas.mixtape.renderer.testmod.shader.uniform

import cicadas.mixtape.renderer.shader.uniform.api.CustomUniform
import cicadas.mixtape.renderer.testmod.shader.data.OutlineData
import com.mojang.blaze3d.buffers.Std140SizeCalculator

object OutlineUniform : CustomUniform<OutlineData>(
    "Outline",
    Std140SizeCalculator().putVec4().putFloat().get()
)