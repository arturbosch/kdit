package io.gitlab.arturbosch.kdit.editor.util

import java.nio.file.Path

/**
 * @author Artur Bosch
 */
object Comments {

	fun of(path: Path?): Pair<String, String> {
		return when (path.toString().substringAfterLast(".")) {
			"java", "groovy", "kt", "scala" -> "//" to ""
			"md", "xml" -> "<!--" to "-->"
			else -> "" to ""
		}
	}

}