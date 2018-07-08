package io.gitlab.arturbosch.kdit

import io.gitlab.arturbosch.kdit.editor.Editor
import javafx.application.Application
import tornadofx.App
import tornadofx.View
import kotlin.reflect.KClass

/**
 * @author Artur Bosch
 */
class Kdit : App() {
	override val primaryView: KClass<out View>
		get() = Editor::class
}

fun main(vararg args: String) {
	Application.launch(Kdit::class.java, *args)
}
