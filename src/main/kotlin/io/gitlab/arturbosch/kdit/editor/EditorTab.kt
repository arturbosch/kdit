package io.gitlab.arturbosch.kdit.editor

import io.gitlab.arturbosch.kdit.highlightings.StyleSheets
import io.gitlab.arturbosch.kdit.highlightings.computeHighlighting
import javafx.beans.value.ObservableBooleanValue
import javafx.scene.control.Tab
import javafx.scene.control.Tooltip
import javafx.scene.layout.StackPane
import org.fxmisc.flowless.VirtualizedScrollPane
import org.fxmisc.richtext.CodeArea
import org.fxmisc.richtext.LineNumberFactory
import org.reactfx.EventStreams
import tornadofx.add
import tornadofx.fail
import tornadofx.success
import tornadofx.task
import java.nio.file.Files
import java.nio.file.Path

/**
 * @author Artur Bosch
 */
class EditorTab(name: String = "New Tab..", content: String = "",
				private var path: Path? = null, private val editable: Boolean = true) : Tab(name) {

	private var codeArea: CodeArea = CodeArea()

	init {
		val thisPath = path
		if (thisPath != null && content.isEmpty()) {
			loadCodeArea(thisPath)
			determineTabName(thisPath)
		} else if (thisPath != null) {
			initCodeArea(content)
			determineTabName(thisPath)
		} else {
			initCodeArea(content)
		}
	}

	private fun initCodeArea(content: String) {
		codeArea.apply {
			paragraphGraphicFactory = LineNumberFactory.get(this)
			addStyleSheetIfAvailableForPath(path)
			enableHighlighting()
			registerShortKeys()
			isEditable = editable
			replaceText(content)
		}
		this.add(StackPane(VirtualizedScrollPane(codeArea)))
		requestFocus()
	}

	private fun CodeArea.enableHighlighting() {
		richChanges().filter { ch -> ch.inserted != ch.removed }
				.subscribe { change -> this.setStyleSpans(0, computeHighlighting(this.text)) }
	}

	private fun CodeArea.addStyleSheetIfAvailableForPath(path: Path?) {
		path?.run { StyleSheets.get(path)?.run { stylesheets.add(this) } }
	}

	private fun loadCodeArea(path: Path) {
		task {
			Files.readAllLines(path).joinToString("\n")
		} success {
			initCodeArea(it)
		} fail {
			println("Could not load content of $path")
		}
	}

	private fun determineTabName(path: Path) {
		val savedTitle = path.fileName.toString()
		val unsavedTitle = "*" + savedTitle
		text = if (codeArea.undoManager.isAtMarkedPosition) savedTitle else unsavedTitle
		tooltip = Tooltip(path.toString())
		EventStreams.valuesOf(codeArea.undoManager.atMarkedPositionProperty())
				.map { saved -> if (saved) savedTitle else unsavedTitle }
				.subscribe { title -> text = title }
	}

	fun requestFocus() {
		codeArea.requestFocus()
	}

	fun save(savePath: Path? = path) {
		if (savePath != null) {
			path = savePath
			writeToFile()
			codeArea.undoManager.currentPosition.mark()
			determineTabName(savePath)
		} else {
			saveAs()
		}
	}

	fun saveAs() {
		ProjectChooser.chooseFile().ifPresent {
			save(it)
		}
	}

	private fun writeToFile() {
		task {
			Files.write(path, codeArea.text.toByteArray())
		} success {
			println("Saved path: $path")
		} fail {
			println("Save failed for path: $path")
		}
	}
}
