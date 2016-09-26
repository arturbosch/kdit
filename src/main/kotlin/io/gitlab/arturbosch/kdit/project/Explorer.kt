package io.gitlab.arturbosch.kdit.project

import javafx.scene.control.TreeView
import java.nio.file.Path
import java.util.Stack

/**
 * @author Artur Bosch
 */
class Explorer(projectPath: Path) : TreeView<String>() {

	init {
		root = FileEntry(projectPath)
		root.isExpanded = true
		style = "-fx-font: 12px Tahoma; -fx-stroke: #eeeeee; -fx-background-color: #0a0a0a; -fx-text-fill: #ffffff;"
		expandSrcMainFolder()
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
				println("$child")
				child.isExpanded = true
				dirs.push(child)
			}
		}
	}

}