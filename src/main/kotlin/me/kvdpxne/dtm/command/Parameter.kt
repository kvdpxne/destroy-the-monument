package me.kvdpxne.dtm.command

class Parameter(arguments: Array<out String>) {

  var arguments: Array<out String> = arguments
    private set

  fun length(): Int {
    return arguments.size
  }

  fun asParameter(from: Int = 1): Parameter {
    val size = length()
    if (from > size) {
      return this
    }
    return Parameter(arguments.copyOfRange(from, size))
  }

  /**
   * @throws ArrayIndexOutOfBoundsException
   */
  fun asText(index: Int = 0): String {
    return arguments[index]
  }

  fun isEmpty(): Boolean {
    return 0 == length()
  }
}