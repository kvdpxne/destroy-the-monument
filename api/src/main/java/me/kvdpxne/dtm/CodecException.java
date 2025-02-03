package me.kvdpxne.dtm;

public class CodecException extends RuntimeException {

  private static final long serialVersionUID = -9200921740924337778L;

  private final String code;

  public CodecException(final String message, final String code) {
    super(message);
    this.code = code;
  }

  public CodecException(final String message, final Throwable cause, final String code) {
    super(message, cause);
    this.code = code;
  }

  public String getCode() {
    return this.code;
  }
}
