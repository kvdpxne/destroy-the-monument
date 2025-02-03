package me.kvdpxne.dtm.damage;

import java.util.UUID;

public interface Damage {

  UUID getAttackerIdentifier();

  double getDamage();

  long getLastDamageTime();
}
