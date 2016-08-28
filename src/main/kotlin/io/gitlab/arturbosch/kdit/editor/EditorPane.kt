package io.gitlab.arturbosch.kdit.editor

import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import tornadofx.fail
import tornadofx.success
import tornadofx.task
import java.nio.file.Path

/**
 * @author Artur Bosch
 */
class EditorPane : TabPane() {

	val KDIT_NAME = "kdit"
	val titleProperty = SimpleStringProperty(KDIT_NAME)

	var title: String
		get() = titleProperty.get() ?: KDIT_NAME
		set(value) = titleProperty.set(value)

	init {
		registerShortKeys()
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
		if (tab is EditorTab) {
			tab.requestFocus()
			title = "$KDIT_NAME - ${tab.path?.toString() ?: tab.name}"
		}
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
		val maybeTab = openTabs().find { path == it.path }
		if (maybeTab == null) {
			createNewTabAsync { EditorTab(path = path) }
		} else {
			focus(maybeTab)
		}
	}

	private fun createNewTabAsync(tab: () -> EditorTab) {
		task {
			tab.invoke()
		} success {
			tabs.add(it)
			focus(it)
			println("Successful opened tab for ${it.path}")
		} fail {
			println("Failed to open new tab.")
		}
	}

	fun saveTab() {
		findOpenTab().save()
	}

	fun saveAsNewPath() {
		ProjectChooser.chooseFile().ifPresent { savePath ->
			val openTab = findOpenTab()
			tabs.remove(openTab)
			openTabs().filter { savePath == it.path }
					.forEach { tabs.remove(it) }

			createNewTabAsync {
				EditorTab(path = savePath, content = openTab.content,
						name = openTab.name, editable = openTab.editable)
			}
		}
	}

	private fun findOpenTab(): EditorTab {
		return selectionModel.selectedItem as EditorTab
	}

	private fun openTabs(): List<EditorTab> = tabs.map { (it as EditorTab) }
}
