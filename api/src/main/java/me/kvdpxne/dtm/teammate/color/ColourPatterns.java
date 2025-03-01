package me.kvdpxne.dtm.teammate.color;

import java.util.regex.Pattern;

/**
 * @since 0.1.0
 */
public final class ColourPatterns {

  /**
   * @since 0.1.0
   */
  public static final Pattern COLOR_IN_HEX =
    Pattern.compile("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$");

  /**
   * @since 0.1.0
   */
  public static final Pattern COLOR_IN_MINECRAFT =
    Pattern.compile("^&[A-Fa-f0-9]{2}$");
}
