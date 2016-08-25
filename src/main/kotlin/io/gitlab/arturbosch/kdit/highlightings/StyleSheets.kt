package io.gitlab.arturbosch.kdit.highlightings

import java.nio.file.Path

/**
 * @author Artur Bosch
 */

object StyleSheets {

	val javaStyle: Lazy<String> = lazy { javaClass.getResource("/java-keywords.css").toExternalForm() }

	fun get(path: Path): String? {
		return if (path.toString().endsWith("java"))
			javaStyle.value
		else null
	}

}