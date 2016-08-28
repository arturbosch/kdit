package io.gitlab.arturbosch.kdit.highlightings.syntax

import java.util.regex.Pattern

/**
 * @author Artur Bosch
 */

private val KEYWORDS_SCALA = arrayOf(
		"abstract",
		"case",
		"catch",
		"class",
		"def",
		"do",
		"else",
		"extends",
		"false",
		"final",
		"finally",
		"for",
		"forSome",
		"if",
		"implicit",
		"import",
		"lazy",
		"match",
		"new",
		"Null",
		"object",
		"override",
		"package",
		"private",
		"protected",
		"return",
		"sealed",
		"super",
		"this",
		"throw",
		"trait",
		"Try",
		"true",
		"type",
		"val",
		"var",
		"while",
		"with",
		"yield"
)

private val SCALA_KEYWORDS_PATTERN = "\\b(" + KEYWORDS_SCALA.joinToString("|") + ")\\b"

val SCALA_PATTERN: Pattern = Pattern.compile(
		"(?<KEYWORD>" + SCALA_KEYWORDS_PATTERN + ")"
				+ "|(?<PAREN>" + PAREN_PATTERN + ")"
				+ "|(?<BRACE>" + BRACE_PATTERN + ")"
				+ "|(?<BRACKET>" + BRACKET_PATTERN + ")"
				+ "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
				+ "|(?<STRING>" + STRING_PATTERN + ")"
				+ "|(?<COMMENT>" + COMMENT_PATTERN + ")")