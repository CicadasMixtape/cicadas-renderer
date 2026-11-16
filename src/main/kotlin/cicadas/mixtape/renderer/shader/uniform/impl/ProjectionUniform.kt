package cicadas.mixtape.renderer.shader.uniform.impl

import cicadas.mixtape.renderer.shader.data.ProjectionData
import cicadas.mixtape.renderer.shader.uniform.api.CustomUniform
import com.mojang.blaze3d.buffers.Std140SizeCalculator

object ProjectionUniform : CustomUniform<ProjectionData>(
    "Projection",
    Std140SizeCalculator().putMat4f().putMat4f().get()
)