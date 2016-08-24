package io.gitlab.arturbosch.kdit.editor

import io.gitlab.arturbosch.kdit.highlightings.computeHighlighting
import javafx.scene.control.Tab
import javafx.scene.layout.StackPane
import org.fxmisc.flowless.VirtualizedScrollPane
import org.fxmisc.richtext.CodeArea
import org.fxmisc.richtext.LineNumberFactory
import org.reactfx.EventStreams
import tornadofx.plusAssign
import tornadofx.success
import tornadofx.task
import java.nio.file.Files
import java.nio.file.Path

/**
 * @author Artur Bosch
 */
class EditorTab(name: String = "New Tab..", content: String = "",
				path: Path? = null, val editable: Boolean = true) : Tab(name) {

	private var codeArea: CodeArea = CodeArea()

	init {
		if (path != null) {
			loadCodeArea(path)
			determineTabName(path)
		} else {
			initCodeArea(content)
		}
	}

	private fun initCodeArea(content: String) {
		codeArea.apply {
			paragraphGraphicFactory = LineNumberFactory.get(this)
			richChanges().filter { ch -> ch.inserted != ch.removed }
					.subscribe { change -> this.setStyleSpans(0, computeHighlighting(this.text)) }
			registerShortKeys()
			isEditable = editable
			replaceText(content)
		}
		this += StackPane(VirtualizedScrollPane(codeArea))
		requestFocus()
	}

	private fun loadCodeArea(path: Path) {
		task {
			Files.readAllLines(path)
					.joinToString("\n")
		} success {
			initCodeArea(it)
		}
	}

	private fun determineTabName(path: Path) {
		val savedTitle = path.fileName.toString()
		val unsavedTitle = "*" + savedTitle
		text = if (codeArea.undoManager.isAtMarkedPosition) savedTitle else unsavedTitle
		EventStreams.valuesOf(codeArea.undoManager.atMarkedPositionProperty())
				.map { saved -> if (saved) savedTitle else unsavedTitle }
				.subscribe { title -> text = title }
	}

	fun requestFocus() {
		codeArea.requestFocus()
	}
}
