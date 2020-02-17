package rarecardssparkle.patches

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.evacipated.cardcrawl.modthespire.lib.SpireField
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import rarecardssparkle.RareCardsSparkle
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
            var sparkleTimer: SpireField<SparkleTimer?> = SpireField { SparkleTimer(0.05f, 0.1f) }

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
                val isOnScreenMethod = AbstractCard::class.java.getDeclaredMethod("isOnScreen")
                isOnScreenMethod.isAccessible = true
                val isOnScreen = isOnScreenMethod.invoke(card) as Boolean
                var shouldSparkle = AbstractCardFields.shouldSparkle.get(card) as Boolean
                var sparkleTimer = AbstractCardFields.sparkleTimer.get(card) as SparkleTimer
                var sparkleColor = AbstractCardFields.sparkleColor.get(card)

                for (sparkleRule: Predicate<AbstractCard> in RareCardsSparkle.sparkleRules) {
                    if (sparkleRule.test(card)) {
                        shouldSparkle = true;
                    }
                }

                if (shouldSparkle && !Settings.hideCards && !card.isFlipped && isOnScreen) {
                    sparkleTimer.applyTime()
                    if (sparkleTimer.shouldReset()) {
                        AbstractDungeon.topLevelEffects.add(RareCardParticleEffect(card.hb, sparkleColor))
                        sparkleTimer.reset()
                    }
                }

                sb.color = Color.WHITE.fixAlpha()
                sb.normalMode()
            }
        }
    }
}