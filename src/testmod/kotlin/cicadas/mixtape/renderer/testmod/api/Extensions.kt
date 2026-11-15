package cicadas.mixtape.renderer.testmod.api

import java.awt.Color

fun Any.init() = this

fun Color.withAlpha(alpha: Int) = Color(red, green, blue, alpha)