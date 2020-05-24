package com.blanktheevil.rarecardssparkle.patches

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.blanktheevil.rarecardssparkle.RareCardsSparkle
import com.blanktheevil.rarecardssparkle.extensions.fixAlpha
import com.blanktheevil.rarecardssparkle.extensions.normalMode
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.rooms.AbstractRoom

@Suppress("unused")
@SpirePatch(clz = AbstractCard::class, method = "renderCard")
object RareCardsSparkleRenderPatch {
  @SpirePostfixPatch
  @JvmStatic
  fun renderSparkles(card: AbstractCard, sb: SpriteBatch) {
    if (AbstractDungeon.getCurrMapNode() != null && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !RareCardsSparkle.config.sparkleInCombat) return

    RareCardsSparkle.menuSparkles.asSequence().filter {
      it.card == card
    }.forEach {
      it.render(sb)
    }

    with(sb) {
      color = Color.WHITE.fixAlpha()
      normalMode()
    }
  }
}
