package io.gitlab.arturbosch.kdit.editor.util

import javafx.stage.DirectoryChooser
import javafx.stage.FileChooser
import tornadofx.FX
import java.io.File
import java.nio.file.Path
import java.util.Optional

/**
 * @author Artur Bosch
 */
object ProjectChooser {

	fun openFile(): Optional<Path> {
		return Optional.ofNullable(FileChooser().showOpenDialog(FX.primaryStage))
				.map(File::toPath)
	}

	fun chooseFile(): Optional<Path> {
		return Optional.ofNullable(FileChooser().showSaveDialog(FX.primaryStage))
				.map(File::toPath)
	}

	fun openDir(): Optional<Path> {
		return Optional.ofNullable(DirectoryChooser().showDialog(FX.primaryStage))
				.map(File::toPath)
	}

}