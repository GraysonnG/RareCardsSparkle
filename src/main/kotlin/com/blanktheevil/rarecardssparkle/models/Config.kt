package com.blanktheevil.rarecardssparkle.models

import com.blanktheevil.rarecardssparkle.RareCardsSparkle
import com.blanktheevil.rarecardssparkle.SparkleRuleDefinition
import com.blanktheevil.rarecardssparkle.helpers.GZIPHelper
import com.evacipated.cardcrawl.modthespire.lib.ConfigUtils
import com.google.gson.Gson
import java.io.File
import java.io.IOException
import kotlin.streams.toList

class Config(
  var sparkleRules: List<SparkleRuleDefinition>,
  var sparkleInCombat: Boolean,
  var allowEasterEggs: Boolean = true) {

  fun save() {
    val file = File(dirPath)

    RareCardsSparkle.config.sparkleRules = RareCardsSparkle.sparkleRules.values.stream()
      .map { it.asSparkleRuleDefinition() }
      .toList()

    try {
      GZIPHelper.saveDataToFile(file, Gson().toJson(this))
    } catch (e: IOException) {
      e.printStackTrace()
    }
  }

  companion object {
    val dirPath = ConfigUtils.CONFIG_DIR + File.separator + RareCardsSparkle.modid + File.separator + "config.gz"

    @JvmStatic
    fun init(): Config {
      val file = File(dirPath)

      if (!file.exists()) {
        file.parentFile.mkdirs()
        file.createNewFile()
        return Config(emptyList(), sparkleInCombat = true, allowEasterEggs = true)
      }

      return load()
    }

    @JvmStatic
    fun load(): Config {
      val file = File(dirPath)

      return try {
        Gson().fromJson(GZIPHelper.loadDataFromFile(file), Config::class.java) ?: Config(emptyList(), sparkleInCombat = true, allowEasterEggs = true)
      } catch (e: Exception) {
        e.printStackTrace()
        Config(emptyList(), sparkleInCombat = true, allowEasterEggs = true)
      }
    }
  }
}