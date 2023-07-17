package me.kvdpxne.dtm.command

class Parameter(private val arguments: Array<out String>) {

  fun asText(index: Int = 0): String {
    return arguments[index]
  }

  fun length(): Int {
    return arguments.size
  }

  fun isEmpty(): Boolean {
    return 0 == length()
  }
}