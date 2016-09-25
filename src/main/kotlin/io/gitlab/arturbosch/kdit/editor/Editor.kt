package io.gitlab.arturbosch.kdit.editor

import io.gitlab.arturbosch.kdit.editor.util.editorPane
import io.gitlab.arturbosch.kdit.project.FileEntry
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import tornadofx.View
import tornadofx.borderpane
import tornadofx.center
import tornadofx.left
import tornadofx.top
import tornadofx.treeview
import java.nio.file.Paths

/**
 * @author Artur Bosch
 */
class Editor : View() {

	private lateinit var editorPane: EditorPane

	override val root = borderpane {
		top {
			// for toolbar
		}
		left {
			val path = Paths.get("/home/artur/Repos/kdit")
			val root = FileEntry(path)
			root.isExpanded = true
			treeview(root) {
				style = "-fx-font: 12px Tahoma; -fx-stroke: #eeeeee; -fx-background-color: #0a0a0a; -fx-text-fill: #ffffff;"
			}
		}
		center {
			editorPane {
				bindTitle()
				editorPane = this
				VBox.setVgrow(this, Priority.ALWAYS)
			}
		}
	}

	private fun EditorPane.bindTitle() {
		this@Editor.titleProperty.bind(this.titleProperty)
	}

}