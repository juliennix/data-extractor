package com.sudosan.entity.java

import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Property


@NodeEntity(label = "import")
class Import(@Id
             @Property(name = "import") var import: String?)
