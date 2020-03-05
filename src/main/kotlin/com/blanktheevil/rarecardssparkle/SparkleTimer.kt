package com.blanktheevil.rarecardssparkle

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.MathUtils

class SparkleTimer(private val min: Float, private val max: Float) {
  private var time: Float = 0.0f;

  fun fireOnTime(): Boolean {
    time -= Gdx.graphics.rawDeltaTime
    val shouldReset = time < 0f

    if (time <= 0.0) {
      time = MathUtils.random(this.min, this.max)
    }

    return shouldReset
  }
}