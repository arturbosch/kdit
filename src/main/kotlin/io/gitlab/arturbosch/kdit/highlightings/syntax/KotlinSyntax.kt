package io.gitlab.arturbosch.kdit.highlightings.syntax

import java.util.regex.Pattern

private val KEYWORDS_KOTLIN = arrayOf(
		"package",
		"import",
		"private",
		"public",
		"internal",
		"as",
		"typealias",
		"class",
		"this",
		"super",
		"val",
		"var",
		"fun",
		"for",
		"null",
		"true",
		"false",
		"is",
		"in",
		"throw",
		"return",
		"break",
		"continue",
		"object",
		"if",
		"try",
		"else",
		"while",
		"do",
		"when",
		"interface",
		"typeof"
)

private val KOTLIN_KEYWORDS_PATTERN = "\\b(" + KEYWORDS_KOTLIN.joinToString("|") + ")\\b"

val KOTLIN_PATTERN: Pattern = Pattern.compile("(?<KEYWORD>$KOTLIN_KEYWORDS_PATTERN)$BASIC_PATTERN")
