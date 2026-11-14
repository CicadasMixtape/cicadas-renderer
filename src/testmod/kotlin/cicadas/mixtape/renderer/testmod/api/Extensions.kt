package cicadas.mixtape.renderer.testmod.api

import org.joml.Vector4f
import java.awt.Color

fun Any.init() = this

val Color.vector4f get() = Vector4f(red / 255f, green / 255f, blue / 255f, alpha / 255f)

fun Color.withAlpha(alpha: Int) = Color(red, green, blue, alpha)