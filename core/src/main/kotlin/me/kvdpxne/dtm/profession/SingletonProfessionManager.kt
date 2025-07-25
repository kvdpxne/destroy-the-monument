package me.kvdpxne.dtm.profession

import java.util.concurrent.ConcurrentHashMap

/**
 * @since 0.1.0
 */
private val PROFESSIONS: MutableMap<String, Profession> by lazy {
  ConcurrentHashMap()
}

/**
 * @since 0.1.0
 */
object SingletonProfessionManager : ProfessionManager {




  override fun getRandomProfession(): Profession {
    return PROFESSIONS.values.random()
  }

  override fun findProfessionByNameOrNull(name: String): Profession? {
    return PROFESSIONS[name.lowercase()]
  }


}