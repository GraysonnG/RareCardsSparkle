package com.blanktheevil.rarecardssparkle

import basemod.interfaces.PostInitializeSubscriber
import com.badlogic.gdx.graphics.Color
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.helpers.ImageMaster
import java.util.function.Predicate

class RareCardsSparkleInit : PostInitializeSubscriber {
  override fun receivePostInitialize() {
    RareCardsSparkle.addSparkleRule(
      RareCardsSparkle.makeID("RareCardsGoldSparkle"),
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
      Color(0.6f, 0.7f, 1f, 0f),
      ImageMaster.GLOW_SPARK_2,
      true,
      SparkleTimer(0.15f, 0.175f),
      Predicate<AbstractCard> {
        it.rarity == AbstractCard.CardRarity.SPECIAL
      }
    )
    // load sparkle rule setting from json file
    // override any existing rule settings with file settings
    // load the badge

    // build out menu

    // register the mod badge and settings menu
  }
}