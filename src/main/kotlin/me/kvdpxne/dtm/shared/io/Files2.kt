package me.kvdpxne.dtm.shared.io

import java.io.FileNotFoundException
import java.io.IOException
import java.net.URI
import java.net.URL
import java.nio.file.FileSystem
import java.nio.file.FileSystems
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.createDirectory
import kotlin.io.path.deleteExisting
import kotlin.io.path.isDirectory
import kotlin.io.path.isWritable
import kotlin.io.path.notExists

object Files2 {

  fun <T> fs(classLoader: ClassLoader, stringPath: String, func: (FileSystem, Path) -> T): T {
    val resource: URL = classLoader.getResource(stringPath)
      ?: throw FileNotFoundException("")

    // A is project directory
    // B is working directory
    // C is jar file name with extension
    // jar:file:/[A]/[B]/plugins/[C]!/[PATH]/
    val rawPath: String = resource.toString()

    val environments: Map<String, String> = emptyMap()
    val arrays: List<String> = rawPath.split('!')

    return FileSystems.newFileSystem(URI.create(arrays[0]), environments).use {
      val path: Path = it.getPath(arrays[1])

      func(it, path)
    }
  }

  /**
   * Creates a directory at the specified path if it does not already exist.
   * If a file exists at the specified path instead of a directory, it will be
   * deleted and replaced with a new directory.
   *
   * This function is optimized for performance by performing early checks and
   * ensuring safe creation of the directory.
   *
   * @param path The path where the directory should be created.
   * @return The path to the created or existing directory.
   *
   * @throws IOException if an I/O error occurs while creating the directory or
   *                     deleting a file at the specified path.
   *
   * @since 0.1.0
   */
  @Synchronized
  fun createDirectoryIfNotExists(
    path: Path?
  ): Path {
    requireNotNull(path) {
      "Path must not be null."
    }

    try {
      if (path.notExists()) {
        val parent: Path = path.parent
        require(parent.isWritable()) {
          "No write permission for the parent directory: $parent"
        }

        return path.createDirectories()
      }

      // Delete if the file is not a directory and recreate it as a directory.
      if (!path.isDirectory()) {
        path.deleteExisting()
        return path.createDirectory()
      }

      return path
    } catch (exception: IOException) {
      throw RuntimeException("Failed to create directory at $path", exception)
    }
  }
}