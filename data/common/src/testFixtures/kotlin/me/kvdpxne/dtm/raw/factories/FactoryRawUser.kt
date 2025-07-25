package me.kvdpxne.dtm.raw.factories

import java.util.UUID
import me.kvdpxne.dtm.data.raw.RawUser
import me.kvdpxne.dtm.data.raw.RawUserStatistics
import me.kvdpxne.dtm.data.raw.RawUserWallet
import me.kvdpxne.dtm.raw.NAMES_OF_USERS
import me.kvdpxne.dtm.shared.uniqueString
import me.kvdpxne.dtm.shared.uniqueUuid

/**
 * @param previousIdentifier
 * @param identifier
 * @param statistics
 * @param wallet
 * @param name
 * @param displayName
 * @param profession
 * @param locale
 *
 * @since 0.1.0
 */
fun makeRawUser(
  // @formatter:off
  previousIdentifier: UUID?             = null,
  previousName      : String?           = null,
  identifier        : UUID              = uniqueUuid(previousIdentifier),
  name              : String            = uniqueString(previousName, NAMES_OF_USERS),
  statistics        : RawUserStatistics = makeRawUserStatistics(),
  wallet            : RawUserWallet     = makeRawUserWallet(),
  profession        : String            = "scout",
  locale            : String            = "pl_PL"
  // @formatter:on
) = RawUser(
  identifier,
  statistics,
  wallet,
  name,
  profession,
  locale
)