package com.blanktheevil.rarecardssparkle

import basemod.BaseMod
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.blanktheevil.rarecardssparkle.vfx.CardParticleEffect
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
import com.megacrit.cardcrawl.cards.AbstractCard
import java.io.IOException
import java.util.*
import java.util.function.Predicate
import kotlin.collections.ArrayList

@Suppress("unused")
@SpireInitializer
class RareCardsSparkle {

  companion object Statics {
    val defaultSparkleColor = Color(1f, 0.7f, 0.4f, 0f)
    val sparkleRules = mutableMapOf<String, SparkleRule>()
    val menuSparkles: ArrayList<CardParticleEffect> = ArrayList()

    private var name: String = ""
    private var version: String = ""
    private var modid: String = ""


    @JvmStatic
    fun initialize() {
      loadProjectProperties()
      log("Version", version)
      BaseMod.subscribe(RareCardsSparkleInit())
    }

    fun log(vararg items: String) {
      println(items.asList().joinToString(" : ", "$name "))
    }

    @JvmStatic
    fun addSparkleRule(id: String, color: Color, texture: TextureAtlas.AtlasRegion, hasRandomVelocity: Boolean, timer: SparkleTimer, conditionForSparkle: Predicate<AbstractCard>) {
      sparkleRules[id] = SparkleRule(
        id,
        color,
        texture,
        hasRandomVelocity,
        timer,
        conditionForSparkle
      )
    }

    @JvmStatic
    fun makeID(value: String): String {
      return "$modid:$value"
    }

    private fun loadProjectProperties() {
      try {
        with(Properties()) {
          load(RareCardsSparkle::class.java.getResourceAsStream("/META-INF/rare-cards-sparkle.prop"))
          name = getProperty("name")
          version = getProperty("version")
          modid = getProperty("modid")
        }
      } catch (e: IOException) {
        e.printStackTrace()
      }
    }
  }
}