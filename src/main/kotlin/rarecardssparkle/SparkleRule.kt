package rarecardssparkle

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.MathUtils
import com.megacrit.cardcrawl.cards.AbstractCard
import java.util.function.Predicate

class SparkleRule(val color: Color, private val rule: Predicate<AbstractCard>) {
  constructor(rule: Predicate<AbstractCard>) : this(createDefaultColor(), rule)

  fun test(card: AbstractCard): Boolean {
    return rule.test(card)
  }

  companion object {
    private fun createDefaultColor(): Color {
      return Color(1f, MathUtils.random(0.7f, 1f), 0.4f, 0f)
    }
  }
}