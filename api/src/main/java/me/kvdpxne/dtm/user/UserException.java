package me.kvdpxne.dtm.user;

import me.kvdpxne.dtm.CodecException;

public class UserException extends CodecException {
  private static final long serialVersionUID = 5914294232811266064L;

  public UserException(String message, String code) {
    super(message, code);
  }

  public UserException(String message, Throwable cause, String code) {
    super(message, cause, code);
  }
}
