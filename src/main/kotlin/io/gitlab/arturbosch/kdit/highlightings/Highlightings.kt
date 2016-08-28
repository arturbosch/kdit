package io.gitlab.arturbosch.kdit.highlightings

import io.gitlab.arturbosch.kdit.highlightings.syntax.JAVA_PATTERN
import io.gitlab.arturbosch.kdit.highlightings.syntax.XML_PATTERN
import io.gitlab.arturbosch.kdit.highlightings.syntax.javaMatching
import io.gitlab.arturbosch.kdit.highlightings.syntax.xmlMatching
import org.fxmisc.richtext.model.StyleSpans
import org.fxmisc.richtext.model.StyleSpansBuilder
import java.nio.file.Path
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * @author Artur Bosch
 */

fun syntax(text: String, path: Path?): StyleSpans<Collection<String>>? {
	if (path == null) return null
	return when (path.toString().substringAfterLast(".")) {
		"java" -> computeHighlighting(text, JAVA_PATTERN, { javaMatching(it) })
		"xml" -> computeHighlighting(text, XML_PATTERN, { xmlMatching(it) })
		else -> null
	}
}

private fun computeHighlighting(text: String, pattern: Pattern,
								groupMatching: (Matcher) -> String?): StyleSpans<Collection<String>> {
	val matcher = pattern.matcher(text)
	var lastKwEnd = 0
	val spansBuilder = StyleSpansBuilder<Collection<String>>()
	while (matcher.find()) {
		val styleClass: String? =
				groupMatching.invoke(matcher)!!
		spansBuilder.add(emptyList<String>(), matcher.start() - lastKwEnd)
		spansBuilder.add(setOf(styleClass ?: ""), matcher.end() - matcher.start())
		lastKwEnd = matcher.end()
	}
	spansBuilder.add(emptyList<String>(), text.length - lastKwEnd)
	return spansBuilder.create()
}
