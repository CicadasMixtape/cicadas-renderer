package cicadas.mixtape.renderer.context

import cicadas.mixtape.renderer.objects.DefaultBox
import cicadas.mixtape.renderer.objects.DefaultOutline
import cicadas.mixtape.renderer.objects.VoxelBox
import cicadas.mixtape.renderer.objects.VoxelOutline
import cicadas.mixtape.renderer.shader.projection.Projection
import cicadas.mixtape.renderer.utility.render.Renderer3D
import com.mojang.blaze3d.vertex.PoseStack

class LevelContext private constructor(
    stack: PoseStack,
    projection: Projection,
) : UIContext(stack, projection, null, { true }) {
    companion object {
        @JvmStatic
        fun create(stack: PoseStack, projection: Projection) = LevelContext(stack, projection)
    }

    fun drawDefaultBox(box: DefaultBox) = Renderer3D.drawDefaultBox(this, box)

    fun drawDefaultOutline(outline: DefaultOutline) = Renderer3D.drawDefaultOutline(this, outline)

    fun drawVoxelBox(box: VoxelBox) = Renderer3D.drawVoxelBox(this, box)

    fun drawVoxelOutline(outline: VoxelOutline) = Renderer3D.drawVoxelOutline(this, outline)

    override fun update() {
        super.update()
        Renderer3D.update()
    }
}