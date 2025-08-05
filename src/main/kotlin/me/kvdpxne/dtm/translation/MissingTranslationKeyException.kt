package me.kvdpxne.dtm.translation

/**
 * @since 0.1.0
 */
internal class MissingTranslationKeyException internal constructor(
  keyName: String = "unspecified"
) : TranslationException() {

  companion object {

    /**
     * @since 0.1.0
     */
    @Suppress("ConstPropertyName")
    private const val serialVersionUID: Long = -6631442349892612368L
  }
}