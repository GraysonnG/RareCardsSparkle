package com.blanktheevil.rarecardssparkle.patches

import com.blanktheevil.rarecardssparkle.RareCardsSparkle
import com.blanktheevil.rarecardssparkle.RareCardsSparkleInit
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch
import com.megacrit.cardcrawl.core.CardCrawlGame

@Suppress("unused")
@SpirePatch(clz = CardCrawlGame::class, method = "create")
class RareCardsSparkleInitPatch {
  companion object {
    @SpirePostfixPatch
    @JvmStatic
    fun init() {
      RareCardsSparkleInit.initialize()
      RareCardsSparkle.log("done init")
    }
  }
}