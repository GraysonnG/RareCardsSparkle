package com.blanktheevil.rarecardssparkle

import basemod.BaseMod
import basemod.interfaces.PostInitializeSubscriber
import com.badlogic.gdx.graphics.Color
import com.blanktheevil.rarecardssparkle.vfx.CardParticleEffect
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.helpers.ImageMaster
import java.io.IOException
import java.util.*
import java.util.function.Predicate
import kotlin.collections.ArrayList

@Suppress("unused")
@SpireInitializer
class RareCardsSparkle : PostInitializeSubscriber {
  companion object Statics {
    val defaultSparkleColor = Color(1f, 0.7f, 0.4f, 0f)
    val sparkleRules: ArrayList<SparkleRule> = ArrayList()
    val menuSparkles: ArrayList<CardParticleEffect> = ArrayList()

    private var name: String = ""
    private var version: String = ""


    @JvmStatic
    fun initialize() {
      loadProjectProperties()
      log("Version", version)
      BaseMod.subscribe(RareCardsSparkle())
    }

    fun log(vararg items: String) {
      println(items.asList().joinToString(" : ", "$name "))
    }

    private fun loadProjectProperties() {
      try {
        with(Properties()) {
          load(RareCardsSparkle::class.java.getResourceAsStream("/META-INF/rare-cards-sparkle.prop"))
          name = getProperty("name")
          version = getProperty("version")
        }
      } catch (e: IOException) {
        e.printStackTrace()
      }
    }
  }

  override fun receivePostInitialize() {
    sparkleRules.add(
      SparkleRule(
        Color(1f, 0.85f, 0.4f, 0f),
        ImageMaster.ROOM_SHINE_2,
        false,
        SparkleTimer(0.15f, 0.175f),
        Predicate<AbstractCard> {
          it.rarity == AbstractCard.CardRarity.RARE
        }
      )
    )
    sparkleRules.add(
      SparkleRule(
        Color(0.6f, 0.7f, 1f, 0f),
        ImageMaster.GLOW_SPARK_2,
        true,
        SparkleTimer(0.15f, 0.175f),
        Predicate<AbstractCard> {
          it.rarity == AbstractCard.CardRarity.SPECIAL
        }
      )
    )
  }
}