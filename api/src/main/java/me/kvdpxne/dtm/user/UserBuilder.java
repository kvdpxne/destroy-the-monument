package me.kvdpxne.dtm.user;

import java.util.UUID;
import me.kvdpxne.dtm.capabilities.Buildable;
import me.kvdpxne.dtm.user.statistics.UserStatistics;
import me.kvdpxne.dtm.wallet.Wallet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.NotNullByDefault;

@NotNullByDefault
public interface UserBuilder
  extends
  Buildable<User> {

  UserBuilder setIdentifier(
    @NotNull UUID identifier
  );

  UserBuilder setName(
    @NotNull String name
  );

  UserBuilder setStatistics(
    @NotNull UserStatistics statistics
  );

  UserBuilder setWallet(
    @NotNull Wallet wallet
  );

  UserBuilder setProfession(
    @NotNull String profession
  );

  @Override
  @NotNull
  User build();
}
