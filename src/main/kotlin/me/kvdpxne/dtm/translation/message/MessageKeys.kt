package me.kvdpxne.dtm.translation.message

enum class MessageKeys : MessageKeyProvider {

  COMMAND_ABILITY_DEPLETE,
  COMMAND_ABILITY_RENEW,
  COMMAND_ARENA_ADD,
  COMMAND_ARENA_CREATE,
  COMMAND_ARENA_LIST,
  COMMAND_ARENA_REMOVE,
  COMMAND_COINS_SET_SELF,
  COMMAND_COINS_SET_OTHERS,
  COMMAND_INSUFFICIENT_PRIVILEGES
  ;

  override val messageKey: MessageKey
    get() = MessageKey.of(this.name)
}