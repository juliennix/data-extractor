package com.sudosan.entity.database

import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Property
import org.neo4j.ogm.annotation.Relationship


@NodeEntity(label = "column")
class Column {

    constructor(tableName: String, columnName: String) {
        this.tableAndColumnName = "$tableName.$columnName"
    }

    @Id
    @Property(name = "columnName")
    var tableAndColumnName: String? = null

    @Relationship(type = "IS_IN", direction = "OUTGOING")
    var table: Table? = null

    @Relationship(type = "IS_FK", direction = "OUTGOING")
    var foreignKeyColumn: Collection<Column> = mutableListOf<Column>()

    @Property(name = "isPrimary")
    var isPrimary: Boolean = false

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Column

        if (tableAndColumnName != other.tableAndColumnName) return false

        return true
    }

    override fun hashCode(): Int {
        return tableAndColumnName?.hashCode() ?: 0
    }


}
