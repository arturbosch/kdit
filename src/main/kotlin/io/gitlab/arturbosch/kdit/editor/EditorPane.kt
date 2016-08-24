package io.gitlab.arturbosch.kdit.editor

import io.gitlab.arturbosch.kdit.highlightings.sampleCode
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import java.nio.file.Path

/**
 * @author Artur Bosch
 */
class EditorPane : TabPane() {

	init {
		registerShortKeys()
		editorTab(name = "Test", content = sampleCode)
	}

	fun switchTabLeft() {
		selectionModel.selectPrevious()
		focus(selectionModel.selectedItem)
	}

	fun switchTabRight() {
		selectionModel.selectNext()
		focus(selectionModel.selectedItem)
	}

	fun switchFocus() {
		if (isFocused) {
			focus(selectionModel.selectedItem)
		} else {
			requestFocus()
		}
	}

	fun focus(tab: Tab) {
		this.selectionModel.select(tab)
		if (tab is EditorTab) tab.requestFocus()
	}


	fun showHelp() {
		var helpTab = tabs.find { it.text == "Help" }
		if (helpTab == null) {
			helpTab = editorTab(name = "Help", editable = false,
					content = "Shortkeys:\n Ctrl+K - Delete line \n Ctrl+D - Duplicate line")
		}
		focus(helpTab)
	}

	fun newTab(path: Path) {
		focus(editorTab(path = path))
	}
}
