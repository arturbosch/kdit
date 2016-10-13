package io.gitlab.arturbosch.kdit.editor

import io.gitlab.arturbosch.kdit.editor.util.editorPane
import io.gitlab.arturbosch.kdit.project.Explorer
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import tornadofx.FX
import tornadofx.View
import tornadofx.add
import tornadofx.borderpane
import tornadofx.center
import tornadofx.left
import tornadofx.success
import tornadofx.task
import tornadofx.top
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

/**
 * @author Artur Bosch
 */
class Editor : View() {

	private lateinit var editorPane: EditorPane
	private var projectExplorer: Explorer? = null

	override val root = borderpane {
		top {
			// for toolbar
		}
		center {
			editorPane(this@Editor) {
				editorPane = this
				VBox.setVgrow(this, Priority.ALWAYS)
			}
		}
	}

	override fun onDock() {
		bindTitle()
		parseArguments()
	}

	private fun bindTitle() {
		this@Editor.titleProperty.bind(this.titleProperty)
	}

	private fun parseArguments() {
		val args = FX.application.parameters.raw
		if (args.size > 0) {
			val path = Paths.get(args[0])
			startWithPath(path)
		} else {
			editorPane.showHelp()
		}
	}

	private fun startWithPath(path: Path) {
		if (Files.exists(path)) {
			if (Files.isDirectory(path)) {
				registerProjectExplorer(path)
			} else {
				editorPane.newTab(path)
			}
		} else {
			editorPane.showHelp()
		}
	}

	fun registerProjectExplorer(path: Path) {
		if (projectExplorer?.projectPath == path) {
			return
		} else {
			task {
				Explorer(path, editorPane).apply { projectExplorer = this }
			} success {
				root.left {
					add(it)
				}
			}
		}
	}

	fun switchToExplorer() {
		projectExplorer?.requestFocus()
	}
}