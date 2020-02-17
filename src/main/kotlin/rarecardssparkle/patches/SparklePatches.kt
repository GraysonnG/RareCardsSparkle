package rarecardssparkle.patches

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.evacipated.cardcrawl.modthespire.lib.*
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.CardGroup
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen
import javassist.CtBehavior
import rarecardssparkle.RareCardsSparkle
import rarecardssparkle.SparkleRule
import rarecardssparkle.SparkleTimer
import rarecardssparkle.extensions.*
import rarecardssparkle.vfx.RareCardParticleEffect
import java.util.function.Predicate

@Suppress("unused")
class SparklePatches {

  @SpirePatch(clz = AbstractCard::class, method = SpirePatch.CLASS)
  class AbstractCardFields {
    companion object {
      @JvmField
      var sparkleTimer: SpireField<SparkleTimer?> = SpireField { SparkleTimer(0.1f, 0.15f) }

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
          val isOnScreenMethod = AbstractCard::class.java.getDeclaredMethod("isOnScreen")
          isOnScreenMethod.isAccessible = true
          val isOnScreen = isOnScreenMethod.invoke(card) as Boolean
          var shouldSparkle = shouldSparkle.get(card) as Boolean
          var sparkleTimer = sparkleTimer.get(card) as SparkleTimer
          var sparkleColor = sparkleColor.get(card)

          for (sparkleRule: SparkleRule in RareCardsSparkle.sparkleRules) {
            if (sparkleRule.test(card)) {
              shouldSparkle = true;
            }
            if (sparkleColor == null) {
              sparkleColor = sparkleRule.color.cpy()
            }
          }

          if (shouldSparkle && !Settings.hideCards && !card.isFlipped && isOnScreen) {
            sparkleTimer.applyTime()
            if (sparkleTimer.shouldReset()) {
              AbstractDungeon.topLevelEffects.add(RareCardParticleEffect(card.hb, sparkleColor))
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
          val isOnScreenMethod = AbstractCard::class.java.getDeclaredMethod("isOnScreen")
          isOnScreenMethod.isAccessible = true
          val isOnScreen = isOnScreenMethod.invoke(card) as Boolean
          var shouldSparkle = shouldSparkle.get(card) as Boolean
          var sparkleTimer = sparkleTimer.get(card) as SparkleTimer
          var sparkleColor = sparkleColor.get(card)

          for (sparkleRule: SparkleRule in RareCardsSparkle.sparkleRules) {
            if (sparkleRule.test(card)) {
              shouldSparkle = true;
            }
            if (sparkleColor == null) {
              sparkleColor = sparkleRule.color.cpy()
            }
          }

          if (shouldSparkle && isOnScreen) {
            sparkleTimer.applyTime()
            if (sparkleTimer.shouldReset()) {
              RareCardsSparkle.menuSparkles.add(RareCardParticleEffect(card.hb, sparkleColor))
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

      class Locator() : SpireInsertLocator() {
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