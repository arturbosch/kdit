package io.gitlab.arturbosch.kdit.highlightings

import java.nio.file.Path

/**
 * @author Artur Bosch
 */

object StyleSheets {

	val javaStyle: Lazy<String> = lazy { javaClass.getResource("/java-keywords.css").toExternalForm() }
	val xmlStyle: Lazy<String> = lazy { javaClass.getResource("/xml-keywords.css").toExternalForm() }
	val mdStyle: Lazy<String> = lazy { javaClass.getResource("/md-style.css").toExternalForm() }

	fun get(path: Path): String? {
		return when (path.toString().substringAfterLast(".")) {
			"java", "groovy", "kt", "scala" -> javaStyle.value
			"xml" -> xmlStyle.value
			"md" -> mdStyle.value
			else -> null
		}
	}

}