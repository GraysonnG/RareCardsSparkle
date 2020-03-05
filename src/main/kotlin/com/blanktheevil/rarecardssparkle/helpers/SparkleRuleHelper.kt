package com.blanktheevil.rarecardssparkle.helpers

import com.blanktheevil.rarecardssparkle.RareCardsSparkle
import com.blanktheevil.rarecardssparkle.SparkleRuleDefinition
import com.evacipated.cardcrawl.modthespire.lib.ConfigUtils
import com.google.gson.Gson
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import kotlin.streams.toList

class SparkleRuleHelper {

  companion object {
    val dirPath = ConfigUtils.CONFIG_DIR + File.separator + RareCardsSparkle.modid + File.separator + "config.json"

    fun saveRulesAsJson() {
      val file = File(dirPath)

      try {
        with(FileWriter(file)) {
          write(Gson().toJson(
            RareCardsSparkle.sparkleRules.values.stream()
              .map { it.asSparkleRuleDefinition() }
              .toList()
          ))
          close()
        }
      } catch (e: IOException) {
        e.printStackTrace()
      }
    }

    fun loadRulesFromJson(): List<SparkleRuleDefinition> {
      val file = File(dirPath)

      try {
        with(FileReader(file)) {
          return (Gson().fromJson(this, Array<SparkleRuleDefinition>::class.java) ?: emptyArray()).asList()
        }
      } catch (e: Exception) {
        e.printStackTrace()
      }

      return emptyList()
    }

    @JvmStatic
    fun preCheckConfigFile() {
      val file = File(dirPath)

      if (!file.exists()) {
        file.parentFile.mkdirs()
        file.createNewFile()
      }
    }
  }
}