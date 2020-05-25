package com.blanktheevil.rarecardssparkle

import basemod.*
import basemod.interfaces.EditStringsSubscriber
import basemod.interfaces.PostInitializeSubscriber
import com.badlogic.gdx.graphics.Color
import com.blanktheevil.rarecardssparkle.extensions.doNothingConsumer
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.colorless.Madness
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.helpers.CardLibrary
import com.megacrit.cardcrawl.helpers.FontHelper
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.localization.UIStrings
import java.util.function.Consumer
import java.util.function.Predicate
import kotlin.streams.toList

class RareCardsSparkleInit : PostInitializeSubscriber, EditStringsSubscriber {
  companion object {
    private const val MAX_PPS = 30f
    private const val LABEL_TEXT = 0
    private const val PER_SECOND_TEXT = 1
    private const val ENABLED_IN_COMBAT_TEXT = 2
    private val settingsMenu = ModPanel()

    private val ID = RareCardsSparkle.makeID("SettingsMenu")
    private val uiStrings: Array<String> by lazy {
      CardCrawlGame.languagePack.getUIString(ID).TEXT
    }

    fun initialize() {
      RareCardsSparkle.config.sparkleRules.forEach {
        RareCardsSparkle.sparkleRules[it.id]?.applySparkleRuleDefinition(it)
      }
      with(settingsMenu) {
        ModLabel(
          uiStrings[LABEL_TEXT],
          375f,
          750f,
          Color.WHITE.cpy(),
          this,
          doNothingConsumer()
        )
          .also {
            this.addUIElement(it)
          }

        RareCardsSparkle.sparkleRules.values.forEachIndexed { index: Int, sparkleRule: SparkleRule ->
          CardElement(
            910f.plus((175).times(index)),
            750f,
            0.5f,
            findCardByRule(sparkleRule)
          )
            .also {
              this.addUIElement(it)
            }

          ModSlider(
            sparkleRule.name,
            500f,
            700f.plus((-50).times(index)),
            MAX_PPS,
            uiStrings[PER_SECOND_TEXT],
            settingsMenu,
            Consumer {
              val newMin = 1f.div(it.value.times(MAX_PPS))
              sparkleRule.timer.setNewMinMax(newMin, newMin.plus(0.025f))
              RareCardsSparkle.config.save()
            })
            .also {
              it.setValue(sparkleRule.timer.tps.div(MAX_PPS))
              this.addUIElement(it)
            }

          ModToggleButton(
            815f,
            683f.plus((-50).times(index)),
            settingsMenu,
            Consumer {
              sparkleRule.enabled = it.enabled
              RareCardsSparkle.config.save()
            })
            .also {
              it.enabled = sparkleRule.enabled
              this.addUIElement(it)
            }
        }

        ModLabeledToggleButton(
          uiStrings[ENABLED_IN_COMBAT_TEXT],
          375f,
          700f.plus((-50).times(RareCardsSparkle.sparkleRules.size)).minus(25f),
          Color.WHITE.cpy(),
          FontHelper.buttonLabelFont,
          RareCardsSparkle.config.sparkleInCombat,
          settingsMenu,
          doNothingConsumer(),
          Consumer {
            RareCardsSparkle.config.sparkleInCombat = it.enabled
            RareCardsSparkle.config.save()
          }
        )
          .also {
            this.addUIElement(it)
          }
      }
      RareCardsSparkle.config.save()
    }

    private fun findCardByRule(rule: SparkleRule): AbstractCard {
      val filteredCards = CardLibrary.getAllCards().stream()
        .filter { rule.test(it) }
        .distinct()
        .toList()

      return if (filteredCards.isNotEmpty()) {
        filteredCards[0].makeCopy()
      } else {
        Madness().makeCopy()
      }
    }
  }

  override fun receivePostInitialize() {
    val badge = ImageMaster.loadImage("com/blanktheevil/rarecardssparkle/images/badge.png")
    BaseMod.registerModBadge(badge, RareCardsSparkle.name, RareCardsSparkle.author, RareCardsSparkle.description, settingsMenu)

    RareCardsSparkle.addSparkleRule(
      RareCardsSparkle.makeID("RareCardsGoldSparkle"),
      "Rare",
      Color(1f, 0.85f, 0.4f, 0f),
      ImageMaster.ROOM_SHINE_2,
      false,
      SparkleTimer(0.22f, 0.245f),
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
      SparkleTimer(0.22f, 0.245f),
      Predicate<AbstractCard> {
        it.rarity == AbstractCard.CardRarity.SPECIAL
      }
    )
  }

  override fun receiveEditStrings() {
    loadLocalizationFiles(Settings.GameLanguage.ENG)
    if (Settings.language != Settings.GameLanguage.ENG) {
      loadLocalizationFiles(Settings.language)
    }
  }

  private fun loadLocalizationFiles(language: Settings.GameLanguage) {
    BaseMod.loadCustomStringsFile(UIStrings::class.java, makeLocalizationPath(language, "settings"))
  }

  private fun makeLocalizationPath(language: Settings.GameLanguage, fileName: String): String {
    var langFolder = "com/blanktheevil/rarecardssparkle/localization/"

    @Suppress("LiftReturnOrAssignment")
    when (language) {
      Settings.GameLanguage.ENG -> langFolder += "eng"
      else -> langFolder += "eng"
    }

    return "$langFolder/$fileName.json"
  }
}