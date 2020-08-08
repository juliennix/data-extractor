package com.sudosan.entity.java

import org.neo4j.ogm.annotation.*


@NodeEntity(label = "class")
class ClassMetadata {

    @Id
    @Property(name = "className")
    var className: String? = null

    @Relationship(type = "IMPORTS", direction = "INCOMING")
    var imports: Collection<Import>? = mutableListOf<Import>()

    @Relationship(type = "IS_IN", direction = "INCOMING")
    var internalPackage: InternalPackage = InternalPackage()

    fun imports(import: Import) {
        imports = imports?.plus(import)
    }

    fun importsAll(imports: Collection<Import>?) {
        this.imports = imports
    }

}
