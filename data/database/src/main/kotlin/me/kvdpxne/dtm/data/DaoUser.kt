package me.kvdpxne.dtm.data

import java.util.Locale
import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawUser
import me.kvdpxne.dtm.data.extensions.UPDATE_LIMIT
import me.kvdpxne.dtm.data.repositories.RepositoryUser
import me.kvdpxne.dtm.data.sources.DatabasesConfiguration
import me.kvdpxne.dtm.data.tables.TableUser
import me.kvdpxne.dtm.data.tables.TableUserStatistics
import me.kvdpxne.dtm.data.tables.TableUserWallet
import me.kvdpxne.dtm.data.transactions.concurrentTransaction
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.innerJoin
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.statements.UpdateStatement
import org.jetbrains.exposed.sql.update

object DaoUser : RepositoryUser {

  /**
   * @since 0.1.0
   */
  private val OP_COLUMNS: List<Column<*>> = arrayListOf(
    TableUser.identifier,
    TableUser.statisticsIdentifier,
    TableUser.walletIdentifier,
    TableUser.name,
    TableUser.displayName,
    TableUser.profession,
    TableUser.locale,
    TableUserStatistics.kills,
    TableUserStatistics.assists,
    TableUserStatistics.deaths,
    TableUserStatistics.destroyedMonuments,
    TableUserStatistics.playedGames,
    TableUserStatistics.gamesWon,
    TableUserStatistics.gamesLost,
    TableUserWallet.coins,
    TableUserWallet.multiplier,
    TableUserWallet.infinite,
    TableUserWallet.locked
  )

  private fun ResultRow.toUser(): RawUser {
    // Podstawowy klucz tabeli użytkownika.
    val identifier: UUID = this[TableUser.identifier]

    //
    val statisticsIdentifier: UUID = this[TableUser.statisticsIdentifier]
    val walletIdentifier: UUID = this[TableUser.walletIdentifier]

    //
    val kills: Int = this[TableUserStatistics.kills]
    val assists: Int = this[TableUserStatistics.assists]
    val deaths: Int = this[TableUserStatistics.deaths]
    val destroyedMonuments: Int = this[TableUserStatistics.destroyedMonuments]
    val playedGames: Int = this[TableUserStatistics.playedGames]
    val gamesWon: Int = this[TableUserStatistics.gamesWon]
    val gamesLost: Int = this[TableUserStatistics.gamesLost]



    //
    val name: String = this[TableUser.name]
    val displayName: String? = this[TableUser.displayName]

    val professionName: String = this[TableUser.profession]
//    val locale: Locale = this[TableUser.locale]?.let { Locales.fromString(it) } ?: TranslationService.defaultLocale

    error("")
//    return RawUser(
//      identifier.toString(),
//      RawUserStatistics(
//        statisticsIdentifier.toString(),
//        kills,
//        assists,
//        deaths,
//        destroyedMonuments,
//        playedGames,
//        gamesWon,
//        gamesLost
//      ),
//      this.toRawUserWallet(walletIdentifier),
//      name,
//      name,
//      professionName
//    )
  }

  /**
   * @param userStatistics
   *
   * @since 0.1.0
   */
  private fun where(
    user: RawUser
  ): SqlExpressionBuilder.() -> Op<Boolean> {
    return {
      TableUserStatistics.identifier eq user.identifier
    }
  }

  /**
   * @param user
   * @param builder
   *
   * @since 0.1.0
   */
  private fun fillUpdateStatement(
    user: RawUser,
    builder: UpdateBuilder<Int>
  ) {
    TableUser.run {
      builder[this.name] = user.name
      builder[this.displayName] = user.name
      builder[this.profession] = user.profession
      builder[this.locale] = user.locale
    }
  }

  /**
   * @param user
   * @param builder
   *
   * @since 0.1.0
   */
  private fun fillInsertStatement(
    user: RawUser,
    builder: UpdateBuilder<Int>
  ) {
    builder[TableUser.identifier] = user.identifier

    this.fillUpdateStatement(user, builder)
  }

  override suspend fun readUsers(): Collection<RawUser> {
    return concurrentTransaction {
      TableUser
        .innerJoin(TableUserStatistics)
        .innerJoin(TableUserWallet)
        .select(this@DaoUser.OP_COLUMNS)
        .map { row: ResultRow ->
          row.toUser()
        }
        .toList()
    }
  }

  override suspend fun readUserIdentifiers(): Collection<UUID> {
    return concurrentTransaction {
      TableUser
        .select(TableUser.identifier)
        .map { row: ResultRow ->
          row[TableUser.identifier]
        }
        .toList()
    }
  }

  override suspend fun readUserNames(): Collection<String> {
    return concurrentTransaction {
      TableUser
        .select(TableUser.name)
        .map { row: ResultRow ->
          row[TableUser.name]
        }
        .toList()
    }
  }

  /**
   * @param predicate
   *
   * @since 0.1.0
   */
  private suspend fun findUserBy(
    predicate: SqlExpressionBuilder.() -> Op<Boolean>
  ): RawUser? {
    return concurrentTransaction(DatabasesConfiguration.main) {
      TableUser
        .innerJoin(TableUserStatistics)
        .innerJoin(TableUserWallet)
        .select(this@DaoUser.OP_COLUMNS)
        .where(predicate)
        .firstNotNullOfOrNull { row: ResultRow ->
          row.toUser()
        }
    }
  }

  override suspend fun findUserByIdentifierOrNull(
    identifier: UUID
  ): RawUser? {
    return this.findUserBy {
      TableUser.identifier eq identifier
    }
  }

  override suspend fun findUserByNameOrNull(
    name: String
  ): RawUser? {
    return this.findUserBy {
      TableUser.name.lowerCase() eq name.lowercase(Locale.US)
    }
  }

  override suspend fun insertUsers(users: Iterable<RawUser>) {
    TODO("Not yet implemented")
  }

  override suspend fun insertUser(
    user: RawUser
  ) {
    DaoUserStatistics.insertUserStatistics(user.statistics)
    me.kvdpxne.dtm.data.DaoUserWallet.insertUserWallet(user.wallet)

    concurrentTransaction {
      TableUser.insert {
        this@DaoUser.fillInsertStatement(user, it)
      }
    }
  }

  override suspend fun updateUsers(users: Iterable<RawUser>): Int {
    TODO("Not yet implemented")
  }

  override suspend fun updateUser(
    user: RawUser
  ) {
    DaoUserStatistics.updateUserStatistics(user.statistics)
    DaoUserWallet.updateUserWallet(user.wallet)

    concurrentTransaction {
      TableUser.update(
        this@DaoUser.where(user),
        UPDATE_LIMIT
      ) { statement: UpdateStatement ->
        this@DaoUser.fillUpdateStatement(user, statement)
      }
    }
  }

  override suspend fun deleteUser(user: RawUser): Boolean {
    TODO("Not yet implemented")
  }

  override suspend fun deleteUsers(): Int {
    return concurrentTransaction {
      TableUser.deleteAll()
    }
  }

  override suspend fun countUsers(): Long {
    return concurrentTransaction {
      TableUser.selectAll().count()
    }
  }
}