package io.gitlab.arturbosch.kdit.highlightings.syntax

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * @author Artur Bosch
 */
internal val KEYWORDS = arrayOf("abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const", "continue", "default", "do", "double", "else", "enum", "extends", "final", "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long", "native", "new", "package", "private", "protected", "public", "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this", "throw", "throws", "transient", "try", "void", "volatile", "while")

internal val KEYWORD_PATTERN = "\\b(" + KEYWORDS.joinToString("|") + ")\\b"
internal val PAREN_PATTERN = "\\(|\\)"
internal val BRACE_PATTERN = "\\{|\\}"
internal val BRACKET_PATTERN = "\\[|\\]"
internal val SEMICOLON_PATTERN = "\\;"
internal val STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"" + "|" + "\'([^\"\\\\]|\\\\.)*\'"
internal val COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/"

val JAVA_PATTERN: Pattern = Pattern.compile(
		"(?<KEYWORD>" + KEYWORD_PATTERN + ")"
				+ "|(?<PAREN>" + PAREN_PATTERN + ")"
				+ "|(?<BRACE>" + BRACE_PATTERN + ")"
				+ "|(?<BRACKET>" + BRACKET_PATTERN + ")"
				+ "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
				+ "|(?<STRING>" + STRING_PATTERN + ")"
				+ "|(?<COMMENT>" + COMMENT_PATTERN + ")")

fun javaMatching(matcher: Matcher): String? {
	return (if (matcher.group("KEYWORD") != null)
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
		null)
}
