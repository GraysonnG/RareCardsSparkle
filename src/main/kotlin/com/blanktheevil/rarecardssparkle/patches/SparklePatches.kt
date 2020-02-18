package com.blanktheevil.rarecardssparkle.patches

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.blanktheevil.rarecardssparkle.RareCardsSparkle
import com.blanktheevil.rarecardssparkle.SparkleRule
import com.blanktheevil.rarecardssparkle.SparkleTimer
import com.blanktheevil.rarecardssparkle.extensions.fixAlpha
import com.blanktheevil.rarecardssparkle.extensions.normalMode
import com.blanktheevil.rarecardssparkle.vfx.CardParticleEffect
import com.evacipated.cardcrawl.modthespire.lib.*
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.CardGroup
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen
import javassist.CtBehavior

@Suppress("unused")
class SparklePatches {

  @SpirePatch(clz = AbstractCard::class, method = SpirePatch.CLASS)
  class AbstractCardFields {
    companion object {
      @JvmField
      var sparkleTimer: SpireField<SparkleTimer?> = SpireField { null }

      @JvmField
      var shouldSparkle: SpireField<Boolean> = SpireField { false }

      @JvmField
      var sparkleColor: SpireField<Color?> = SpireField { null }
    }
  }

  @SpirePatch(clz = AbstractCard::class, method = "renderCard")
  class RareCardsSparkleRender {
    companion object {
      @SpirePostfixPatch
      @JvmStatic
      fun renderSparkles(card: AbstractCard, sb: SpriteBatch) {
        with(AbstractCardFields) {
          val isOnScreen = AbstractCard::class.java.getDeclaredMethod("isOnScreen").apply {
            isAccessible = true
          }.invoke(card) as Boolean

          var shouldSparkle = shouldSparkle.get(card) as Boolean
          var sparkleTimer = sparkleTimer.get(card)
          var sparkleColor = sparkleColor.get(card)
          var floaty = false
          var sparkleTexture: TextureAtlas.AtlasRegion? = null

          for (sparkleRule: SparkleRule in RareCardsSparkle.sparkleRules) {
            if (sparkleRule.test(card)) {
              shouldSparkle = true
              sparkleTexture = sparkleRule.texture
              floaty = sparkleRule.floaty

              if (sparkleTimer == null) {
                sparkleTimer = sparkleRule.timer
              }

              if (sparkleColor == null) {
                sparkleColor = sparkleRule.color.cpy()
              }
            }
          }

          if (sparkleTimer == null) {
            sparkleTimer = SparkleTimer(0.1f, 0.15f)
          }

          if (shouldSparkle && !Settings.hideCards && !card.isFlipped && isOnScreen) {
            sparkleTimer.applyTime()
            if (sparkleTimer.shouldReset()) {
              AbstractDungeon.topLevelEffects.add(CardParticleEffect(card.hb, sparkleColor, sparkleTexture, floaty))
              sparkleTimer.reset()
            }
          }
        }
        with(sb) {
          color = Color.WHITE.fixAlpha()
          normalMode()
        }
      }
    }
  }

  @SpirePatch(clz = AbstractCard::class, method = "renderInLibrary")
  class RareCardsSparkleLibraryRender {
    companion object {
      @SpirePostfixPatch
      @JvmStatic
      fun renderSparkles(card: AbstractCard, sb: SpriteBatch) {
        with(AbstractCardFields) {
          val isOnScreen = AbstractCard::class.java.getDeclaredMethod("isOnScreen").apply {
            isAccessible = true
          }.invoke(card) as Boolean

          var shouldSparkle = shouldSparkle.get(card) as Boolean
          var sparkleTimer = sparkleTimer.get(card)
          var sparkleColor = sparkleColor.get(card)
          var floaty = false
          var sparkleTexture: TextureAtlas.AtlasRegion? = null

          for (sparkleRule: SparkleRule in RareCardsSparkle.sparkleRules) {
            if (sparkleRule.test(card)) {
              shouldSparkle = true
              sparkleTexture = sparkleRule.texture
              floaty = sparkleRule.floaty

              if (sparkleTimer == null) {
                sparkleTimer = sparkleRule.timer
              }

              if (sparkleColor == null) {
                sparkleColor = sparkleRule.color.cpy()
              }
            }
          }
          if (sparkleTimer == null) {
            sparkleTimer = SparkleTimer(0.1f, 0.15f)
          }

          if (shouldSparkle && isOnScreen) {
            sparkleTimer.applyTime()
            if (sparkleTimer.shouldReset()) {
              RareCardsSparkle.menuSparkles.add(CardParticleEffect(card.hb, sparkleColor, sparkleTexture, floaty))
              sparkleTimer.reset()
            }
          }
        }
      }
    }
  }

  @SpirePatch(clz = CardLibraryScreen::class, method = "renderGroup")
  class RareCardsSparkleLibraryRenderGroup {
    companion object {
      @SpireInsertPatch(locator = Locator::class)
      @JvmStatic
      fun renderSparkles(screen: CardLibraryScreen, sb: SpriteBatch, cg: CardGroup) {
        RareCardsSparkle.menuSparkles.stream()
          .forEach { it.render(sb) }
      }

      class Locator : SpireInsertLocator() {
        override fun Locate(ctBehavior: CtBehavior?): IntArray {
          val matcher = Matcher.MethodCallMatcher(CardGroup::class.java, "renderTip")
          return LineFinder.findAllInOrder(ctBehavior, matcher)
        }
      }
    }
  }

  @SpirePatch(clz = CardLibraryScreen::class, method = "updateCards")
  class RareCardsSparkleLibraryUpdateCards {
    companion object {
      @SpirePostfixPatch
      @JvmStatic
      fun updateSparkles(screen: CardLibraryScreen) {
        RareCardsSparkle.menuSparkles.stream()
          .forEach { it.update() }
        RareCardsSparkle.menuSparkles.removeIf { it.isDone }
      }
    }
  }
}