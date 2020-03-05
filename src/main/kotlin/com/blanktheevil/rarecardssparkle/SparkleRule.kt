package com.blanktheevil.rarecardssparkle

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import com.megacrit.cardcrawl.cards.AbstractCard
import java.util.function.Predicate

class SparkleRule(private val id: String, val name: String, val color: Color, val texture: AtlasRegion?, val floaty: Boolean, var timer: SparkleTimer, private val rule: Predicate<AbstractCard>) {
  fun test(card: AbstractCard): Boolean {
    return rule.test(card)
  }

  fun asSparkleRuleDefinition(): SparkleRuleDefinition {
    return SparkleRuleDefinition(id, timer.min, timer.max)
  }

  fun applySparkleRuleDefinition(definition: SparkleRuleDefinition) {
    println("apply definition: ${definition.min}, ${definition.max}")
    timer.setNewMinMax(definition.min, definition.max)
  }
}