package me.kvdpxne.dtm.shared.reflection

interface FieldAccessor {

  fun get(target: Any): Any?

  fun set(target: Any, value: Any): Any

  fun has(target: Any): Boolean
}