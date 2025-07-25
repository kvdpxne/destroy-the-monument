package me.kvdpxne.dtm.arena.voting;

import java.util.Collection;
import me.kvdpxne.dtm.capabilities.Identifiable;
import me.kvdpxne.dtm.arena.Arena;
import me.kvdpxne.dtm.user.LocalUser;
import me.kvdpxne.dtm.user.LocalUserProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import org.jetbrains.annotations.Unmodifiable;

public interface ArenaVoting
  extends
  Identifiable<Integer> {

  @Override
  @NotNull
  Integer getIdentifier();

  @NotNull
  Arena getArena();

  @Unmodifiable
  @NotNull
  Collection<LocalUser> getVoters();

  @Range(from = 0, to = Integer.MAX_VALUE)
  int getVotes();

  boolean hasVote(
    final @Nullable LocalUserProvider userProvider
    );

  boolean castVote(
    final @Nullable LocalUserProvider userProvider
  );

  boolean revokeVote(
    final @Nullable LocalUserProvider userProvider
  );

  void clearVotes();
}
