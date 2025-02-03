package me.kvdpxne.dtm.translation;

import me.kvdpxne.boujee.TranslationKeyProvider;
import org.jetbrains.annotations.NotNull;

public interface Communicable {

  void chat(
    final @NotNull TranslationKeyProvider keyProvider
  );

  void title(
    final @NotNull TranslationKeyProvider keyProvider
  );

  void subtitle(
    final @NotNull TranslationKeyProvider keyProvider
  );

  void action(
    final @NotNull TranslationKeyProvider keyProvider
  );
}
