package me.kvdpxne.dtm.kit

import java.lang.RuntimeException

class SlotAlreadyTakenException(index: Int) : RuntimeException("Slot at index $index is already taken.")