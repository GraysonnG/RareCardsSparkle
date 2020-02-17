package rarecardssparkle

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.MathUtils

class SparkleTimer(private val min: Float, private val max: Float) {
  private var time: Float = 0.0f;

  fun reset() {
    time = MathUtils.random(this.min, this.max)
  }

  fun applyTime() {
    time -= Gdx.graphics.deltaTime
  }

  fun shouldReset(): Boolean {
    return time < 0f;
  }
}