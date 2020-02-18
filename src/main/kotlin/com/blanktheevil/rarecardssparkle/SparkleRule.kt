package com.blanktheevil.rarecardssparkle

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import com.megacrit.cardcrawl.cards.AbstractCard
import java.util.function.Predicate

class SparkleRule(val color: Color, val texture: AtlasRegion?, val floaty: Boolean, private val rule: Predicate<AbstractCard>) {
  fun test(card: AbstractCard): Boolean {
    return rule.test(card)
  }
}