package cicadas.mixtape.rendertest.testmod.font

import cicadas.mixtape.rendertest.font.MsdfFont
import cicadas.mixtape.rendertest.testmod.Testmod

object Fonts {
    // Дефолтные шрифты
    @JvmField val BOLD = MsdfFont.build(Testmod.id("default/sf_bold"))
    @JvmField val MEDIUM = MsdfFont.build(Testmod.id("default/sf_medium"))
    @JvmField val REGULAR = MsdfFont.build(Testmod.id("default/sf_regular"))
    @JvmField val SEMIBOLD = MsdfFont.build(Testmod.id("default/sf_semibold"))

    // Скруглённые шрифты
    @JvmField val ROUNDED_BOLD = MsdfFont.build(Testmod.id("rounded/sf_rounded_bold"))
    @JvmField val ROUNDED_MEDIUM = MsdfFont.build(Testmod.id("rounded/sf_rounded_medium"))
    @JvmField val ROUNDED_REGULAR = MsdfFont.build(Testmod.id("rounded/sf_rounded_regular"))
    @JvmField val ROUNDED_SEMIBOLD = MsdfFont.build(Testmod.id("rounded/sf_rounded_semibold"))
}