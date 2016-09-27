package io.gitlab.arturbosch.kdit.editor.util

import io.gitlab.arturbosch.kdit.editor.Editor
import io.gitlab.arturbosch.kdit.editor.EditorPane
import io.gitlab.arturbosch.kdit.editor.EditorTab
import javafx.event.EventTarget
import javafx.scene.control.TabPane
import tornadofx.opcr
import java.nio.file.Path

/**
 * @author Artur Bosch
 */

fun EventTarget.editorPane(editor: Editor, op: (EditorPane.() -> Unit)? = null) = opcr(this, EditorPane(editor), op)

fun TabPane.editorTab(name: String = "", content: String = "",
					  path: Path? = null, editable: Boolean = true,
					  op: (EditorTab.() -> Unit)? = null): EditorTab {
	val tab = EditorTab(name = name, content = content, path = path, editable = editable)
	tabs.add(tab)
	op?.invoke(tab)
	return tab
}

fun Any?.notNull(): Boolean = if (this != null) true else false

inline fun <T, R> T.onlyIfNull(block: T?.() -> R) {
	if (this == null) {
		block()
	}
}

fun String.replaceLast(oldChars: String, newChars: String, ignoreCase: Boolean = false): String {
	val index = lastIndexOf(oldChars, ignoreCase = ignoreCase)
	return if (index < 0) this else this.replaceRange(index, index + oldChars.length, newChars)
}