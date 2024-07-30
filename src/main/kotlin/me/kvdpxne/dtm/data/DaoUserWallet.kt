package me.kvdpxne.dtm.data

import me.kvdpxne.dtm.data.source.database
import me.kvdpxne.dtm.data.tables.TableUserWallet
import me.kvdpxne.dtm.user.Wallet
import org.ktorm.dsl.QueryRowSet
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.map
import org.ktorm.dsl.select
import org.ktorm.dsl.update
import org.ktorm.dsl.where

/**
 * @since 0.1.0
 */
object DaoUserWallet {

  /**
   * @since 0.1.0
   */
  private fun toUserWallet(
    row: QueryRowSet
  ): Wallet {
    //
    val identifier = row[TableUserWallet.identifier]!!

    //
    val coins = row[TableUserWallet.coins]!!
    val multiplier = row[TableUserWallet.multiplier]!!

    //
    return Wallet(
      coins,
      multiplier,
      identifier
    )
  }

  /**
   * @since 0.1.0
   */
  fun findUserWalletByIdentifierOrNull(
    identifier: String
  ): Wallet? {
    return database.from(TableUserWallet)
      .select()
      .where {
        TableUserWallet.identifier eq identifier
      }
      .map {
        toUserWallet(it)
      }
      .firstOrNull()
  }

  /**
   * @since 0.1.0
   */
  fun insertUserWallet(
    wallet: Wallet
  ) {
    database.insert(TableUserWallet) {
      set(it.identifier, wallet.identifier)
      set(it.coins, wallet.coins)
      set(it.multiplier, wallet.multiplier)
    }
  }

  /**
   * @since 0.1.0
   */
  fun updateUserWallet(
    wallet: Wallet
  ) {
    database.update(TableUserWallet) {
      set(it.coins, wallet.coins)
      set(it.multiplier, wallet.multiplier)

      where {
        it.identifier eq wallet.identifier
      }
    }
  }
}