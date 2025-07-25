package me.kvdpxne.dtm.team.color

class BasicTeamColor(
  private val color: String
) : TeamColor {

  override fun getAsFullFormat(): String {
    return this.color
  }

  override fun getAsSimpleFormat(): String {
    return BUILTIN_COLORS[this.color] ?: throw Exception("$color not found")
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is BasicTeamColor) return false

    if (color != other.color) return false

    return true
  }

  override fun hashCode(): Int {
    return color.hashCode()
  }


}