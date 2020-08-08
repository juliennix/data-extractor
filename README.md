# Data-extractor for java projects
The idea of this Kotlin project is to use neo4j and a grammar to analyse java projects 
in terms of dependency. It was a small project but wanted to share if someone wants to
look at kotlin code, see grammar use to analyse structured text, play with neo4j or
take it to next step and create code analysis, database analyses (etc) tool.

It is useful to detect and analyse how a java project is build, if any architecture issue,
dependency issues (e.g. cardinality or loop). See picture to see what can be seen.

Run this AppKt with a path as argument, and it will run recursively from the path and 
look at all java file (with .java extension).
After few seconds/minutes depending on the project to analyse,
it will push all the data analysed in a neo4j database.

## Setup
### Neo4j database: 
Go on neo4j website, install neo4j 4.1.0 (please note the version and double check in the
gradle file if it still fits).
Create a database, it can be created with neo4j name and password as password, it may 
be changed in the App.Kt file.
Once the database is running, you are good to go.

### Kotlin
Install openjdk 11 or amazon java corretto 11 and run the App.Kt main file.
The easiest is to install intellij and run the AppKt goal with a path as argument.


## Analyse
Simple go on the neo4j browser and test some requests like
See all nodes (info):
match(n) return n
See import info from a node:
match(import:import {import: 'import+packagePath'}) return import
'import+packagePath' can be: importcom.sudosan.entity for example

Fee free to play with it and look at how your code is structured.

Have fun :) :) :) 

## Images

Intellij run setup
![Alt text](./images/2020-08-08 20_20_51-data-extractor.png?raw=true "Data extractor intellij")

Neo4j all nodes of a java project example
![Alt text](./images/2020-08-08 22_21_33-neo4j3.png?raw=true "Neo4j all nodes of a project example")

Neo4j import example
![Alt text](./images/2020-08-08 22_15_26-neo4j_import.png?raw=true "Neo4j import example")

Neo4j import extended example
![Alt text](./images/2020-08-08 22_16_23-neo4j2.png?raw=true "Neo4j import extended example")
