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
import tornadofx.top
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
			add(Explorer(path))
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