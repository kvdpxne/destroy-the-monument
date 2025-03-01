package me.kvdpxne.dtm.teammate.color;

public final class ColourValidator {

  public static boolean isValidColour(final String colour) {
    return (null != colour && !colour.isEmpty()) &&
      (ColourPatterns.COLOR_IN_HEX.matcher(colour).matches() ||
        ColourPatterns.COLOR_IN_MINECRAFT.matcher(colour).matches());
  }
}
