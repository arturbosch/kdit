package io.gitlab.arturbosch.kdit.editor

import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import tornadofx.View
import tornadofx.hbox
import tornadofx.vbox

/**
 * @author Artur Bosch
 */
class Editor : View() {

	private lateinit var editorPane: EditorPane

	override val root = vbox {
		hbox {
			// for toolbar
		}
		editorPane {
			editorPane = this
			VBox.setVgrow(this, Priority.ALWAYS)
		}
	}.apply {
		stylesheets.add("java-keywords.css")
	}

}