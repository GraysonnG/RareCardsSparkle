package com.blanktheevil.rarecardssparkle.vfx

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.MathUtils
import com.blanktheevil.rarecardssparkle.RareCardsSparkle
import com.blanktheevil.rarecardssparkle.extensions.*
import com.evacipated.cardcrawl.modthespire.Loader
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.vfx.AbstractGameEffect
import java.time.Instant
import java.util.*

class CardParticleEffect(val card: AbstractCard, color: Color?, texture: AtlasRegion?, floaty: Boolean) : AbstractGameEffect() {
  private var x: Float = card.hb.x
  private var y: Float = card.hb.y
  private var vX: Float = 0.0f
  private var vY: Float = 0.0f
  private var oX: Float
  private var oY: Float
  private var halfWidth: Float = card.hb.width.div(2.0f)
  private var halfHeight: Float = card.hb.height.div(2.0f)
  private var halfDuration: Float
  private var img: AtlasRegion = ImageMaster.ROOM_SHINE_2

  companion object {
    var easterEggHue = 0f
  }

  init {
    duration = MathUtils.random(0.9f, 1.2f)
    scale = MathUtils.random(0.4f, 0.6f).scale()
    halfDuration = duration.div(2f)
    oX = MathUtils.random(-halfWidth, halfWidth) - img.packedWidth.div(2f)
    oY = MathUtils.random(-halfHeight, halfHeight) - img.packedHeight.div(2f)
    rotation = MathUtils.random(-5f, 5f)

    if (color != null) {
      this.color = color.cpy()
    } else {
      this.color = Color(1f, 0.85f, 0.4f, 0f)
    }

    doPrideEasterEgg()

    if (floaty) {
      vX = MathUtils.random(-5f, 5f).scale()
      vY = MathUtils.random(-7f, 7f).scale()
    }

    img = texture ?: ImageMaster.ROOM_SHINE_2
  }

  override fun update() {
    this.x = card.hb.cX
    this.y = card.hb.cY
    applyInterpolationToAlpha()
    applyVelocity()
    applyTime()
    setDoneWhenComplete(duration < 0f)
  }

  private fun applyInterpolationToAlpha() {
    with(Interpolation.pow3In) {
      color.a = if (duration > halfDuration) {
        apply(0.6f, 0f, duration.minus(halfDuration).div(halfDuration))
      } else {
        apply(0f, 0.6f, duration.div(halfDuration))
      }
    }
  }

  private fun applyVelocity() {
    oX += vX.times(Gdx.graphics.deltaTime)
    oY += vY.times(Gdx.graphics.deltaTime)
  }

  override fun render(sb: SpriteBatch) {
    with(sb) {
      color = this@CardParticleEffect.color
      additiveMode()
      draw(
        img,
        x + oX,
        y + oY,
        img.packedWidth.div(2f),
        img.packedHeight.div(2f),
        img.packedWidth.toFloat(),
        img.packedHeight.toFloat(),
        scale.times(card.drawScale),
        scale.times(MathUtils.random(0.6f, 1.4f)).times(card.drawScale),
        rotation
      )
      color = Color.WHITE.fixAlpha()
      normalMode()
    }
  }

  override fun dispose() {}

  private fun doPrideEasterEgg() {
    val isPrideMonth = Date.from(Instant.now()).month == 5 && RareCardsSparkle.config.allowEasterEggs
    val isPrideModLoaded = Loader.isModLoaded("PrideMod")

    if (isPrideMonth || isPrideModLoaded) {
      color = colorFromHSL(
        easterEggHue,
        1f,
        0.5f
      )
    }
  }
}