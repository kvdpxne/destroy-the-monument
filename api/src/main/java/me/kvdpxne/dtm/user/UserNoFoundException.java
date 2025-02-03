package me.kvdpxne.dtm.user;

public class UserNoFoundException extends UserException {
  private static final long serialVersionUID = -2618818579969403291L;

  public UserNoFoundException(final String message, final String code) {
    super(message, "DTM-UNF");
  }

  public UserNoFoundException(final String message, final Throwable cause, final String code) {
    super(message, cause, code);
  }
}
