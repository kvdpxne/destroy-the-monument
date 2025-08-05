package me.kvdpxne.dtm.placeholder

object Placeholders {

  const val INDEX: String = "INDEX"

  const val PROFESSION_NAME: String = "PROFESSION_NAME"

  const val CURRENT_PROFESSION_NAME: String = "CURRENT_${this.PROFESSION_NAME}"

  const val MEMBER_COUNT: String = "MEMBER_COUNT"
  const val MEMBER_LIMIT: String = "MEMBER_LIMIT"

  const val GAME_UID: String = "GAME_UID"
  const val GAME_NAME: String = "GAME_NAME"

  const val CURRENT_ARENA: String = "CURRENT_ARENA"
  const val USER_NAME: String = "USER_NAME"

  const val TEAM_NAME: String = "TEAM_NAME"
  const val TEAM_SIZE: String = "TEAM_SIZE"
  const val TEAM_SIZE_LIMIT: String = "TEAM_SIZE_LIMIT"
}