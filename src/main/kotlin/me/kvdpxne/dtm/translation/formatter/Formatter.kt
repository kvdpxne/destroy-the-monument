package me.kvdpxne.dtm.translation.formatter

class Formatter private constructor(
  val replaceable: MutableMap<String, String>
) {

  companion object {

    fun begin(initialCapacity: Int = 6): Formatter {
      return Formatter(HashMap(initialCapacity))
    }
  }

  fun with(field: String, value: Any): Formatter {
    this.replaceable["{$field}"] = value.toString()
    return this
  }
}