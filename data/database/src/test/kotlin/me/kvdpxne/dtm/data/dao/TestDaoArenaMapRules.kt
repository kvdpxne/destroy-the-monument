package me.kvdpxne.dtm.data.dao

import me.kvdpxne.dtm.data.raw.RawArenaMapRules
import me.kvdpxne.dtm.raw.factories.makeRawArenaMapRules
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder

private val ARENA_MAP_RULES: RawArenaMapRules by lazy {
  makeRawArenaMapRules()
}

@Order(0)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestDaoArenaMapRules {
  
  @BeforeAll
  fun `prepare battlefield`() {
    println(ARENA_MAP_RULES.toStylishString().listed(2))
  }
}