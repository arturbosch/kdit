package io.gitlab.arturbosch.kdit.editor

import io.gitlab.arturbosch.kdit.highlightings.StyleSheets
import io.gitlab.arturbosch.kdit.highlightings.syntax
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
class EditorTab(val name: String = "New Tab..", content: String = "",
				var path: Path? = null, private val editable: Boolean = true) : Tab(name) {

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
			enableHighlighting(path)
			registerShortKeys()
			isEditable = editable
			replaceText(content)
		}
		this.add(StackPane(VirtualizedScrollPane(codeArea)))
		requestFocus()
	}

	private fun CodeArea.enableHighlighting(path: Path?) {
		path?.run {
			// if style for path is found, add syntax
			StyleSheets.get(path)?.run {
				stylesheets.add(this)
				richChanges()
						.filter { ch -> ch.inserted != ch.removed }
						.subscribe { change -> setStyleSpans(0, syntax(text, path)) }
			}
		}
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
			enableStyleAfterSave(it)
		}
	}

	private fun enableStyleAfterSave(it: Path?) {
		codeArea.enableHighlighting(it)
		codeArea.appendText(" ")
		codeArea.deletePreviousChar()
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
