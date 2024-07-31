package me.kvdpxne.dtm.tasks

import fr.mrmicky.fastboard.FastBoard
import kotlin.time.Duration.Companion.seconds
import me.kvdpxne.dtm.game.Game
import me.kvdpxne.dtm.scoreboard.updateScoreboardTime
import me.kvdpxne.dtm.shared.minecraft.bukkit.runSynchronousTask
import org.bukkit.scheduler.BukkitRunnable

class GameTimeUpdateTaskTimer(
  private val game: Game,
): BukkitRunnable() {

  val playerMutableList = mutableListOf<FastBoard>()

  private var secondsNumber: Int = 0

  private fun formatTime(): String {
    var fullTime = ""

    // 59m 59s
    val text = this.secondsNumber.seconds.toString()

    // Time with minutes and seconds
    if (text.contains(" ")) {
      val timeText = text.split(" ")

      fullTime += if (2 == timeText[0].length) {
        "0${timeText[0]}"
      } else {
        timeText[0]
      }

      fullTime += if (2 == timeText[1].length) {
        ":0${timeText[1]}"
      } else {
        ":${timeText[1]}"
      }

      return fullTime.replace("m", "")
        .replace("s", "")
    }

    // Time with only seconds
    if (text.contains("s") && !text.contains("m")) {
      fullTime += if (text.length == 2) {
        "00:0$text"
      } else {
        "00:$text"
      }
      return fullTime.replace("s", "")
    }

    // Time with only a minute
    if (text.contains("m") && !text.contains("s")) {
      fullTime += if (text.length == 2) {
        "0$text:00"
      } else {
        "$text:00"
      }
      return fullTime.replace("m", "")
    }

    throw RuntimeException("unsupported time")
  }

  override fun run() {
    if (3600 < this.secondsNumber) {
      this.cancel()

      runSynchronousTask {
        this.game.stop()
      }
      return
    }

    this.playerMutableList.forEach {
      updateScoreboardTime(it, this.formatTime())
    }

    ++this.secondsNumber
  }
}