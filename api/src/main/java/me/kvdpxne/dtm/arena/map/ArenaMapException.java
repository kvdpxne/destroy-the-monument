package me.kvdpxne.dtm.arena.map;

import me.kvdpxne.dtm.arena.ArenaException;

public class ArenaMapException extends ArenaException {
  public ArenaMapException(String message, String code) {
    super(message, code);
  }

  public ArenaMapException(String message, Throwable cause, String code) {
    super(message, cause, code);
  }
}
