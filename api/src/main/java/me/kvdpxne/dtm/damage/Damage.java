package me.kvdpxne.dtm.damage;

import java.util.UUID;
import me.kvdpxne.dtm.teammate.Teammate;

public interface Damage {

  Teammate getAttacker();

  UUID getAttackerIdentifier();

  double getDamage();

  long getLastDamageTime();
}
