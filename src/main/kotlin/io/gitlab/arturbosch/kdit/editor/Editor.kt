package io.gitlab.arturbosch.kdit.editor

import io.gitlab.arturbosch.kdit.editor.util.editorPane
import io.gitlab.arturbosch.kdit.project.Explorer
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import tornadofx.View
import tornadofx.add
import tornadofx.borderpane
import tornadofx.center
import tornadofx.left
import tornadofx.success
import tornadofx.task
import tornadofx.top
import java.nio.file.Path

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
			editorPane {
				bindTitle()
				editorPane = this
				registerEditor(this@Editor)
				VBox.setVgrow(this, Priority.ALWAYS)
			}
		}
	}

	private fun EditorPane.bindTitle() {
		this@Editor.titleProperty.bind(this.titleProperty)
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
}