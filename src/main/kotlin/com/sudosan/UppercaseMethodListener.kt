//package com.sudosan
package com.sudosan

import com.sudosan.grammar.Java8BaseListener
import com.sudosan.grammar.Java8Parser
import java.util.*


class UppercaseMethodListener : Java8BaseListener() {

    private val errors = ArrayList<String>()

    // ... getter for errors

    override fun enterMethodDeclarator(ctx: Java8Parser.MethodDeclaratorContext) {
        val node = ctx.Identifier()
        val methodName = node.getText()

        if (Character.isUpperCase(methodName.get(0))) {
            val error = String.format("Method %s is uppercased!", methodName)
            errors.add(error)
        }
    }
}
