package io.gitlab.arturbosch.kdit.highlightings.syntax

import java.util.regex.Matcher
import java.util.regex.Pattern

internal val KEYWORDS = arrayOf("abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const", "continue", "default", "do", "double", "else", "enum", "extends", "final", "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long", "native", "new", "package", "private", "protected", "public", "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this", "throw", "throws", "transient", "try", "void", "volatile", "while")

private val KEYWORD_PATTERN = "\\b(" + KEYWORDS.joinToString("|") + ")\\b"
private const val PAREN_PATTERN = "\\(|\\)"
private const val BRACE_PATTERN = "\\{|\\}"
private const val BRACKET_PATTERN = "\\[|\\]"
private const val SEMICOLON_PATTERN = "\\;"
private const val STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"" + "|" + "\'([^\"\\\\]|\\\\.)*\'"
private const val COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/"
private const val ANNOTATION_PATTERN = "@[A-Za-z]+"

const val BASIC_PATTERN = "|(?<ANNOTATION>$ANNOTATION_PATTERN)|(?<PAREN>$PAREN_PATTERN)|(?<BRACE>$BRACE_PATTERN)|(?<BRACKET>$BRACKET_PATTERN)|(?<SEMICOLON>$SEMICOLON_PATTERN)|(?<STRING>$STRING_PATTERN)|(?<COMMENT>$COMMENT_PATTERN)"

val JAVA_PATTERN: Pattern = Pattern.compile("(?<KEYWORD>$KEYWORD_PATTERN)$BASIC_PATTERN")

fun javaMatching(matcher: Matcher): String? = when {
	matcher.group("KEYWORD") != null -> "keyword"
	matcher.group("ANNOTATION") != null -> "annotation"
	matcher.group("PAREN") != null -> "paren"
	matcher.group("BRACE") != null -> "brace"
	matcher.group("BRACKET") != null -> "bracket"
	matcher.group("SEMICOLON") != null -> "semicolon"
	matcher.group("STRING") != null -> "string"
	matcher.group("COMMENT") != null -> "comment"
	else -> null
}
