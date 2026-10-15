package cicadas.mixtape.rendertest.objects

import net.minecraft.world.phys.AABB
import java.awt.Color

data class DefaultBox(
    val aabb: AABB,
    val color: Color
)