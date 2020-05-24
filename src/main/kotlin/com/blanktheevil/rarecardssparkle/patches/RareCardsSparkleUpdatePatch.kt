package com.blanktheevil.rarecardssparkle.patches

import com.blanktheevil.rarecardssparkle.RareCardsSparkle
import com.blanktheevil.rarecardssparkle.helpers.SparkleRenderHelper
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch
import com.megacrit.cardcrawl.cards.AbstractCard

@Suppress("unused", "unused_parameter")
@SpirePatch(clz = AbstractCard::class, method = "update")
object RareCardsSparkleUpdatePatch {
  @SpirePostfixPatch
  @JvmStatic
  fun updateSparkles(card: AbstractCard) {
    SparkleRenderHelper.addSparklesToCard(card, true)

    RareCardsSparkle.menuSparkles.asSequence()
      .filter { it.card == card }
      .forEach { it.update() }
    RareCardsSparkle.menuSparkles.removeIf { it.isDone }
  }
}
