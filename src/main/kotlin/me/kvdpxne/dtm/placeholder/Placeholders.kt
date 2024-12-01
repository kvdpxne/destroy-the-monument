package me.kvdpxne.dtm.placeholder

object Placeholders {

  const val PROFESSION_NAME: String = "PROFESSION_NAME"

  const val CURRENT_PROFESSION_NAME: String = "CURRENT_${this.PROFESSION_NAME}"
}