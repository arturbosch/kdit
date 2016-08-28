package io.gitlab.arturbosch.kdit.highlightings.syntax

import java.util.regex.Pattern

/**
 * @author Artur Bosch
 */

private val KEYWORDS_GROOVY = arrayOf(
		"abstract",
		"as",
		"assert",
		"boolean",
		"break",
		"byte",
		"case",
		"catch",
		"char",
		"class",
		"const",
		"continue",
		"def",
		"default",
		"do",
		"double",
		"else",
		"enum",
		"extends",
		"false",
		"final",
		"finally",
		"float",
		"for",
		"goto",
		"if",
		"implements",
		"import",
		"in",
		"instanceof",
		"int",
		"interface",
		"long",
		"native",
		"new",
		"null",
		"package",
		"private",
		"protected",
		"public",
		"return",
		"short",
		"static",
		"strictfp",
		"super",
		"switch",
		"this",
		"threadsafe",
		"throw",
		"throws",
		"transient",
		"true",
		"try",
		"void",
		"volatile",
		"while",
		"synchronized"
)

private val GROOVY_KEYWORDS_PATTERN = "\\b(" + KEYWORDS_GROOVY.joinToString("|") + ")\\b"

val GROOVY_PATTERN: Pattern = Pattern.compile(
		"(?<KEYWORD>" + GROOVY_KEYWORDS_PATTERN + ")"
				+ "|(?<PAREN>" + PAREN_PATTERN + ")"
				+ "|(?<BRACE>" + BRACE_PATTERN + ")"
				+ "|(?<BRACKET>" + BRACKET_PATTERN + ")"
				+ "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
				+ "|(?<STRING>" + STRING_PATTERN + ")"
				+ "|(?<COMMENT>" + COMMENT_PATTERN + ")")