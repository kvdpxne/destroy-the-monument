package me.kvdpxne.dtm.user.contractor;

import java.util.UUID;
import me.kvdpxne.dtm.DisplayableNameable;
import me.kvdpxne.dtm.Identifiable;
import me.kvdpxne.dtm.translation.Communicable;
import me.kvdpxne.dtm.user.LocalUserProvider;
import org.jetbrains.annotations.Nullable;

public interface UserContractor
  extends
  Communicable,
  Identifiable<UUID>,
  DisplayableNameable,
  LocalUserProvider {

  /**
   * @throws UnsupportedOperationException
   *
   * @since 0.1.0
   */
  @Nullable
  Object getPlayerOrNull();

  /**
   * @throws UnsupportedOperationException
   *
   * @since 0.1.0
   */
  boolean isOnline();

  /**
   * @throws UnsupportedOperationException
   *
   * @since 0.1.0
   */
  boolean isAdministrator();

  /**
   * @throws UnsupportedOperationException
   *
   * @since 0.1.0
   */
  boolean hasPrivilege(
    final @Nullable String privilege
  );
}
