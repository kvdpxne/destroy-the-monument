package me.kvdpxne.dtm.data.validation

import me.kvdpxne.dtm.shared.StylishToStringBuilder
import me.kvdpxne.dtm.util.StylishToString
import org.jetbrains.annotations.Range

data class BasicValidationError(
  private val field: String,
  private val message: String,
  private val code: Int,
  private val invalidValue: Any? = null,
  private val metadata: Map<String, Any?>? = null
) : ValidationError {

  init {
    require(this.field.isNotBlank()) {
      "Validation error field must not be blank or empty."
    }

    require(this.message.isNotBlank()) {
      "Validation error message must not be blank or empty."
    }

    require(0 > this.code) {
      "Validation error code must be a negative integer."
    }

    this.metadata?.run {
      for ((key: String, _: Any?) in this) {
        require(key.isNotBlank()) {
          "Validation error metadata key must not be blank or empty."
        }
      }
    }
  }

  override fun getField(): String {
    return this.field
  }

  override fun getMessage(): String {
    return this.message
  }

  override fun getCode(): @Range(from = -2147483648, to = -1) Int {
    return this.code
  }

  override fun getInvalidValue(): Any? {
    return this.invalidValue
  }

  override fun getMetadata(): Map<String, Any?>? {
    return this.metadata
  }

  override fun toStylishString(): StylishToString {
    return StylishToStringBuilder
      .begin("BasicValidatorError")
      .add("field", this.field)
      .add("message", this.message)
      .add("code", this.code)
      .add("invalidValue", this.invalidValue)
      .add("metadata", this.metadata)
  }

  override fun toString(): String {
    return this.toStylishString().packed()
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is BasicValidationError) return false

    if (this.code != other.code) return false
    if (this.field != other.field) return false

    return true
  }

  override fun hashCode(): Int {
    var result = this.code
    result = 31 * result + this.field.hashCode()
    return result
  }
}
