package me.kvdpxne.dtm.shared

import java.util.UUID

interface Identity {

  val identifier: UUID

  val key: String
}