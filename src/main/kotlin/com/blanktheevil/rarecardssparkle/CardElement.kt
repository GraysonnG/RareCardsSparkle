package com.blanktheevil.rarecardssparkle

import basemod.IUIElement
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.blanktheevil.rarecardssparkle.extensions.scale
import com.blanktheevil.rarecardssparkle.helpers.SparkleRenderHelper
import com.megacrit.cardcrawl.cards.AbstractCard

class CardElement(
  private val xPos: Float,
  private val yPos: Float,
  private val scale: Float,
  private val previewCard: AbstractCard
) : IUIElement {

  init {
    previewCard.isSeen = true
  }

  override fun update() {
    with(previewCard) {
      drawScale = scale
      targetDrawScale = scale
      current_x = xPos.plus(256f.times(scale).div(2f)).scale()
      current_y = yPos.minus(256f.times(scale).div(2f)).scale()
      target_x = xPos.plus(256f.times(scale).div(2f)).scale()
      target_y = yPos.minus(256f.times(scale).div(2f)).scale()
      drawScale = scale
      targetDrawScale = scale
      isSeen = true
      update()
    }
    SparkleRenderHelper.addSparklesToCard(previewCard, true)
    RareCardsSparkle.menuSparkles.asSequence()
      .filter { it.card == previewCard }
      .forEach { it.update() }
    RareCardsSparkle.menuSparkles.removeIf { it.isDone }
  }

  override fun render(sb: SpriteBatch) {
    with(sb) {
      color = Color.WHITE.cpy()
      previewCard.render(this)

      RareCardsSparkle.menuSparkles.asSequence()
        .filter { it.card == previewCard }
        .forEach { it.render(this) }
    }
  }

  override fun updateOrder(): Int {
    return 1
  }

  override fun renderLayer(): Int {
    return 1
  }
}