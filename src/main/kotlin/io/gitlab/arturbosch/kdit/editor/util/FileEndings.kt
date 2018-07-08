package io.gitlab.arturbosch.kdit.editor.util

import java.nio.file.Files
import java.nio.file.Path

/**
 * @author Artur Bosch
 */
object FileEndings {

	fun isSame(path1: Path, path2: Path): Boolean {
		return path1.toString().endsWith(path2.toString().substringAfterLast("."))
	}

	fun isSupported(path: Path): Boolean {
		val contentType = Files.probeContentType(path)
		return when {
			contentType.startsWith("text") -> true
			contentType.startsWith("application") -> return when (contentType.substringAfter("/")) {
				"x-yaml", "yaml", "xml", "json", "html" -> true
				else -> false
			}
			else -> false
		}
	}

}
