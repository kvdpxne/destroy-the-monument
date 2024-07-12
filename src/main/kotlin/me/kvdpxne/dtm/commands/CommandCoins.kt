package me.kvdpxne.dtm.commands

import me.kvdpxne.dtm.command.Command
import me.kvdpxne.dtm.command.CommandBuilder
import me.kvdpxne.dtm.user.UserPerformer

// TODO coins command
// /dtm coins
// /dtm coins set            <PLAYER_NAME> <VALUE>
// /dtm coins add            <PLAYER_NAME> <VALUE>
// /dtm coins subtract       <PLAYER_NAME> <VALUE>
// /dtm coins multiplier     <PLAYER_NAME>
// /dtm coins multiplier set <PLAYER_NAME> <VALUE>
// /dtm coins top
fun createCoinsCommand(): Command = CommandBuilder()
  .name("coins")
  .parent("dtm")
  .handler<UserPerformer> { performer, _ ->
    performer.sendMessage("&fCoins: &6${performer.user.wallet.coins}")
  }
  .build()
