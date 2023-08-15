package me.kvdpxne.dtm.profession

object ProfessionManager : Iterable<Profession> {

  /**
   *
   */
  var professions = mutableMapOf<String, Profession>()
    private set


  fun getRandomProfession() = professions.values.random()

  fun hasProfession(name: String) = professions.contains(name)

  fun findProfessionByName(name: String) = professions[name]

  fun addProfession(profession: Profession): Boolean {
    val name = profession.name
    if (hasProfession(name)) {
      return false
    }

    professions[name] = profession
    return true
  }

  /**
   *
   */
  fun initializeBuiltInProfessions() {
    arrayOf(
      archer(),
      scout()
    ).forEach { addProfession(it) }
  }


  /**
   * Returns an iterator over the elements of this object.
   */
  override fun iterator() = professions.values.iterator()

  override fun toString(): String {
    return "ProfessionManager(professions=$professions)"
  }
}