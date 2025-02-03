package me.kvdpxne.dtm.position;

/**
 * @since 0.1.0
 */
public final class PositionMultidimensionalException
  extends
  PositionException {

  private static final long serialVersionUID = -6663460999724883697L;

  public PositionMultidimensionalException(String message) {
    super(message, "DTM-PME");
  }

  public PositionMultidimensionalException(String message, Throwable cause) {
    super(message, cause, "DTM-PME");
  }
}
