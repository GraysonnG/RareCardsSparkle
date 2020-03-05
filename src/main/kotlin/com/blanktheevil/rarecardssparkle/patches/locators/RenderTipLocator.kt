package com.blanktheevil.rarecardssparkle.patches.locators

import com.evacipated.cardcrawl.modthespire.lib.LineFinder
import com.evacipated.cardcrawl.modthespire.lib.Matcher
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator
import com.megacrit.cardcrawl.cards.CardGroup
import javassist.CtBehavior

@Suppress("unused")
class RenderTipLocator : SpireInsertLocator() {
  override fun Locate(ctBehavior: CtBehavior?): IntArray {
    val matcher = Matcher.MethodCallMatcher(CardGroup::class.java, "renderTip")
    return LineFinder.findAllInOrder(ctBehavior, matcher)
  }
}