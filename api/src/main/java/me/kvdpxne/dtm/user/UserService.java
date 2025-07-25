package me.kvdpxne.dtm.user;

import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

/**
 * @since 0.1.0
 */
public interface UserService {

  /**
   * @since 0.1.0
   */
  @Nullable
  User findUserByIdentifierOrNull(
    @NotNull UUID identifier
  );

  /**
   * @since 0.1.0
   */
  @Nullable
  User findUserByNameOrNull(
    @NotNull String name
  );

  /**
   * @throws NullPointerException
   * @throws IllegalStateException
   * @throws UserDuplicationException
   *
   *
   * @since 0.1.0
   */
  @NotNull
  User createUser(
    @NotNull User user,
    boolean ignoreNew
  );

  /**
   * @throws NullPointerException
   * @throws IllegalStateException
   * @throws UserNotFoundException
   *
   * @since 0.1.0
   */
  @NotNull
  User updateUser(
    @NotNull User user
  );

  /**
   * @since 0.1.0
   */
  boolean deleteUser(
    @NotNull User user
  );

  /**
   * @since 0.1.0
   */
  boolean deleteUserByIdentifier(
    @NotNull UUID identifier
  );

  /**
   * @since 0.1.0
   */
  boolean deleteUserByName(
    @NotNull String name
  );

  /**
   * @since 0.1.0
   */
  @Range(from = 0, to = Long.MAX_VALUE)
  long countUsers();
}
