package io.gitlab.arturbosch.kdit.highlightings.syntax

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * @author Artur Bosch
 */
private val XML_NODE = "\\<([^\\s]+)" + "|" + "(\\>|/\\>)"
private val XML_STRING = "\"([^\"\\\\]|\\\\.)*\"" + "|" + "\'([^\"\\\\]|\\\\.)*\'"
private val XML_ATTRIBUTE = "\\w*="
private val XML_COMMENT = "\\<!--\\\\[\\s\\S\\\\]*?--\\>"

val XML_PATTERN: Pattern = Pattern.compile(
		"(?<NODE>" + XML_NODE + ")"
				+ "|(?<COMMENT>" + XML_COMMENT + ")"
				+ "|(?<STRING>" + XML_STRING + ")"
				+ "|(?<ATTRIBUTE>" + XML_ATTRIBUTE + ")")


fun xmlMatching(matcher: Matcher): String? {
	return (if (matcher.group("COMMENT") != null)
		"comment"
	else if (matcher.group("STRING") != null)
		"string"
	else if (matcher.group("NODE") != null)
		"node"
	else if (matcher.group("ATTRIBUTE") != null)
		"attribute"
	else
		null)
}