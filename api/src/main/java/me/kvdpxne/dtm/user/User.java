package me.kvdpxne.dtm.user;

import java.util.UUID;
import me.kvdpxne.boujee.locale.LocaleSource;
import me.kvdpxne.boujee.locale.LocaleSourceProvider;
import me.kvdpxne.boujee.receiver.Receiver;
import me.kvdpxne.dtm.capabilities.Nameable;
import me.kvdpxne.dtm.capabilities.Persistable;
import me.kvdpxne.dtm.capabilities.Rebuildable;
import me.kvdpxne.dtm.profession.Profession;
import me.kvdpxne.dtm.user.statistics.UserStatistics;
import me.kvdpxne.dtm.wallet.Wallet;
import org.jetbrains.annotations.NotNull;

public interface User
  extends
  Persistable<UUID>,
  Receiver,
  Rebuildable<User, UserBuilder>,
  Nameable,
  LocalUserProvider {

  /**
   * @since 0.1.0
   */
  @NotNull
  @Override
  UUID getIdentifier();

  /**
   * @since 0.1.0
   */
  @NotNull
  @Override
  String getName();

  /**
   * @since 0.1.0
   */
  @NotNull
  UserStatistics getStatistics();

  /**
   * @since 0.1.0
   */
  @NotNull
  Wallet getWallet();

  /**
   * @since 0.1.0
   */
  @NotNull
  String getProfessionName();

  /**
   * @since 0.1.0
   */
  @NotNull
  Profession getProfession();

  /**
   * @since 0.1.0
   */
  @NotNull
  LocaleSource getLocaleSource();

  /**
   * @since 0.1.0
   */
  boolean updateLocaleSource(
    @NotNull final LocaleSourceProvider localeSourceProvider
  );

  /**
   * @since 0.1.0
   */
  boolean updateLocaleSource(
    @NotNull final String localization
  );

  /**
   * @since 0.1.0
   */
  @Override
  @NotNull
  LocalUser getLocalUser();
}
