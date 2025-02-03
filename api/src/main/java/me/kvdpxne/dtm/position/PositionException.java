package me.kvdpxne.dtm.position;

import me.kvdpxne.dtm.CodecException;

public class PositionException extends CodecException {
  private static final long serialVersionUID = -1170148774646828859L;

  public PositionException(String message, String code) {
    super(message, code);
  }

  public PositionException(String message, Throwable cause, String code) {
    super(message, cause, code);
  }
}
