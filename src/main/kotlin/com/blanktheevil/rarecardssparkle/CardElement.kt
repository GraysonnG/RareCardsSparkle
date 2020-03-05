package com.blanktheevil.rarecardssparkle

import basemod.IUIElement
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.blanktheevil.rarecardssparkle.extensions.scale
import com.blanktheevil.rarecardssparkle.helpers.SparkleRenderHelper
import com.megacrit.cardcrawl.cards.AbstractCard

class CardElement(private val x: Float,private val y: Float, private val scale: Float, private val previewCard: AbstractCard) : IUIElement {

  init {
    previewCard.drawScale = scale
    previewCard.targetDrawScale = scale
    previewCard.isSeen = true
  }

  override fun update() {
    previewCard.current_x = x.plus(256f.times(scale).div(2f)).scale()
    previewCard.current_y = y.minus(256f.times(scale).div(2f)).scale()
    previewCard.target_x = x.plus(256f.times(scale).div(2f)).scale()
    previewCard.target_y = y.minus(256f.times(scale).div(2f)).scale()
    previewCard.update()
    RareCardsSparkle.menuSparkles.forEach { it.update() }
    RareCardsSparkle.menuSparkles.removeIf { it.isDone }
  }

  override fun render(sb: SpriteBatch) {
    with(sb) {
      color = Color.WHITE.cpy()
      previewCard.render(this)
      SparkleRenderHelper.addSparklesToCard(previewCard, this, true, true)
      RareCardsSparkle.menuSparkles.forEach { it.render(this) }
    }
  }

  override fun updateOrder(): Int {
    return 1
  }

  override fun renderLayer(): Int {
    return 1
  }
}