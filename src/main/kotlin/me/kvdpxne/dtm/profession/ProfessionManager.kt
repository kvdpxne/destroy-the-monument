package me.kvdpxne.dtm.profession

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.UUID
import me.kvdpxne.dtm.professions.createArcher
import me.kvdpxne.dtm.professions.createAssassin
import me.kvdpxne.dtm.professions.createDefender
import me.kvdpxne.dtm.professions.createEngineer
import me.kvdpxne.dtm.professions.createKnight
import me.kvdpxne.dtm.professions.createMedic
import me.kvdpxne.dtm.professions.createPyro
import me.kvdpxne.dtm.professions.createScout
import me.kvdpxne.dtm.professions.createSpecialist

val professionMap: MutableMap<UUID, Profession> = mutableMapOf()

private val logger: KLogger = KotlinLogging.logger {}

object ProfessionManager : Iterable<Profession> {

  /**
   * Returns a random profession object.
   *
   * This function retrieves all professions from the `professionMap`
   * (assumed to be a map of some kind) and then returns a random element from
   * the collection of values.
   *
   * @return A random `Profession` object.
   */
  fun getRandomProfession(): Profession {
    return professionMap.values.random()
  }

  fun findByName(name: String, ignoreCase: Boolean = true): Profession? {
    return professionMap.values.find {
      it.name.equals(name, ignoreCase)
    }
  }

  fun addProfession(
    profession: Profession
  ) {
    val identifier = profession.identifier
    if (professionMap.contains(identifier)) {
      return
    }

    professionMap[identifier] = profession
    logger.debug {
      "The profession \"$profession\" has been added and assigned to the " +
        "identifier \"$identifier\"."
    }
  }

  fun addProfessions(
    vararg professions: Profession
  ) {
    professions.forEach {
      this.addProfession(it)
    }
  }

  /**
   *
   */
  fun addBuiltInProfessions() {
    this.addProfessions(
      createArcher(),
      createKnight(),
      createEngineer(),
      createScout(),
      createMedic(),
      createPyro(),
      createDefender(),
      createAssassin(),
      createSpecialist()
    )

    logger.info {
      "All built-in professions have been added"
    }
  }

  /**
   * Returns an iterator over the elements of this object.
   */
  override fun iterator(): Iterator<Profession> {
    return professionMap.values.iterator()
  }

  override fun toString(): String {
    return "ProfessionManager(professions=$professionMap)"
  }
}