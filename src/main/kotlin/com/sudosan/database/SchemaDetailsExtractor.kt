package com.sudosan.database

import com.sudosan.App
import com.sudosan.packageEntityName
import com.sudosan.entity.database.Column
import com.sudosan.entity.database.Database
import com.sudosan.entity.database.Table
import com.sudosan.entity.java.ClassMetadata
import org.neo4j.ogm.session.SessionFactory
import org.slf4j.LoggerFactory
import java.sql.DatabaseMetaData
import java.sql.DriverManager
import java.sql.ResultSet

class SchemaDetailsExtractor {
    private val log = LoggerFactory.getLogger(SchemaDetailsExtractor::class.java)
    val databaseName = "immcbuilder"
    val userName = "root"
    val password = "password"
    val mySQLPort = "3306"
    val hostUrl = "localhost"

    fun extractDatabaseStructure() {

        val sessionFactory = SessionFactory(App().configuration, packageEntityName)
        val session = sessionFactory.openSession()

        sessionFactory.close()

        // Setup the connection with the DB
        Class.forName("com.mysql.cj.jdbc.Driver")
        val conn = DriverManager.getConnection("jdbc:mysql://" + hostUrl
                + ":" + mySQLPort, userName, password)
        // --- LISTING DATABASE SCHEMA NAMES ---
        var resultSet = conn.metaData.catalogs
        while (resultSet.next()) {
            log.info("Schema Name = " + resultSet.getString("TABLE_CAT"))
        }
        resultSet.close()

        val database = Database(databaseName)

        // --- LISTING DATABASE TABLE NAMES ---
        val types = arrayOf("TABLE")
        resultSet = conn.metaData
                .getTables(databaseName, null, "%", types)
        var tableName: String
        while (resultSet.next()) {
            tableName = resultSet.getString(3)
            log.info("Table Name = $tableName")
            // --- LISTING DATABASE COLUMN NAMES ---
            val meta = conn.metaData
            val columns = meta.getColumns(databaseName, null, tableName, "%")

            val table = Table(tableName)
            database.addTable(table)
            table.database = database

            val primaryKeys: List<String> = getPrimaryKeyList(meta, table)
            val foreignKeys: HashMap<Column, Column> = getForeignKeyList(meta, table)

            while (columns.next()) {
                val columnName = columns.getString(4)
                log.info("Column Name of table " + tableName + " = "
                        + columnName)

                val column = Column(tableName, columnName)
                column.table = table
                if (primaryKeys.contains(columnName)) {
                    column.isPrimary = true
                }

                if (foreignKeys.containsKey(column)) {
                    val foreignColumn = foreignKeys[column]!!
                    column.foreignKeyColumn = column.foreignKeyColumn.plus(foreignColumn)
                }

                table.addColumn(column)
                session.save(column)

                session.load(ClassMetadata::class.java, column.tableAndColumnName)
            }
        }
        resultSet.close()
        sessionFactory.close()

    }

    private fun getPrimaryKeyList(meta: DatabaseMetaData, table: Table): MutableList<String> {
        val primaryKeysResultSet: ResultSet = meta.getPrimaryKeys(null, null, table.tableName)
        val primaryKeys = mutableListOf<String>()
        while (primaryKeysResultSet.next()) {
            val columnName = primaryKeysResultSet.getString("COLUMN_NAME")
            primaryKeys.add(columnName)
        }
        return primaryKeys
    }

    private fun getForeignKeyList(meta: DatabaseMetaData, table: Table): HashMap<Column, Column> {
        val foreignKeysResultSet: ResultSet = meta.getImportedKeys(null, null, table.tableName)
        val foreignKeys = hashMapOf<Column, Column>()
        while (foreignKeysResultSet.next()) {
            // Add table
            val primaryTableName = foreignKeysResultSet.getString("PKTABLE_NAME")
            table.foreignKeyTable = table.foreignKeyTable.plus(Table(primaryTableName))

            val foreignTableName = foreignKeysResultSet.getString("FKTABLE_NAME")
            val primaryColumnKey = foreignKeysResultSet.getString("PKCOLUMN_NAME")
            val foreignColumnName = foreignKeysResultSet.getString("FKCOLUMN_NAME")
            foreignKeys[Column(foreignTableName, foreignColumnName)] = Column(primaryTableName, primaryColumnKey)
        }
        return foreignKeys
    }
}
