package me.kvdpxne.dtm.arena;

import me.kvdpxne.dtm.CodecException;

public class ArenaException extends CodecException {

  public ArenaException(String message, String code) {
    super(message, code);
  }

  public ArenaException(String message, Throwable cause, String code) {
    super(message, cause, code);
  }
}
