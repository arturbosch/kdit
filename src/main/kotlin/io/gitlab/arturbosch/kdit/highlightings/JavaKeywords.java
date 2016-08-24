package io.gitlab.arturbosch.kdit.highlightings;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import java.io.File;
import java.net.MalformedURLException;

/**
 * @author Artur Bosch
 */
public class JavaKeywords extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws MalformedURLException {
		CodeArea codeArea = new CodeArea();
		codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));

		codeArea.richChanges()
				.filter(ch -> !ch.getInserted().equals(ch.getRemoved()))
				.subscribe(change -> codeArea.setStyleSpans(0, HighlightingsKt.computeHighlighting(codeArea.getText())));
		codeArea.replaceText(0, 0, JavaKeywordsKt.getSampleCode());

		Scene scene = new Scene(new StackPane(new VirtualizedScrollPane<>(codeArea)), 600, 400);
		scene.getStylesheets().add(new File("src/main/resources/io/gitlab/arturbosch/kdit/java-keywords.css").toURI().toURL().toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Java Keywords Demo");
		primaryStage.show();
	}

}