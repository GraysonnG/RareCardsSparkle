package com.blanktheevil.rarecardssparkle

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.MathUtils

class SparkleTimer(var min: Float, var max: Float) {
  private var time: Float = 0.0f

  var tps: Float = 1f.div(min)

  fun fireOnTime(): Boolean {
    time -= Gdx.graphics.rawDeltaTime
    val shouldReset = time < 0f

    if (time <= 0.0) {
      time = MathUtils.random(this.min, this.max)
    }

    return shouldReset
  }

  fun setNewMinMax(min: Float, max: Float) {
    this.min = min
    this.max = max
    this.tps = 1f.div(min)
  }
}