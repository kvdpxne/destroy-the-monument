package me.kvdpxne.dtm.command

class Parameters(arguments: Array<out String>) {

  var arguments: Array<out String> = arguments
    private set

  fun length(): Int {
    return arguments.size
  }

  fun asParameter(from: Int = 1): Parameters {
    val size = length()
    if (from > size) {
      return this
    }
    return Parameters(arguments.copyOfRange(from, size))
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