package io.gitlab.arturbosch.kdit.highlightings

import org.fxmisc.richtext.model.StyleSpans
import org.fxmisc.richtext.model.StyleSpansBuilder

/**
 * @author Artur Bosch
 */
fun computeHighlighting(text: String): StyleSpans<Collection<String>> {
	val matcher = PATTERN.matcher(text)
	var lastKwEnd = 0
	val spansBuilder = StyleSpansBuilder<Collection<String>>()
	while (matcher.find()) {
		val styleClass = (if (matcher.group("KEYWORD") != null)
			"keyword"
		else if (matcher.group("PAREN") != null)
			"paren"
		else if (matcher.group("BRACE") != null)
			"brace"
		else if (matcher.group("BRACKET") != null)
			"bracket"
		else if (matcher.group("SEMICOLON") != null)
			"semicolon"
		else if (matcher.group("STRING") != null)
			"string"
		else if (matcher.group("COMMENT") != null)
			"comment"
		else
			null)!! /* never happens */
		spansBuilder.add(emptyList<String>(), matcher.start() - lastKwEnd)
		spansBuilder.add(setOf(styleClass), matcher.end() - matcher.start())
		lastKwEnd = matcher.end()
	}
	spansBuilder.add(emptyList<String>(), text.length - lastKwEnd)
	return spansBuilder.create()
}