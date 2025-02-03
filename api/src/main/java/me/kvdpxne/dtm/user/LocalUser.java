package me.kvdpxne.dtm.user;

import java.util.UUID;
import me.kvdpxne.boujee.TranslationKeyProvider;
import me.kvdpxne.boujee.locale.LocaleSource;
import me.kvdpxne.boujee.locale.LocaleSourceProvider;
import me.kvdpxne.dtm.profession.Profession;
import me.kvdpxne.dtm.translation.Communicable;
import me.kvdpxne.dtm.user.cache.UserCache;
import me.kvdpxne.dtm.user.contractor.UserContractor;
import me.kvdpxne.dtm.user.statistics.UserStatistics;
import me.kvdpxne.dtm.wallet.Wallet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @since 0.1.0
 */
public interface LocalUser
  extends
  Communicable,
  User {

  /**
   * @since 0.1.0
   */
  @Override
  @NotNull
  UUID getIdentifier();

  /**
   * @since 0.1.0
   */
  @Override
  @NotNull
  String getName();

  /**
   * @since 0.1.0
   */
  @Override
  @Nullable
  String getDisplayName();

  /**
   * @since 0.1.0
   */
  @Override
  @NotNull
  UserStatistics getStatistics();

  /**
   * @since 0.1.0
   */
  @Override
  @NotNull
  Wallet getWallet();

  /**
   * @since 0.1.0
   */
  @Override
  @NotNull
  Profession getCurrentProfession();

  /**
   * @since 0.1.0
   */
  void setCurrentProfession(
    final @NotNull Profession profession
  );

  @NotNull
  UserCache getCache();

  /**
   * @since 0.1.0
   */
  @NotNull
  UserContractor getContractor();

  /**
   * @since 0.1.0
   */
  @Override
  @NotNull
  LocaleSource getLocaleSource();

  /**
   * @since 0.1.0
   */
  @Override
  boolean updateLocaleSource(
    final @NotNull LocaleSourceProvider localeSourceProvider
  );

  /**
   * @since 0.1.0
   */
  @Override
  boolean updateLocaleSource(
    final @NotNull String localization
  );

  @Override
  void chat(
    final @NotNull TranslationKeyProvider keyProvider
  );

  @Override
  void title(
    final @NotNull TranslationKeyProvider keyProvider
  );

  @Override
  void subtitle(
    final @NotNull TranslationKeyProvider keyProvider
  );

  @Override
  void action(
    final @NotNull TranslationKeyProvider keyProvider
  );
}
