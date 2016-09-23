package io.gitlab.arturbosch.kdit.editor

import javafx.application.Platform
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCombination
import org.fxmisc.richtext.CodeArea
import org.fxmisc.wellbehaved.event.EventPattern
import org.fxmisc.wellbehaved.event.InputMap.consume
import org.fxmisc.wellbehaved.event.InputMap.sequence
import org.fxmisc.wellbehaved.event.Nodes

/**
 * @author Artur Bosch
 */

fun EditorPane.registerShortKeys() {
	val tabPane = this
	Nodes.addInputMap(this, sequence(
			consume(EventPattern.keyPressed(KeyCode.T, KeyCombination.CONTROL_DOWN)) {
				tabPane.editorTab("New Tab...").run { tabPane.focus(this) }
			},
			consume(EventPattern.keyPressed(KeyCode.Q, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN)) {
				tabPane.saveEditedTabs()
				Platform.exit()
			},
			consume(EventPattern.keyPressed(KeyCode.W, KeyCombination.CONTROL_DOWN)) {
				tabPane.tabs.remove(tabPane.selectionModel.selectedItem)
			},
			consume(EventPattern.keyPressed(KeyCode.H, KeyCombination.CONTROL_DOWN)) {
				tabPane.showHelp()
			},
			consume(EventPattern.keyPressed(KeyCode.LEFT, KeyCombination.CONTROL_DOWN, KeyCombination.ALT_DOWN)) {
				tabPane.switchTabLeft()
			},
			consume(EventPattern.keyPressed(KeyCode.RIGHT, KeyCombination.CONTROL_DOWN, KeyCombination.ALT_DOWN)) {
				tabPane.switchTabRight()
			},
			consume(EventPattern.keyPressed(KeyCode.ESCAPE)) {
				tabPane.switchFocus()
			},
			consume(EventPattern.keyPressed(KeyCode.O, KeyCombination.CONTROL_DOWN)) {
				ProjectChooser.openFile().ifPresent {
					tabPane.newTab(it)
				}
			},
			consume(EventPattern.keyPressed(KeyCode.S, KeyCombination.CONTROL_DOWN)) {
				tabPane.saveTab()
			},
			consume(EventPattern.keyPressed(KeyCode.A, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN)) {
				tabPane.saveAsNewPath()
			},
			consume(EventPattern.keyPressed(KeyCode.O, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN)) {
				println(ProjectChooser.openDir())
			}
	))
}

fun CodeArea.registerShortKeys() {
	val codeArea = this
	Nodes.addInputMap(this, sequence(
			consume(EventPattern.keyPressed(KeyCode.K, KeyCombination.CONTROL_DOWN)) {
				codeArea.deleteLine()
			},
			consume(EventPattern.keyPressed(KeyCode.D, KeyCombination.CONTROL_DOWN)) {
				codeArea.duplicateLine()
			},
			consume(EventPattern.keyPressed(KeyCode.ENTER, KeyCombination.SHIFT_DOWN)) {
				codeArea.newLine()
			},
			consume(EventPattern.keyPressed(KeyCode.UP, KeyCombination.CONTROL_DOWN)) {
				codeArea.previousSection()
			},
			consume(EventPattern.keyPressed(KeyCode.DOWN, KeyCombination.CONTROL_DOWN)) {
				codeArea.nextSection()
			}
	))
}

