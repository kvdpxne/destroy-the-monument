package me.kvdpxne.dtm.util;

public final class Strings {

  private Strings() {
    throw new AssertionError("No instances.");
  }

  public static boolean isEmpty(final String s) {
    return null == s || s.isEmpty();
  }

  public static boolean isBlank(final String s) {
    return null == s || s.trim().isEmpty();
  }
}
