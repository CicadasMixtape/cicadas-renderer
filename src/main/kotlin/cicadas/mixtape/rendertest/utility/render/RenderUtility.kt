package cicadas.mixtape.rendertest.utility.render

import cicadas.mixtape.rendertest.api.window
import com.mojang.blaze3d.ProjectionType
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.renderer.CachedOrthoProjectionMatrixBuffer

object RenderUtility {
    private val projection = CachedOrthoProjectionMatrixBuffer("rendertest-projection", -1000f, 1000f, true)

    fun projection(unit: () -> Unit) {
        val width = window.width.toFloat() / 2f
        val height = window.height.toFloat() / 2f
        val buffer = RenderSystem.getProjectionMatrixBuffer()!!
        val type = RenderSystem.getProjectionType()

        RenderSystem.setProjectionMatrix(projection.getBuffer(width, height), ProjectionType.ORTHOGRAPHIC)
        unit()
        RenderSystem.setProjectionMatrix(buffer, type)
    }
}