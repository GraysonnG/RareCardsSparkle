package com.blanktheevil.rarecardssparkle.patches

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.blanktheevil.rarecardssparkle.extensions.fixAlpha
import com.blanktheevil.rarecardssparkle.extensions.normalMode
import com.blanktheevil.rarecardssparkle.helpers.SparkleRenderHelper
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch
import com.megacrit.cardcrawl.cards.AbstractCard

@Suppress("unused")
@SpirePatch(clz = AbstractCard::class, method = "renderCard")
class RareCardsSparkleRenderPatch {
  companion object {
    @SpirePostfixPatch
    @JvmStatic
    fun renderSparkles(card: AbstractCard, sb: SpriteBatch) {
      SparkleRenderHelper.addSparklesToCard(card, sb, false)
      with(sb) {
        color = Color.WHITE.fixAlpha()
        normalMode()
      }
    }
  }
}