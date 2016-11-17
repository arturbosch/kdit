package io.gitlab.arturbosch.kdit.highlightings.syntax

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * @author Artur Bosch
 */
private val MD_HEADER = "(#+) (.*)"
private val MD_BOLD = "(\\**|__)(.*?)\\1"
private val MD_EMPH = "(\\*|_)(.*?)\\1"
private val MD_LINKS = "\\[([^\\[]+)\\]\\(([^\\)]+)\\)"
private val MD_CODE = "`(.*?)`"

val MD_PATTERN: Pattern = Pattern.compile(
		"(?<HEADERS>" + MD_HEADER + ")"
				+ "|(?<BOLD>" + MD_BOLD + ")"
				+ "|(?<EMPH>" + MD_EMPH + ")"
				+ "|(?<CODE>" + MD_CODE + ")"
				+ "|(?<LINKS>" + MD_LINKS + ")")

fun mdMatching(matcher: Matcher): String? {
	return (if (matcher.group("HEADERS") != null)
		"headers"
	else if (matcher.group("LINKS") != null)
		"links"
	else if (matcher.group("BOLD") != null)
		"bold"
	else if (matcher.group("EMPH") != null)
		"emph"
	else if (matcher.group("CODE") != null)
		"code"
	else
		null)
}