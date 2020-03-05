package com.blanktheevil.rarecardssparkle

class SparkleRuleDefinition(val id: String, val min: Float, val max: Float, val enabled: Boolean) {
  constructor(id: String, min: Float, max: Float) : this(id, min, max, true)
}