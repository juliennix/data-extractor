//package com.sudosan

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTreeWalker
import org.junit.Test


internal class UppercaseMethodListenerTest {

    @Test
    fun enterMethodDeclarator() {
        val javaClassContent = "import java.util.*;\n" +
                "\n" +
                "\n" +
                "class App {\n" +
                "    val greeting: String\n" +
                "        get() {\n" +
                "            return \"Data extractor is extracting the data (yeah)\"\n" +
                "        }\n" +
                "}"
        val java8Lexer = Java8Lexer(CharStreams.fromString(javaClassContent))

        val tokens = CommonTokenStream(java8Lexer)
        val parser = Java8Parser(tokens)
        val tree = parser.compilationUnit()

        val walker = ParseTreeWalker()
        val listener = UppercaseMethodListener()

        walker.walk(listener, tree)
    }
}
