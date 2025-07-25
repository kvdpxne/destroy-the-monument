package me.kvdpxne.dtm.user.contractor;

import java.util.UUID;
import me.kvdpxne.boujee.TranslationKeyProvider;
import me.kvdpxne.dtm.DisplayableNameable;
import me.kvdpxne.dtm.capabilities.Identifiable;
import me.kvdpxne.dtm.translation.Communicable;
import me.kvdpxne.dtm.user.LocalUser;
import me.kvdpxne.dtm.user.LocalUserProvider;
import me.kvdpxne.dtm.user.UserException;
import me.kvdpxne.dtm.user.UserInvalidNameException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface UserContractor
  extends
  Communicable,
  Identifiable<UUID>,
  DisplayableNameable,
  LocalUserProvider {

  @NotNull
  UUID getIdentifier();

  @NotNull
  String getName();

  @Nullable
  String getDisplayName();

  @NotNull
  LocalUser getLocalUser();

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

  /**
   * @throws UnsupportedOperationException
   * @throws UserInvalidNameException
   * @throws UserException
   *
   * @since 0.1.0
   */
  @Nullable
  Object getPlayerOrNull();

  /**
   * @throws UnsupportedOperationException
   * @throws UserInvalidNameException
   * @throws UserException
   *
   * @since 0.1.0
   */
  boolean isOnline();

  /**
   * @throws UnsupportedOperationException
   * @throws UserInvalidNameException
   * @throws UserException
   *
   * @since 0.1.0
   */
  boolean isOperator();

  /**
   * @param privilege
   * @param offline
   *
   * @throws UnsupportedOperationException
   * @throws IllegalArgumentException
   * @throws UserInvalidNameException
   * @throws UserException
   *
   * @since 0.1.0
   */
  byte hasPrivilegeAsOperator(
    final @Nullable String privilege,
    final boolean offline
  );

  /**
   * @param privilege
   * @param operator
   * @param offline
   *
   * @throws UnsupportedOperationException
   * @throws IllegalArgumentException
   *
   * @since 0.1.0
   */
  byte hasPrivilege(
    final @Nullable String privilege,
    final boolean operator,
    final boolean offline
  );
}
