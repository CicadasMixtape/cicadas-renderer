package cicadas.mixtape.renderer.shader.uniform.impl

import cicadas.mixtape.renderer.shader.data.ShadowData
import cicadas.mixtape.renderer.shader.uniform.api.CustomUniform
import com.mojang.blaze3d.buffers.Std140SizeCalculator

object ShadowUniform : CustomUniform<ShadowData>(
    "Shadow",
    Std140SizeCalculator().putFloat().get()
)