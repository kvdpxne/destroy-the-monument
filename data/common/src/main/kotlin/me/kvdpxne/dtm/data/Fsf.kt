package me.kvdpxne.dtm.data

import me.kvdpxne.dtm.data.repositories.RepositoryArena
import me.kvdpxne.dtm.data.repositories.RepositoryArenaMap
import me.kvdpxne.dtm.data.repositories.RepositoryUser
import me.kvdpxne.dtm.data.repositories.RepositoryUserStatistics
import me.kvdpxne.dtm.data.repositories.RepositoryUserWallet

object Fsf {

  lateinit var repositoryArena: RepositoryArena
  lateinit var repositoryArenaMap: RepositoryArenaMap

  lateinit var repositoryUser: RepositoryUser
  lateinit var repositoryUserStatistics: RepositoryUserStatistics
  lateinit var repositoryUserWallet: RepositoryUserWallet
}