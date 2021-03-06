package com.blanktheevil.rarecardssparkle.patches

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.blanktheevil.rarecardssparkle.RareCardsSparkle
import com.blanktheevil.rarecardssparkle.patches.locators.RenderTipLocator
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.cards.CardGroup
import com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen

@Suppress("unused", "UNUSED_PARAMETER")
@SpirePatch(clz = CardLibraryScreen::class, method = "renderGroup")
object RareCardsSparkleLibraryRenderGroupPatch {
  @SpireInsertPatch(locator = RenderTipLocator::class)
  @JvmStatic
  fun renderSparkles(screen: CardLibraryScreen, sb: SpriteBatch, cg: CardGroup) {
    RareCardsSparkle.menuSparkles.asSequence()
      .forEach { it.render(sb) }
  }
}
