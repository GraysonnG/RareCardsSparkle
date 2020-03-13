package com.blanktheevil.rarecardssparkle.patches

import com.blanktheevil.rarecardssparkle.RareCardsSparkle
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch
import com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen

@Suppress("unused")
@SpirePatch(clz = CardLibraryScreen::class, method = "updateCards")
class RareCardsSparkleLibraryUpdateCardsPatch {
  companion object {
    @SpirePostfixPatch
    @JvmStatic
    fun updateSparkles(@Suppress("UNUSED_PARAMETER") screen: CardLibraryScreen) {
      RareCardsSparkle.menuSparkles.parallelStream()
        .forEach { it.update() }
      RareCardsSparkle.menuSparkles.removeIf { it.isDone }
    }
  }
}