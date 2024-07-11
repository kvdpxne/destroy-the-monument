package me.kvdpxne.dtm.data

import me.kvdpxne.dtm.wallet.Wallet
import org.ktorm.dsl.QueryRowSet
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.map
import org.ktorm.dsl.select
import org.ktorm.dsl.update
import org.ktorm.dsl.where
import org.ktorm.schema.Table
import org.ktorm.schema.float
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object TableUserWallet : Table<Nothing>("user_wallet") {

  val identifier = varchar("identifier").primaryKey()
  val coins = int("coins")
  val multiplier = float("multiplier")
}

internal fun toUserWallet(
  row: QueryRowSet
): Wallet {
  val identifier = row[TableUserWallet.identifier]!!
  val coins = row[TableUserWallet.coins]!!
  val multiplier = row[TableUserWallet.multiplier]!!

  return Wallet(
    coins,
    multiplier,
    identifier
  )
}

fun findUserWalletByIdentifier(
  identifier: String
): Wallet? {
  return database.from(TableUserWallet)
    .select()
    .where { TableUserWallet.identifier eq identifier }
    .map { toUserWallet(it) }
    .firstOrNull()
}

fun updateUserWaller(
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

fun insertUserWallet(
  wallet: Wallet
) {
  database.insert(TableUserWallet) {
    set(it.coins, wallet.coins)
    set(it.multiplier, wallet.multiplier)

    set(it.identifier, wallet.identifier)
  }
}