package com.sudosan.entity.database

import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Property
import org.neo4j.ogm.annotation.Relationship


@NodeEntity(label = "database")
class Database(@Id
               @Property(name = "databaseName")
               var databaseName: String? = null) {

    @Relationship(type = "CONTAINS", direction = "OUTGOING")
    var tables: Collection<Table>? = mutableListOf<Table>()

    fun addTable(table: Table) {
        tables = tables?.plus(table)
    }

    fun addAllTables(tables: Collection<Table>?) {
        this.tables = tables
    }

}
