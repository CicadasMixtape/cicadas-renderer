package cicadas.mixtape.renderer.objects

import net.minecraft.world.phys.AABB
import java.awt.Color

data class DefaultOutline(
    val aabb: AABB,
    val color: Color,
    val lineWidth: Float
)