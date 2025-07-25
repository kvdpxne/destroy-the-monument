package me.kvdpxne.dtm.shared

fun uniqueString(
  previous: String? = null,
  collection: Array<String>,
  ignoreCase: Boolean = true
): String {
  return of(
    previous,
    { collection.random() },
    { next: String -> next.equals(previous, ignoreCase) }
  )
}