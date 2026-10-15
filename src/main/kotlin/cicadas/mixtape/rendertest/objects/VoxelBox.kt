package cicadas.mixtape.rendertest.objects

import net.minecraft.core.BlockPos
import net.minecraft.world.phys.shapes.VoxelShape
import java.awt.Color

data class VoxelBox(
    val pos: BlockPos,
    val shape: VoxelShape,
    val color: Color
)