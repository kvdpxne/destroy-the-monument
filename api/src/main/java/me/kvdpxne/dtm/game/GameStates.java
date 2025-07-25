package me.kvdpxne.dtm.game;

public final class GameStates {

  public static final int INITIALIZED = 2;

  public static final int STARTING = 1;

  public static final int RUNNING = 0;

  public static final int ENDING = 4;

  public static final int STOPPING = 3;

  private GameStates() {
    throw new AssertionError();
  }
}


