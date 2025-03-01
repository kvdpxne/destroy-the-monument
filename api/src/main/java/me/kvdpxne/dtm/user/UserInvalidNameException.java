package me.kvdpxne.dtm.user;

public class UserInvalidNameException extends UserException {

  private static final long serialVersionUID = -7061230509755226741L;

  public UserInvalidNameException(String message) {
    super(message, "DTM_INVALID_NAME");
  }
}
