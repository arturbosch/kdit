package io.gitlab.arturbosch.kdit.editor

import io.gitlab.arturbosch.kdit.editor.util.HELP_TEXT
import io.gitlab.arturbosch.kdit.editor.util.onlyIfNull
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
class EditorPane(val editor: Editor) : TabPane() {

	val KDIT_NAME = "kdit"
	val titleProperty = SimpleStringProperty(KDIT_NAME)

	var title: String
		get() = titleProperty.get() ?: KDIT_NAME
		set(value) = titleProperty.set(value)

	init {
		registerShortKeys()
		showHelp()
	}

	fun openProject(project: Path) {
		editor.registerProjectExplorer(project)
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
			title = "$KDIT_NAME - ${retrieveTabName(tab)}"
		}
	}

	fun showHelp() {
		tabs.find { it.text == "Help" }.onlyIfNull {
			createNewTabInBackground {
				EditorTab(name = "Help", editable = false, content = HELP_TEXT)
			}
		}
	}

	fun newTab(path: Path) {
		val maybeTab = openTabs().find { path == it.path }
		if (maybeTab == null) {
			createNewTabInBackground {
				EditorTab(path = path)
			}
		} else {
			focus(maybeTab)
		}
	}

	fun saveTab() {
		findOpenTab().save()
	}

	fun saveAsNewPath() {
		findOpenTab().saveAs()
	}

	fun saveEditedTabs() {
		openTabs().filterNot { it.path == null }.forEach { it.save() }
	}

	fun reloadTabIfFileEndingsChanges(savePath: Path) {
		val openTab = findOpenTab()
		tabs.remove(openTab)
		openTabs().filter { savePath == it.path }
				.forEach { tabs.remove(it) }
		createNewTabInBackground {
			EditorTab(path = savePath)
		}
	}

	fun closeTabsWithSamePathAsThis(tab: EditorTab, path: Path) {
		openTabs().filter { path == it.path }
				.filter { tab != it }
				.forEach { tabs.remove(it) }
	}

	private fun findOpenTab(): EditorTab {
		return selectionModel.selectedItem as EditorTab
	}

	private fun openTabs(): List<EditorTab> = tabs.map { (it as EditorTab) }

	private fun retrieveTabName(tab: EditorTab) = tab.path?.toString() ?: tab.name

	private fun createNewTabInBackground(tab: () -> EditorTab) {
		task {
			tab.invoke()
		} success {
			tabs.add(it)
			focus(it)
			println("Successful opened tab for ${retrieveTabName(it)}")
		} fail {
			println("Failed to open new tab.")
		}
	}

}
