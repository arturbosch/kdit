package io.gitlab.arturbosch.kdit.project

import javafx.scene.control.TreeItem
import javafx.scene.image.ImageView
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.attribute.BasicFileAttributes
import java.util.Comparator

/**
 * @author Artur Bosch
 */
class FileEntry(val path: Path) : TreeItem<String>() {

	companion object {
		private val FOLDER_IMAGE = ::FileEntry.javaClass.getResource("/folder.png").toString()
		private val FOLDER_OPEN_IMAGE = ::FileEntry.javaClass.getResource("/folder-open.png").toString()
		private val FILE_IMAGE = ::FileEntry.javaClass.getResource("/file.png").toString()
	}

	val isDirectory: Boolean = Files.isDirectory(path)

	init {
		value = path.fileName.toString()
		graphic = if (isDirectory) ImageView(FOLDER_IMAGE) else ImageView(FILE_IMAGE)

		addEventHandler<TreeModificationEvent<String>>(TreeItem.branchExpandedEvent()) {
			val source = it.source as FileEntry
			if (source.isDirectory && source.isExpanded) {
				source.graphic = ImageView(FOLDER_OPEN_IMAGE)
			}
			if (source.children.isEmpty()) {
				val attributes = Files.readAttributes(source.path, BasicFileAttributes::class.java)
				if (attributes.isDirectory) {
					Files.newDirectoryStream(source.path).sortedWith(compareByDirAndName())
							.forEach {
								val fileEntry = FileEntry(it)
								source.children.add(fileEntry)
							}
				}
			} else {

			}
		}

		addEventHandler<TreeModificationEvent<String>>(TreeItem.branchCollapsedEvent()) {
			val source = it.source as FileEntry
			if (source.isDirectory && !source.isExpanded) {
				source.graphic = ImageView(FOLDER_IMAGE)
			}
		}
	}

	private fun compareByDirAndName(): Comparator<Path> {
		return Comparator { o1, o2 ->
			if (Files.isDirectory(o1) && !Files.isDirectory(o2)) {
				return@Comparator -1
			}
			if (!Files.isDirectory(o1) && Files.isDirectory(o2)) {
				return@Comparator 1
			}
			o1.toString().compareTo(o2.toString())
		}
	}

	override fun isLeaf(): Boolean {
		return !isDirectory
	}
}
