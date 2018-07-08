package io.gitlab.arturbosch.kdit.highlightings.syntax

import java.util.regex.Matcher
import java.util.regex.Pattern

private const val MD_HEADER = "(#+) (.*)"
private const val MD_BOLD = "(\\**|__)(.*?)\\1"
private const val MD_EMPH = "(\\*|_)(.*?)\\1"
private const val MD_LINKS = "\\[([^\\[]+)\\]\\(([^\\)]+)\\)"
private const val MD_CODE = "`(.*?)`"

val MD_PATTERN: Pattern = Pattern.compile(
		"(?<HEADERS>" + MD_HEADER + ")"
				+ "|(?<BOLD>" + MD_BOLD + ")"
				+ "|(?<EMPH>" + MD_EMPH + ")"
				+ "|(?<CODE>" + MD_CODE + ")"
				+ "|(?<LINKS>" + MD_LINKS + ")")

fun mdMatching(matcher: Matcher): String? = when {
	matcher.group("HEADERS") != null -> "headers"
	matcher.group("LINKS") != null -> "links"
	matcher.group("BOLD") != null -> "bold"
	matcher.group("EMPH") != null -> "emph"
	matcher.group("CODE") != null -> "code"
	else -> null
}
