package me.kvdpxne.dtm.shared.ancillary

import java.time.LocalDateTime

interface Auditable {

  val creationDate: LocalDateTime

  val lastModificationDate: LocalDateTime?
}