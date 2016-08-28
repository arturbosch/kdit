package io.gitlab.arturbosch.kdit.editor.util

import java.nio.file.Path

/**
 * @author Artur Bosch
 */
object FileEndings {

	fun isSame(path1: Path, path2: Path): Boolean {
		return path1.toString().endsWith(path2.toString().substringAfterLast("."))
	}
}