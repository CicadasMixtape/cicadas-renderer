package cicadas.mixtape.renderer.context

import cicadas.mixtape.renderer.objects.DefaultBox
import cicadas.mixtape.renderer.objects.DefaultOutline
import cicadas.mixtape.renderer.objects.VoxelBox
import cicadas.mixtape.renderer.objects.VoxelOutline
import cicadas.mixtape.renderer.shader.projection.Projection
import cicadas.mixtape.renderer.utility.render.Renderer3D
import com.mojang.blaze3d.vertex.PoseStack

class LevelContext private constructor(
    override val stack: PoseStack,
    override val projection: Projection,
) : UIContext(stack, projection, null, { true }) {
    companion object {
        @JvmStatic
        fun create(stack: PoseStack, projection: Projection) = LevelContext(stack, projection)
    }

    fun drawDefaultBox(box: DefaultBox) {
        Renderer3D.addDefaultBox(box)
    }

    fun drawDefaultOutline(outline: DefaultOutline) {
        Renderer3D.addDefaultOutline(outline)
    }

    fun drawVoxelBox(box: VoxelBox) {
        Renderer3D.addVoxelBox(box)
    }

    fun drawVoxelOutline(outline: VoxelOutline) {
        Renderer3D.addVoxelOutline(outline)
    }

    override fun update() {
        Renderer3D.update(this)
        super.update()
    }
}