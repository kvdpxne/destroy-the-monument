package me.kvdpxne.dtm.profession

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
import me.kvdpxne.dtm.shared.debug.Debug

object ProfessionManager : Iterable<Profession> {

  /**
   * @since 0.1.0
   */
  private val _professions: MutableMap<UUID, Profession> = mutableMapOf()

  /**
   * @since 0.1.0
   */
  val professions: List<Profession>
    get() = this._professions.values.toList()

  /**
   * Returns a random profession object.
   *
   * This function retrieves all professions from the `professionMap`
   * (assumed to be a map of some kind) and then returns a random element from
   * the collection of values.
   *
   * @return A random `Profession` object.
   * @since 0.1.0
   */
  val randomProfession: Profession
    get() = this._professions.values.filter { it.enabled }.random()

  /**
   * @param name
   * @param ignoreCase
   *
   * @return
   * @since 0.1.0
   */
  fun findProfessionByName(
    name: String,
    ignoreCase: Boolean = true
  ): Profession? {
    return this._professions.values.find {
      it.name.equals(name, ignoreCase)
    }
  }

  /**
   * @since 0.1.0
   */
  fun addProfession(
    profession: Profession
  ) {
    val identifier = profession.identifier
    if (this._professions.contains(identifier)) {
      return
    }

    this._professions[identifier] = profession
    Debug.log {
      "The new ${profession.name} profession has been successfully registered."
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
   * Returns an iterator over the elements of this object.
   */
  override fun iterator(): Iterator<Profession> {
    return this._professions.values.iterator()
  }
}