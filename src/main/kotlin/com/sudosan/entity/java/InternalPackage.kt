package com.sudosan.entity.java

import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Property
import org.neo4j.ogm.annotation.Relationship


@NodeEntity(label = "internalPackage")
class InternalPackage(@Id
                      @Property(name = "packageName") var packageName: String? = "") {

    @Relationship(type = "CONTAINS", direction = "INCOMING")
    var classes: Collection<ClassMetadata>? = mutableListOf<ClassMetadata>()

    fun addClass(classMetadata: ClassMetadata) {
        classes = classes?.plus(classMetadata)
    }
}
