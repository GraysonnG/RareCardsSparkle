package rarecardssparkle.extensions

import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.vfx.AbstractGameEffect
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL30
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import rarecardssparkle.RareCardsSparkle

fun Int.scale(): Float = this * Settings.scale
fun Float.scale(): Float = this * Settings.scale

fun AbstractGameEffect.applyTime() {
    this.duration -= Gdx.graphics.deltaTime
}

fun AbstractGameEffect.applyDoneWhenComplete(checkFor: Boolean) {
    if (checkFor) {
        isDone = true;
    }
}

fun SpriteBatch.additiveMode() {
    this.setBlendFunction(GL30.GL_SRC_ALPHA, GL30.GL_ONE)
}

fun SpriteBatch.normalMode() {
    this.setBlendFunction(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA)
}

fun Color.fixAlpha(): Color {
    this.a = 1.0f;
    return this
}

fun String.log() {
    RareCardsSparkle.log(this)
}
