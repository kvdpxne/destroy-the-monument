package me.kvdpxne.dtm.user;

import java.util.UUID;
import me.kvdpxne.boujee.locale.LocaleSource;
import me.kvdpxne.boujee.locale.LocaleSourceProvider;
import me.kvdpxne.boujee.receiver.Receiver;
import me.kvdpxne.dtm.DisplayableNameable;
import me.kvdpxne.dtm.Identifiable;
import me.kvdpxne.dtm.profession.Profession;
import me.kvdpxne.dtm.user.statistics.UserStatistics;
import me.kvdpxne.dtm.wallet.Wallet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface User
  extends
  Identifiable<UUID>,
  Receiver,
  DisplayableNameable,
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
  @Nullable
  @Override
  String getDisplayName();

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
  Profession getCurrentProfession();

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
