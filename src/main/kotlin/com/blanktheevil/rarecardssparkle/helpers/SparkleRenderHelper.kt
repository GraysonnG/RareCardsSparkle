package com.blanktheevil.rarecardssparkle.helpers

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.blanktheevil.rarecardssparkle.RareCardsSparkle
import com.blanktheevil.rarecardssparkle.SparkleTimer
import com.blanktheevil.rarecardssparkle.patches.RareCardsSparkleFields
import com.blanktheevil.rarecardssparkle.vfx.CardParticleEffect
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon

@Suppress("UNUSED_PARAMETER", "unused")
class SparkleRenderHelper {
  companion object {
    fun addSparklesToCard(card: AbstractCard, renderInLibrary: Boolean) {
      val isOnScreen = AbstractCard::class.java.getDeclaredMethod("isOnScreen").apply {
        isAccessible = true
      }.invoke(card) as Boolean
      var shouldSparkle = RareCardsSparkleFields.shouldSparkle.get(card) as Boolean
      var sparkleTimer = RareCardsSparkleFields.sparkleTimer.get(card)
      var sparkleColor = RareCardsSparkleFields.sparkleColor.get(card)
      var floaty = false
      var sparkleTexture: TextureAtlas.AtlasRegion? = null

      RareCardsSparkle.sparkleRules.values.stream()
        .filter { it.enabled }
        .filter { it.test(card) }
        .forEach {
          shouldSparkle = true
          sparkleTexture = it.texture
          sparkleColor = it.color.cpy()
          sparkleTimer = it.timer
          floaty = it.floaty
        }

      if (sparkleTimer == null) {
        sparkleTimer = SparkleTimer(0.1f, 0.15f)
      }


      with(sparkleTimer!!) {
        if (renderInLibrary) {
          if (shouldSparkle && isOnScreen) {
            if (fireOnTime()) {
              RareCardsSparkle.menuSparkles.add(CardParticleEffect(card, sparkleColor, sparkleTexture, floaty))
            }
          }
        } else {
          if (shouldSparkle && !Settings.hideCards && !card.isFlipped && isOnScreen) {
            if (fireOnTime()) {
              AbstractDungeon.topLevelEffectsQueue.add(CardParticleEffect(card, sparkleColor, sparkleTexture, floaty))
            }
          }
        }
      }
    }
  }
}