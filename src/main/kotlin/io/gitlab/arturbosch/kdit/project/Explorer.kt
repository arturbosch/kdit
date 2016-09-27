package io.gitlab.arturbosch.kdit.project

import io.gitlab.arturbosch.kdit.editor.EditorPane
import io.gitlab.arturbosch.kdit.editor.util.FileEndings
import javafx.event.EventHandler
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.input.MouseEvent
import java.nio.file.Path
import java.util.Stack

/**
 * @author Artur Bosch
 */
class Explorer(val projectPath: Path, val editorPane: EditorPane) : TreeView<String>() {

	init {
		root = FileEntry(projectPath)
		root.isExpanded = true
		style = "-fx-font: 12px Tahoma; -fx-stroke: #eeeeee; -fx-background-color: #0a0a0a; -fx-text-fill: #ffffff;"
		expandSrcMainFolder()
		registerListeners()
	}

	private fun registerListeners() {
		val mouseEventHandle = EventHandler { event: MouseEvent -> handleMouseClicked(event) }
		val keyEventHandle = EventHandler { event: KeyEvent -> handleEnterClicked(event) }
		addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEventHandle)
		addEventHandler(KeyEvent.KEY_PRESSED, keyEventHandle)
	}

	private fun handleEnterClicked(event: KeyEvent) {
		if (event.code == KeyCode.ENTER) {
			handle(selectionModel.selectedItem)
		}
	}

	private fun handle(item: TreeItem<String>) {
		val pathToOpen = (item as FileEntry).path
		if (FileEndings.isSupported(pathToOpen)) {
			editorPane.newTab(pathToOpen)
		}
	}

	private fun handleMouseClicked(event: MouseEvent) {
		if (event.clickCount == 2) {
			handle(selectionModel.selectedItem)
		}
	}

	private fun expandSrcMainFolder() {
		findFolder(root as FileEntry, "src")?.let {
			it.isExpanded = true
			val dirs = Stack<FileEntry>()
			dirs.addAll(it.children.map { it as FileEntry })
			while (!dirs.empty()) {
				recursivelyExpandFolders(dirs)
			}
		}
	}

	private fun findFolder(fileEntry: FileEntry, folderToFind: String): FileEntry? {
		return fileEntry.children.asSequence()
				.map { it as FileEntry }
				.find { it.path.fileName.toString() == folderToFind }
	}

	private fun recursivelyExpandFolders(dirs: Stack<FileEntry>) {
		val nextEntry = dirs.pop()
		nextEntry.isExpanded = true
		val children = nextEntry.children.map { it as FileEntry }
		if (children.all { it.isDirectory }) {
			for (child in children) {
				child.isExpanded = true
				dirs.push(child)
			}
		}
	}

}