package me.kvdpxne.dtm.arena;

public class ArenaNoFoundException
  extends ArenaException {

  private static final long serialVersionUID = -180212178502037131L;

  public ArenaNoFoundException(String message, String code) {
    super(message, code);
  }

  public ArenaNoFoundException(String message, Throwable cause, String code) {
    super(message, cause, code);
  }
}
