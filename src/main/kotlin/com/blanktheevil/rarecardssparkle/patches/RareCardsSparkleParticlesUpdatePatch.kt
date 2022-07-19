package com.blanktheevil.rarecardssparkle.patches

import com.badlogic.gdx.Gdx
import com.blanktheevil.rarecardssparkle.RareCardsSparkle
import com.blanktheevil.rarecardssparkle.vfx.CardParticleEffect
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch
import com.megacrit.cardcrawl.core.CardCrawlGame

@Suppress("unused")
@SpirePatch(clz = CardCrawlGame::class, method = "update")
object RareCardsSparkleParticlesUpdatePatch {
  @SpirePostfixPatch
  @JvmStatic
  fun updateSparkles(game: CardCrawlGame) {
    RareCardsSparkle.menuSparkles.asSequence()
      .forEach { it.update() }
    RareCardsSparkle.menuSparkles.removeIf { it.isDone }

    CardParticleEffect.easterEggHue += Gdx.graphics.rawDeltaTime.div(5f)
    if (CardParticleEffect.easterEggHue > 1f) {
      CardParticleEffect.easterEggHue = 0f
    }
  }
}
