package io.gitlab.arturbosch.kdit.highlightings.syntax

import java.util.regex.Pattern

/**
 * @author Artur Bosch
 */
private val XML_NODE = "\\<([^\"\\\\]|\\\\.)*\\>" + "|" + "\\<([^\\s]+)" + "|" + "(\\>|/>)"
private val XML_COMMENT = "<!--\\*(.|\\R)*?\\*-->"

val XML_PATTERN: Pattern = Pattern.compile(
		"(?<STRING>" + XML_NODE + ")"
				+ "|(?<COMMENT>" + XML_COMMENT + ")")
