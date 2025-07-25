package me.kvdpxne.dtm.game;

import java.util.UUID;
import me.kvdpxne.dtm.capabilities.Identifiable;
import org.jetbrains.annotations.NotNull;

public interface GameSettings extends Identifiable<UUID> {

  @Override
  @NotNull
  UUID getIdentifier();

  int getMinimumNumberOfPlayers(); // minimalna liczba graczy aby gra mogla wystartowac

  int getMaximumNumberOfPlayers(); // maksymalna ilosc graczy ktorzy moga byc jednocznie w grze

  boolean isInfiniteNumberOfPlayersAllowed(); // czy jest dozowlona nieskonczna ilosc graczy w grze
}
