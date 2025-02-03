package me.kvdpxne.dtm.user;

import java.util.Map;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import org.jetbrains.annotations.Unmodifiable;

/**
 * @since 0.1.0
 */
public interface LocalUserManager {

  /**
   * @since 0.1.0
   */
  @Unmodifiable
  Map<UUID, LocalUser> getLocalUsersByIdentifier();

  /**
   * @since 0.1.0
   */
  @Unmodifiable
  Map<String, LocalUser> getLocalUsersByName();

  /**
   * @since 0.1.0
   */
  @NotNull
  Iterable<UUID> getLocalUserIdentifiers();

  /**
   * @since 0.1.0
   */
  @NotNull
  Iterable<String> getLocalUserNames();

  /**
   * @throws NullPointerException
   * @since 0.1.0
   */
  @Nullable
  LocalUser findUserByIdentifierOrNull(
    final @NotNull UUID identifier
  );

  /**
   * @throws NullPointerException
   * @throws UserNoFoundException
   *
   * @since 0.1.0
   */
  @NotNull
  LocalUser findUserByIdentifier(
    final @NotNull UUID identifier
  );

  /**
   * @throws NullPointerException
   * @throws IllegalArgumentException
   *
   * @since 0.1.0
   */
  @Nullable
  LocalUser findUserByNameOrNull(
    final @NotNull String name
  );

  /**
   * @throws NullPointerException
   * @throws IllegalArgumentException
   * @throws UserNoFoundException
   *
   * @since 0.1.0
   */
  @NotNull
  LocalUser findUserByName(
    final @NotNull String name
  );

  /**
   * @since 0.1.0
   */
  boolean addLocalUser(
    final @NotNull LocalUserProvider localUserProvider
  );

  /**
   * @since 0.1.0
   */
  boolean removeLocalUser(
    final @NotNull LocalUser localUser
  );

  /**
   * @since 0.1.0
   */
  boolean removeLocalUserByIdentifier(
    final @NotNull UUID identifier
  );

  /**
   * @since 0.1.0
   */
  boolean removeLocalUserByName(
    final @NotNull String name
  );

  /**
   * @since 0.1.0
   */
  @Range(from = 0, to = Integer.MAX_VALUE)
  int getNumberOfLocalUsersByIdentifier();

  /**
   * @since 0.1.0
   */
  @Range(from = 0, to = Integer.MAX_VALUE)
  int getNumberOfLocalUsersByName();

  /**
   * @since 0.1.0
   */
  void clearLocalUsers();
}
