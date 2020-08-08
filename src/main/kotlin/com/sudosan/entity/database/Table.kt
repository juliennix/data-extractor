package com.sudosan.entity.database

import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Property
import org.neo4j.ogm.annotation.Relationship


@NodeEntity(label = "table")
class Table(@Id
            @Property(name = "tableName")
            var tableName: String? = null) {

    @Relationship(type = "IS_IN", direction = "OUTGOING")
    var database: Database? = null

    @Relationship(type = "CONTAINS", direction = "OUTGOING")
    var columns: Collection<Column>? = mutableListOf<Column>()

    @Relationship(type = "IS_LINKED_BY_FK", direction = "OUTGOING")
    var foreignKeyTable: Collection<Table> = mutableListOf<Table>()

    fun addColumn(column: Column) {
        columns = columns?.plus(column)
    }

    fun addAllColumns(columns: Collection<Column>?) {
        this.columns = columns
    }

}
