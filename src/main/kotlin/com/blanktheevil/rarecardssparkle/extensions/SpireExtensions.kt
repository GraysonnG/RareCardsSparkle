@file:Suppress("unused")

package com.blanktheevil.rarecardssparkle.extensions

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL30
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.blanktheevil.rarecardssparkle.RareCardsSparkle
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.vfx.AbstractGameEffect
import java.util.function.Consumer

fun Int.scale(): Float = this * Settings.scale
fun Float.scale(): Float = this * Settings.scale

fun AbstractGameEffect.applyTime() {
  this.duration -= Gdx.graphics.deltaTime
}

fun AbstractGameEffect.setDoneWhenComplete(checkFor: Boolean) {
  if (checkFor) {
    isDone = true
  }
}

fun SpriteBatch.additiveMode() {
  this.setBlendFunction(GL30.GL_SRC_ALPHA, GL30.GL_ONE)
}

fun SpriteBatch.normalMode() {
  this.setBlendFunction(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA)
}

fun Color.fixAlpha(): Color {
  this.a = 1.0f
  return this
}

fun String.log() {
  RareCardsSparkle.log(this)
}

fun <T> doNothingConsumer(): Consumer<T> = Consumer {  }

fun colorFromHSL(hue: Float, sat: Float, light: Float): Color {
  return java.awt.Color.getHSBColor(hue, sat, light).let {
    Color(
      it.red.div(255f),
      it.green.div(255f),
      it.blue.div(255f),
      1f)
  }
}