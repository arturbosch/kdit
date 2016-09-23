package io.gitlab.arturbosch.kdit.highlightings.syntax

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * @author Artur Bosch
 */
internal val KEYWORDS = arrayOf("abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const", "continue", "default", "do", "double", "else", "enum", "extends", "final", "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long", "native", "new", "package", "private", "protected", "public", "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this", "throw", "throws", "transient", "try", "void", "volatile", "while")

private val KEYWORD_PATTERN = "\\b(" + KEYWORDS.joinToString("|") + ")\\b"
private val PAREN_PATTERN = "\\(|\\)"
private val BRACE_PATTERN = "\\{|\\}"
private val BRACKET_PATTERN = "\\[|\\]"
private val SEMICOLON_PATTERN = "\\;"
private val STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"" + "|" + "\'([^\"\\\\]|\\\\.)*\'"
private val COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/"
private val ANNOTATION_PATTERN = "@[A-Za-z]+"

val BASIC_PATTERN = "|(?<ANNOTATION>$ANNOTATION_PATTERN)|(?<PAREN>$PAREN_PATTERN)|(?<BRACE>$BRACE_PATTERN)|(?<BRACKET>$BRACKET_PATTERN)|(?<SEMICOLON>$SEMICOLON_PATTERN)|(?<STRING>$STRING_PATTERN)|(?<COMMENT>$COMMENT_PATTERN)"

val JAVA_PATTERN: Pattern = Pattern.compile("(?<KEYWORD>$KEYWORD_PATTERN)$BASIC_PATTERN")

fun javaMatching(matcher: Matcher): String? {
	return (if (matcher.group("KEYWORD") != null)
		"keyword"
	else if (matcher.group("ANNOTATION") != null)
		"annotation"
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
