package cicadas.mixtape.renderer.objects

import net.minecraft.core.BlockPos
import net.minecraft.world.phys.shapes.VoxelShape
import java.awt.Color

data class VoxelOutline(
    val pos: BlockPos,
    val shape: VoxelShape,
    val color: Color,
    val lineWidth: Float
)