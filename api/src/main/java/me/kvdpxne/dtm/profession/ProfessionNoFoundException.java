package me.kvdpxne.dtm.profession;

import me.kvdpxne.dtm.CodecException;

public class ProfessionNoFoundException extends CodecException  {
  public ProfessionNoFoundException(String message, String code) {
    super(message, code);
  }

  public ProfessionNoFoundException(String message, Throwable cause, String code) {
    super(message, cause, code);
  }
}
