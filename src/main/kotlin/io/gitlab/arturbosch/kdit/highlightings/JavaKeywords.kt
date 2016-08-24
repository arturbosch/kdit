package io.gitlab.arturbosch.kdit.highlightings

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
internal val STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\""
internal val COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/"

val PATTERN: Pattern = Pattern.compile(
		"(?<KEYWORD>" + KEYWORD_PATTERN + ")"
				+ "|(?<PAREN>" + PAREN_PATTERN + ")"
				+ "|(?<BRACE>" + BRACE_PATTERN + ")"
				+ "|(?<BRACKET>" + BRACKET_PATTERN + ")"
				+ "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
				+ "|(?<STRING>" + STRING_PATTERN + ")"
				+ "|(?<COMMENT>" + COMMENT_PATTERN + ")")

val sampleCode = arrayOf("package com.example;", "", "import java.util.*;", "", "public class Foo extends Bar implements Baz {", "", "    /*", "     * multi-line comment", "     */", "    public static void main(String[] args) {", "        // single-line comment", "        for(String arg: args) {", "            if(arg.length() != 0)", "                System.out.println(arg);", "            else", "                System.err.println(\"Warning: empty string as argument\");", "        }", "    }", "", "}").joinToString("\n")