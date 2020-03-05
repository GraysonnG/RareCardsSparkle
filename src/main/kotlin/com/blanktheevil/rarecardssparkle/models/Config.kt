package com.blanktheevil.rarecardssparkle.models

import com.blanktheevil.rarecardssparkle.RareCardsSparkle
import com.blanktheevil.rarecardssparkle.SparkleRuleDefinition
import com.evacipated.cardcrawl.modthespire.lib.ConfigUtils
import com.google.gson.Gson
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import kotlin.streams.toList

class Config(
  var sparkleRules: List<SparkleRuleDefinition>,
  var sparkleInCombat: Boolean) {

  companion object {
    val dirPath = ConfigUtils.CONFIG_DIR + File.separator + RareCardsSparkle.modid + File.separator + "config.json"

    @JvmStatic
    fun init(): Config {
      val file = File(dirPath)

      if (!file.exists()) {
        file.parentFile.mkdirs()
        file.createNewFile()
        return Config(emptyList(), true)
      }

      return load()
    }

    @JvmStatic
    fun save(config: Config) {
      val file = File(dirPath)

      config.sparkleRules = RareCardsSparkle.sparkleRules.values.stream()
        .map { it.asSparkleRuleDefinition() }
        .toList()

      try {
        with(FileWriter(file)) {
          write(Gson().toJson(config))
          close()
        }
      } catch (e: IOException) {
        e.printStackTrace()
      }
    }

    @JvmStatic
    fun load(): Config {
      val file = File(dirPath)

      try {
        with(FileReader(file)) {
          return (Gson().fromJson(this, Config::class.java) ?: Config(emptyList(), true))
        }
      } catch (e: Exception) {
        e.printStackTrace()
      }

      return Config(emptyList(), true)
    }
  }
}