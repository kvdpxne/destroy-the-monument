package me.kvdpxne.dtm.gui

class SlotAlreadyTakenException(index: Int) : RuntimeException("Slot at index $index is already taken.")