package me.kvdpxne.dtm.data;

import me.kvdpxne.dtm.UnsubstantiatedInitializationError;

public final class InteractionErrorCodes {

  public static final int DUPLICATED = -1280714084;

  public static final int NO_TABLE = -521959215;

  public static final int NO_REFERENCE = -1429889779;

  public static final int NO_RECORD = -95125818;

  private InteractionErrorCodes() {
    throw new UnsubstantiatedInitializationError(this.getClass());
  }
}
