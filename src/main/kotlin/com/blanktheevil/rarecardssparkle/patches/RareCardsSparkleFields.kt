package com.blanktheevil.rarecardssparkle.patches

import com.badlogic.gdx.graphics.Color
import com.blanktheevil.rarecardssparkle.SparkleTimer
import com.evacipated.cardcrawl.modthespire.lib.SpireField
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.cards.AbstractCard

@SpirePatch(clz = AbstractCard::class, method = SpirePatch.CLASS)
class RareCardsSparkleFields {
  companion object {
    @JvmField
    var sparkleTimer: SpireField<SparkleTimer?> = SpireField { null }

    @JvmField
    var shouldSparkle: SpireField<Boolean> = SpireField { false }

    @JvmField
    var sparkleColor: SpireField<Color?> = SpireField { null }
  }
}