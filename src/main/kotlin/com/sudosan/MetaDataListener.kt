//package com.sudosan
package com.sudosan

import com.sudosan.entity.java.Import
import com.sudosan.grammar.Java8BaseListener
import com.sudosan.grammar.Java8Parser


class MetaDataListener : Java8BaseListener() {

    var packageName = ""
    val imports = mutableListOf<Import>()

    // ... getter for errors

    override fun enterImportDeclaration(ctx: Java8Parser.ImportDeclarationContext) {
        val node = ctx.singleTypeImportDeclaration()
        if (node != null) imports += Import(node.text)
    }

    override fun enterPackageDeclaration(ctx: Java8Parser.PackageDeclarationContext) {
        val node = ctx.packageName()
        if (node != null) packageName = node.text
    }
}
