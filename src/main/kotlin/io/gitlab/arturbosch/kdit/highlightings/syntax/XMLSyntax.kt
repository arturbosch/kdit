package io.gitlab.arturbosch.kdit.highlightings.syntax

import java.util.regex.Matcher
import java.util.regex.Pattern

private const val XML_NODE = "\\<([^\\s]+)" + "|" + "(\\>|/\\>)"
private const val XML_STRING = "\"([^\"\\\\]|\\\\.)*\"" + "|" + "\'([^\"\\\\]|\\\\.)*\'"
private const val XML_ATTRIBUTE = "\\w*="
private const val XML_COMMENT = "\\<!--\\\\[\\s\\S\\\\]*?--\\>"

val XML_PATTERN: Pattern = Pattern.compile(
		"(?<NODE>" + XML_NODE + ")"
				+ "|(?<COMMENT>" + XML_COMMENT + ")"
				+ "|(?<STRING>" + XML_STRING + ")"
				+ "|(?<ATTRIBUTE>" + XML_ATTRIBUTE + ")")

fun xmlMatching(matcher: Matcher): String? = when {
	matcher.group("COMMENT") != null -> "comment"
	matcher.group("STRING") != null -> "string"
	matcher.group("NODE") != null -> "node"
	matcher.group("ATTRIBUTE") != null -> "attribute"
	else -> null
}
