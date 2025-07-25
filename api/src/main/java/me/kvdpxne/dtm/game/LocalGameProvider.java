package me.kvdpxne.dtm.game;

import me.kvdpxne.dtm.LocalProvider;

public interface LocalGameProvider extends LocalProvider<LocalGame> {

  default LocalGame toLocalGame() {
    return this.toLocal();
  }
}
