package com.blanktheevil.rarecardssparkle.crossover

import LightsOut.patches.CustomLightPatches
import com.blanktheevil.rarecardssparkle.vfx.CardParticleEffect
import com.evacipated.cardcrawl.modthespire.Loader

fun Sequence<CardParticleEffect>.handleLightsOutCrossover(): Sequence<CardParticleEffect> {
  if (Loader.isModLoaded("LightsOut")) {
    this.forEach {
      CustomLightPatches.processCustomLights(it)
    }
  }
  return this
}