package io.gitlab.arturbosch.kdit.editor

import io.gitlab.arturbosch.kdit.editor.util.Defaults
import org.fxmisc.richtext.CodeArea
import org.fxmisc.richtext.model.NavigationActions

/**
 * @author Artur Bosch
 */


fun CodeArea.nextPage() {
	val index = currentParagraph
	if (paragraphs.size - 1 <= index + Defaults.pageSize) {
		moveTo(paragraphs.size - 1, 0)
	} else {
		moveTo(index + Defaults.pageSize, 0)
	}
}

fun CodeArea.previousPage() {
	val index = currentParagraph
	if (0 >= index - Defaults.pageSize) {
		moveTo(0, 0)
	} else {
		moveTo(index - Defaults.pageSize, 0)
	}
}

fun CodeArea.nextSection() {
	val currentIndex = currentParagraph
	val indexedPar = paragraphs.asSequence().withIndex()
			.filter { it.index > currentIndex }
			.filter { it.value.text.isBlank() }
			.firstOrNull()
	indexedPar?.run { moveTo(index, 0) } ?: moveTo(length)
}

fun CodeArea.previousSection() {
	val currentIndex = currentParagraph
	val indexedPar = paragraphs.asSequence().withIndex()
			.filter { it.index < currentIndex }
			.filter { it.value.text.isBlank() }
			.lastOrNull()
	indexedPar?.run { moveTo(index, 0) } ?: moveTo(0)
}

fun CodeArea.deleteLine() {
	val x = currentParagraph
	selectLine()
	replaceSelection("")
	if (onLastLine(x)) {
		deletePreviousChar()
	} else {
		deleteNextChar()
	}
}

fun CodeArea.onLastLine(x: Int) = paragraphs.size - 1 == x

fun CodeArea.duplicateLine() {
	val y = caretColumn
	val x = currentParagraph
	selectLine()
	if (selection.length == 0) {
		newLine()
	} else {
		copy()
		deselect()
		newLine()
		paste()
		moveTo(x + 1, y)
	}
}

fun CodeArea.newLine() {
	lineEnd(NavigationActions.SelectionPolicy.CLEAR)
	replaceSelection("\n")
}