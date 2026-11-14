package cicadas.mixtape.renderer.testmod.font

import cicadas.mixtape.renderer.font.MsdfFont
import cicadas.mixtape.renderer.testmod.Testmod

object Fonts {
    @JvmField val BOLD = MsdfFont.build(Testmod.id("default/sf_bold"))
    @JvmField val ROUNDED_BOLD = MsdfFont.build(Testmod.id("rounded/sf_bold"))
}