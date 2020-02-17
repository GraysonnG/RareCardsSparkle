package rarecardssparkle

import basemod.interfaces.PostInitializeSubscriber
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.MathUtils
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.helpers.CardLibrary
import rarecardssparkle.extensions.log
import rarecardssparkle.patches.SparklePatches
import java.io.IOException
import java.util.*
import java.util.function.Predicate
import kotlin.collections.ArrayList

@Suppress("unused")
@SpireInitializer
class RareCardsSparkle {
    companion object Statics {
        val defaultSparkleColor = Color(1f, 0.7f, 0.4f, 0f)
        val sparkleRules: ArrayList<Predicate<AbstractCard>> = ArrayList()

        private var name: String = ""
        private var version: String = ""


        @JvmStatic
        fun initialize() {
            loadProjectProperties()
            log("Version", version)

            sparkleRules.add(
                Predicate<AbstractCard> {
                    it.rarity == AbstractCard.CardRarity.RARE
                }
            )
        }

        fun log(vararg items: String) {
            println(items.asList().joinToString(" : ", "$name "))
        }

        private fun loadProjectProperties() {
            try {
                val properties = Properties();
                properties.load(RareCardsSparkle.javaClass.getResourceAsStream("/META-INF/rare-cards-sparkle.prop"))
                name = properties.getProperty("name")
                version = properties.getProperty("version")
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

}