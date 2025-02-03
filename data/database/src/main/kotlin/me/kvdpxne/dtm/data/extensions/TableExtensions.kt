package me.kvdpxne.dtm.data.extensions

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.DoubleColumnType
import org.jetbrains.exposed.sql.FloatColumnType
import org.jetbrains.exposed.sql.IntegerColumnType
import org.jetbrains.exposed.sql.LongColumnType
import org.jetbrains.exposed.sql.Table

const val PREFIX_PRIMARY_KEY = "pk"

const val PREFIX_CHECK = "chk"

val LIMIT_TO_DELETE: Int? = 1

val UPDATE_LIMIT: Int? = 1

internal fun generateCustomCheckPrefix(
  type: String,
  tableName: String,
  name: String
): String {
  return "${PREFIX_CHECK}_${tableName}_${type}_$name"
}

/**
 * Defines a column of type Int representing an unsigned 32-bit integer.
 *
 * Due to Java's lack of native unsigned integer types and limitations in some databases
 * like SQLite (which also lacks unsigned integer support), this function uses a signed
 * Int type to store values. The name "usint32" (unsigned/signed int 32) reflects this
 * compromise, indicating that while the intended usage is for unsigned values (non-negative),
 * the underlying storage type is signed to ensure compatibility across Java and various
 * databases. Values stored in this column are checked to ensure they fall within the
 * non-negative range (0 to Int.MAX_VALUE).
 *
 * This function extends the `Table` class from the JetBrains Exposed library.
 *
 * @param name The name of the column.
 * @return A Column<Int> representing the unsigned 32-bit integer column.
 * @since 0.1.0
 */
internal fun Table.usint32(
  name: String
): Column<Int> {
  return this.registerColumn(name, IntegerColumnType())
    .check(
      generateCustomCheckPrefix(
        "usint32",
        this@usint32.tableName,
        name
      )
    ) {
      it.between(0, Int.MAX_VALUE)
    }
}

/**
 * unsigned/signed int 32
 *
 * @since 0.1.0
 */
internal fun Table.usint64(
  name: String
): Column<Long> {
  return this.registerColumn(name, LongColumnType())
    .check {
      it.between(0L, Long.MAX_VALUE)
    }
}

internal fun Table.usfloat32(
  name: String,
  from: Float = 0.0F,
  to: Float = Float.MAX_VALUE,
): Column<Float> {
  return this.registerColumn(name, FloatColumnType())
    .check {
      it.between(from, to)
    }
}

internal fun Table.usfloat64(
  name: String,
  from: Double = 0.0,
  to: Double = Double.MAX_VALUE,
): Column<Double> {
  return this.registerColumn(name, DoubleColumnType())
    .check {
      it.between(from, to)
    }
}