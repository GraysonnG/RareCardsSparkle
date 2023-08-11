package com.blanktheevil.rarecardssparkle.patches

import com.blanktheevil.rarecardssparkle.helpers.SparkleRenderHelper
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.CardCrawlGame

@Suppress("unused", "unused_parameter")
@SpirePatch(clz = AbstractCard::class, method = "update")
object RareCardsSparkleUpdatePatch {
  @SpirePostfixPatch
  @JvmStatic
  fun updateSparkles(card: AbstractCard) {
    SparkleRenderHelper.addSparklesToCard(card, !CardCrawlGame.isInARun())
  }
}
