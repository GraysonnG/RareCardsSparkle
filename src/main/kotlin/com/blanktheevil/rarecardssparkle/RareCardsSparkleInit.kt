package com.blanktheevil.rarecardssparkle

import basemod.*
import basemod.interfaces.PostInitializeSubscriber
import com.badlogic.gdx.graphics.Color
import com.blanktheevil.rarecardssparkle.helpers.SparkleRuleHelper
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.colorless.Madness
import com.megacrit.cardcrawl.helpers.CardLibrary
import com.megacrit.cardcrawl.helpers.ImageMaster
import java.util.function.Consumer
import java.util.function.Predicate
import kotlin.streams.toList

class RareCardsSparkleInit : PostInitializeSubscriber {
  companion object {
    private const val MAX_PPS = 30f
    private val settingsMenu = ModPanel()

    fun initialize() {
      SparkleRuleHelper.loadRulesFromJson().forEach {
        RareCardsSparkle.sparkleRules[it.id]?.applySparkleRuleDefinition(it)
      }
      // load sparkle rule setting from json file
      // override any existing rule settings with file settings

      val sliderLabel = ModLabel(
        "Sparkles Per Second by Rule",
        375f,
        750f,
        Color.WHITE.cpy(),
        settingsMenu,
        Consumer { /* do nothing */ }
      )

      RareCardsSparkle.sparkleRules.values.forEachIndexed { index: Int, sparkleRule: SparkleRule ->
        val previewCard = CardElement(
          910f.plus((175).times(index)),
          750f,
          0.5f,
          findCardByRule(sparkleRule)
        )

        val slider = ModSlider(
          sparkleRule.name,
          500f,
          700f.plus((-50).times(index)),
          MAX_PPS,
          "p/s",
          settingsMenu,
          Consumer {
            val newMin = 1f.div(it.value.times(MAX_PPS))
            sparkleRule.timer.setNewMinMax(newMin, newMin.plus(0.025f))
            SparkleRuleHelper.saveRulesAsJson()
          })

        val checkbox = ModToggleButton(
          815f,
          683f.plus((-50).times(index)),
          settingsMenu,
          Consumer {
            sparkleRule.enabled = it.enabled
            SparkleRuleHelper.saveRulesAsJson()
          })

        slider.setValue(sparkleRule.timer.tps.div(MAX_PPS))
        checkbox.enabled = sparkleRule.enabled

        settingsMenu.addUIElement(slider)
        settingsMenu.addUIElement(checkbox)
        settingsMenu.addUIElement(previewCard)
      }

      // x = ESTIMATED_FRAME TIME / 0.15

      settingsMenu.addUIElement(sliderLabel)


      SparkleRuleHelper.saveRulesAsJson()
    }

    private fun findCardByRule(rule: SparkleRule): AbstractCard {
      val filteredCards = CardLibrary.getAllCards().stream()
        .filter { rule.test(it) }
        .distinct()
        .toList()

      return if (filteredCards.isNotEmpty()) {
        filteredCards[0]
      } else {
        Madness()
      }
    }
  }

  override fun receivePostInitialize() {
    val badge = ImageMaster.loadImage("com/blanktheevil/rarecardssparkle/images/badge.png")
    BaseMod.registerModBadge(badge, "Some Name", "Blank The Evil", "some description", settingsMenu)

    RareCardsSparkle.addSparkleRule(
      RareCardsSparkle.makeID("RareCardsGoldSparkle"),
      "Rare",
      Color(1f, 0.85f, 0.4f, 0f),
      ImageMaster.ROOM_SHINE_2,
      false,
      SparkleTimer(0.15f, 0.175f),
      Predicate<AbstractCard> {
        it.rarity == AbstractCard.CardRarity.RARE
      }
    )

    RareCardsSparkle.addSparkleRule(
      RareCardsSparkle.makeID("SpecialCardsBlueSparkle"),
      "Special",
      Color(0.6f, 0.7f, 1f, 0f),
      ImageMaster.GLOW_SPARK_2,
      true,
      SparkleTimer(0.15f, 0.175f),
      Predicate<AbstractCard> {
        it.rarity == AbstractCard.CardRarity.SPECIAL
      }
    )
  }
}