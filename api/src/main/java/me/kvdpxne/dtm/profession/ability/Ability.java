package me.kvdpxne.dtm.profession.ability;

import me.kvdpxne.dtm.event.Cancellable;
import me.kvdpxne.dtm.capabilities.Copyable;
import me.kvdpxne.dtm.DisplayableNameable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

public interface Ability
  extends
  Cancellable,
  DisplayableNameable,
  Copyable<Ability> {

  /**
   * @since 0.1.0
   */
  @Override
  @NotNull
  String getName();

  /**
   * @since 0.1.0
   */
  @Override
  @Nullable
  String getDisplayName();

  /**
   * @since 0.1.0
   */
  short getCooldown();

  /**
   * @since 0.1.0
   */
  short getRemainingCooldown();

  /**
   * @since 0.1.0
   */
  void reduceCooldown(
    final @Range(from = 0, to = Short.MAX_VALUE) short amount
  );

  /**
   * @since 0.1.0
   */
  void reduceCooldown();

  /**
   * @since 0.1.0
   */
  boolean isPaused();

  /**
   * ready to use
   */
  boolean isReady();

  /**
   *
   */
  boolean isActive();

  /**
   *
   */
  boolean wasUsed();

  /**
   * @since 0.1.0
   */
  @NotNull
  AbilityType getType();

  /**
   * @since 0.1.0
   */
  void pause();

  /**
   * @since 0.1.0
   */
  void renew();

  /**
   * @since 0.1.0
   */
  @Override
  void cancel();

  /**
   * @since 0.1.0
   */
  @Override
  @NotNull
  Ability copy();
}
