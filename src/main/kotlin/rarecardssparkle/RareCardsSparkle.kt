package rarecardssparkle

import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
import java.io.IOException
import java.util.*

@Suppress("unused")
@SpireInitializer
class RareCardsSparkle {
    companion object Statics {
        private var name: String = ""
        private var version: String = ""

        @JvmStatic
        fun initialize() {
            loadProjectProperties()
            log("Version", version)
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